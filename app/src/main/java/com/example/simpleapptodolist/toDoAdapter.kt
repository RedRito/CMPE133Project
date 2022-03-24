package com.example.simpleapptodolist


import android.graphics.Paint
import android.icu.text.CaseMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import org.w3c.dom.Text


class toDoAdapter (
    private val todos: MutableList<ToDo>) : RecyclerView.Adapter<toDoAdapter.TodoViewHolder>(){

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    private fun toggleStrikeThrough(tvToDoTitle: TextView, isChecked: Boolean)
    {
        if(isChecked)
        {
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        }
        else
        {
            tvToDoTitle.paintFlags = tvToDoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curToDO = todos[position]
        holder.itemView.apply {
            tvToDoTitle.text = curToDO.title
            cbDone.isChecked = curToDO.isChecked
            toggleStrikeThrough(tvToDoTitle, curToDO.isChecked)
            cbDone.setOnCheckedChangeListener{_, isChecked -> toggleStrikeThrough(tvToDoTitle, isChecked)}
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}