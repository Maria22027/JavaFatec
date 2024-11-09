package br.edu.fatecpg.viacep.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.viacep.R
import br.edu.fatecpg.viacep.model.Endereco
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class ResultadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)
        val fabVoltar = findViewById<FloatingActionButton>(R.id.fabVoltar)

        val cep = intent.getStringExtra("CEP")
        if (cep != null) {
            Thread {
                val endereco = buscarEndereco(cep)
                runOnUiThread {
                    if (endereco != null) {
                        textViewResultado.text = endereco.toString()
                        Log.d("ResultadoActivity", endereco.toString())
                    } else {
                        textViewResultado.text = getString(R.string.cep_incorreto)
                        Toast.makeText(this, getString(R.string.cep_incorreto), Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }

        fabVoltar.setOnClickListener {
            finish()
        }
    }

    private fun buscarEndereco(cep: String): Endereco? {
        val url = URL("https://viacep.com.br/ws/$cep/json/")
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(response)
                
                if (jsonObject.has("erro")) {
                    null
                } else {
                    Endereco(
                        jsonObject.getString("cep"),
                        jsonObject.getString("logradouro"),
                        jsonObject.getString("complemento"),
                        jsonObject.getString("bairro"),
                        jsonObject.getString("localidade"),
                        jsonObject.getString("uf"),
                        jsonObject.getString("ibge"),
                        jsonObject.getString("gia"),
                        jsonObject.getString("ddd"),
                        jsonObject.getString("siafi")
                    )
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            connection.disconnect()
        }
    }
}
