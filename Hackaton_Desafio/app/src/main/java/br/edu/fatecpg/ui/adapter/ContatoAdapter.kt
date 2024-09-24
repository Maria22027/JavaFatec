package br.edu.fatecpg.ui.adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.fatecpg.ui.R
import br.edu.fatecpg.ui.model.Contato
import com.bumptech.glide.Glide

class ContatoAdapter(private val contatos:List<Contato>): RecyclerView.Adapter<ContatoAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtNomeC = itemView.findViewById<TextView>(R.id.txt_nomeC)
        val txtWhatsC = itemView.findViewById<TextView>(R.id.txt_whatsC)
        val imgC = itemView.findViewById<ImageView>(R.id.imgC)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contato,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contatos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contato = contatos[position]



        holder.txtNomeC.text = contato.nome
        holder.txtWhatsC.text = contato.number
        // Usando Glide para carregar a imagem
        contato.img?.let { imageUri ->
            Glide.with(holder.itemView.context)
                .load(imageUri)
                .into(holder.imgC)
        }
    }
}
