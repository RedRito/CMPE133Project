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

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }


    inner class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvArticleTitle)
        val author: TextView = itemView.findViewById(R.id.tvArticleAuthor)
        val description: TextView = itemView.findViewById(R.id.tvArticleDescription)

        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_articles, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = articleList[position]
        holder.title.text = currentitem.title
        holder.author.text = currentitem.author
        holder.description.text = currentitem.description
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}
