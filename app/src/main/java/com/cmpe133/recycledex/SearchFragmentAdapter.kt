package com.cmpe133.recycledex
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class SearchFragmentAdapter(private val articleList : ArrayList<Article>) : RecyclerView.Adapter<SearchFragmentAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvArticleTitle)
        val author: TextView = itemView.findViewById(R.id.tvArticleAuthor)
        val description: TextView = itemView.findViewById(R.id.tvArticleDescription)
        /**
        fun bind(art: Article, context: Context) {
            title.text = art.title
            author.text = art.author
            author.text = art.description
        }
        **/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_articles, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
        val currentitem = articleList[position]
        holder.title.text = currentitem.title
        holder.author.text = currentitem.author
        holder.description.text = currentitem.description
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return 10
    }

}
