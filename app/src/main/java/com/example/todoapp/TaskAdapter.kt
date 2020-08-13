package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter:RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        fun populateModel(model: Task){

            itemView.title.text=model.taskName
            itemView.description.text=model.taskDescription
            itemView.deadline.text=model.Deadline.toString()
        }
    }

    var models:List<Task> = listOf()
        set(value) {
            field=value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task,null,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int=models.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

}