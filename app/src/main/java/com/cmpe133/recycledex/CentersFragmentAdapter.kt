package com.cmpe133.recycledex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CentersFragmentAdapter(private val centersList : ArrayList<Centers>) : RecyclerView.Adapter<CentersFragmentAdapter.MyViewHolder>(){
    private lateinit var mListener: onItemClickListener

    //onitem click interface
    interface onItemClickListener{

        fun onItemClick(position: Int)

    }
    //method that sets the listener
    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }


    inner class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

       //gets and intializes variables from layout
        val name : TextView = itemView.findViewById(R.id.tvCenterName)
        val address : TextView = itemView.findViewById(R.id.tvAddress)
        val hours : TextView = itemView.findViewById(R.id.tvHours)
        val num : TextView = itemView.findViewById(R.id.tvPhoneNum)
        //on initialize
        init {

            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }

    }

    //gets the view from layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.centers_card, parent, false)
        return MyViewHolder(itemView, mListener)
    }
    //when given the arraylist, bind / change the data
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = centersList[position]
        holder.name.text = currentitem.name
        holder.address.text = currentitem.location
        holder.hours.text = currentitem.description
        holder.num.text = currentitem.phone
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return centersList.size
    }

}