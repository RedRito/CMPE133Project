package com.cmpe133.recycledex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CentersFragmentAdapter(private val centersList : ArrayList<Centers>) : RecyclerView.Adapter<CentersFragmentAdapter.MyViewHolder>(){
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }


    inner class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        val title: TextView = itemView.findViewById(R.id.tvArticleTitle)
//        val author: TextView = itemView.findViewById(R.id.tvArticleAuthor)
//        val description: TextView = itemView.findViewById(R.id.tvArticleDescription)
        val name : TextView = itemView.findViewById(R.id.tvCenterName)
        val address : TextView = itemView.findViewById(R.id.tvAddress)
        val hours : TextView = itemView.findViewById(R.id.tvHours)
        val num : TextView = itemView.findViewById(R.id.tvPhoneNum)

        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.centers_card, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = centersList[position]
        holder.name.text = currentitem.name
        holder.address.text = currentitem.location
        holder.hours.text = currentitem.description
        holder.num.text = currentitem.phone
//        holder.title.text = currentitem.title
//        holder.author.text = currentitem.author
//        holder.description.text = currentitem.description
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return centersList.size
    }

}