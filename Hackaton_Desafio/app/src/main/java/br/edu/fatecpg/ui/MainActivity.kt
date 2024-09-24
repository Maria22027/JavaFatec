package br.edu.fatecpg.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.fatecpg.ui.dao.ContatoDao
import br.edu.fatecpg.ui.model.Contato
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = ContatoDao()
        setContentView(R.layout.activity_main)

        val edtN = findViewById<TextInputLayout>(R.id.edt_nome)
        val edtW = findViewById<TextInputLayout>(R.id.edt_whats)
        val edtI = findViewById<TextInputLayout>(R.id.edt_img)
        val btnC = findViewById<AppCompatButton>(R.id.btnCadastro)
        val txt = findViewById<TextView>(R.id.txv_contacts)

        btnC.setOnClickListener(){
            val n = edtN.editText?.text.toString() // Obtendo o texto do EditText dentro do TextInputLayout
            val w = edtW.editText?.text.toString()
            val i = edtI.editText?.text.toString()

            val dado = Contato(n,w,i)
            dao.adicionarContato(dado)
        }
        txt.setOnClickListener(){
            val intent = Intent(this, ListaActivity::class.java)

            startActivity(intent)
        }


    }
}