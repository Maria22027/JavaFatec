package br.edu.fatecpg.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.fatecpg.ui.adapter.ContatoAdapter
import br.edu.fatecpg.ui.dao.ContatoDao

class ListaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        val dao = ContatoDao()

        val recyclerView = findViewById<RecyclerView>(R.id.rv_Lista)
        val contatos = dao.consultarContato()
        recyclerView.adapter = ContatoAdapter(contatos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }