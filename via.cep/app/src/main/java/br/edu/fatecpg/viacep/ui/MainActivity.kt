package br.edu.fatecpg.viacep.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.viacep.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var cepRegistrado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextCep = findViewById<EditText>(R.id.editTextCep)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val fabPesquisar = findViewById<FloatingActionButton>(R.id.fabPesquisar)

        btnBuscar.setOnClickListener {
            val cep = editTextCep.text.toString()

            if (cep.isNotEmpty()) {
                if (cep.matches(Regex("^[0-9]{8}$"))) {
                    cepRegistrado = cep
                    Toast.makeText(this, "Um CEP foi inserido com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "O CEP digitado não existe.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, digite um CEP.", Toast.LENGTH_SHORT).show()
            }
        }

        fabPesquisar.setOnClickListener {
            val cep = editTextCep.text.toString()

            if (cepRegistrado != null && cepRegistrado == cep) {
                val intent = Intent(this, ResultadoActivity::class.java)
                intent.putExtra("CEP", cepRegistrado)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erro! Clique em Buscar ou Digite um CEP correto!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
