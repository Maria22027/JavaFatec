package br.edu.fatecpg.ui.dao

import br.edu.fatecpg.ui.model.Contato

class ContatoDao {
    companion object{
        private val contatos:MutableList<Contato> = mutableListOf()
    }

    fun adicionarContato(contato: Contato){
        contatos.add(contato)
    }

    fun consultarContato():List<Contato>{
        return contatos
    }
}