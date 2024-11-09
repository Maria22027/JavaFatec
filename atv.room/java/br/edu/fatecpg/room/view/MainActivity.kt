package br.edu.fatecpg.room.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import br.edu.fatecpg.persistenciaroom.databinding.ActivityMainBinding
import br.edu.fatecpg.room.dao.UserDao
import br.edu.fatecpg.room.database.AppDatabase
import br.edu.fatecpg.room.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-app"
        ).fallbackToDestructiveMigration().build()

        userDao = db.userDao()

        binding.btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }

        binding.btnSave.setOnClickListener {
            val nome = binding.txtName.text.toString()
            val sobrenome = binding.txtSobrenome.text.toString()
            val email = binding.txtEmail.text.toString()

            if (selectedImageUri == null) {
                Toast.makeText(this, "Por favor, selecione uma imagem.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(0, nome, sobrenome, email, selectedImageUri.toString())

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val existingUser = userDao.getUserByEmail(email)
                    if (existingUser == null) {
                        userDao.insertAll(user)
                        Log.d("MainActivity", "Usuário salvo: $user")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Usuário salvo com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("MainActivity", "Email já cadastrado: $email")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Email já cadastrado!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Erro ao salvar o usuário: ${e.message}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Erro ao salvar o usuário!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnList.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val list = userDao.getAll() ?: emptyList()
                withContext(Dispatchers.Main) {
                    list.forEach {
                        Log.d("USUÁRIO", "${it.uid} - ${it.firstName} - ${it.lastName} - ${it.email} - ${it.profilePicture}")
                    }
                    Log.d("MainActivity", "Total de usuários listados: ${list.size}")
                }
            }
        }

        binding.btnUpdate.setOnClickListener {
            val nome = binding.txtName.text.toString()
            val sobrenome = binding.txtSobrenome.text.toString()
            val email = binding.txtEmail.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val user = userDao.getUserByEmail(email)
                    if (user != null) {
                        val updatedUser = user.copy(firstName = nome, lastName = sobrenome, profilePicture = selectedImageUri.toString())
                        userDao.updateUser(updatedUser)
                        Log.d("MainActivity", "Dados atualizados: $updatedUser")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("MainActivity", "Usuário não encontrado para atualização: $email")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Usuário não encontrado!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Erro ao atualizar o usuário: ${e.message}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Erro ao atualizar o usuário!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            val email = binding.txtEmail.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val user = userDao.getUserByEmail(email)
                    if (user != null) {
                        userDao.deleteUser(user)
                        Log.d("MainActivity", "Usuário deletado: $user")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Usuário deletado com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("MainActivity", "Usuário não encontrado para deleção: $email")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Usuário não encontrado!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Erro ao deletar o usuário: ${e.message}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Erro ao deletar o usuário!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                binding.imgProfile.setImageURI(uri)
            }
        }
    }

    companion object {
        private const val PICK_IMAGE = 1
    }
}
