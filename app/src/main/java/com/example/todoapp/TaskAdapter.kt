package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter:RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var onItemClicked :(model:Task)->Unit ={}
    private var deleteItem :(item:Int)->Unit ={}

    fun setOnItemClickListener(onItemClicked: (model:Task) -> Unit = {} ) {
        this.onItemClicked=onItemClicked
    }

    fun removeRecyclerViewItem(onItemClicked: (model:Int) -> Unit = {} ) {
        this.deleteItem=onItemClicked
    }

    fun removeNote(note:Int){
        models.removeAt(note)
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun populateModel(model: Task){

            itemView.title.text=model.taskName
            itemView.description.text=model.taskDescription
            itemView.deadline.text=model.Deadline
            itemView.setOnClickListener {
                onItemClicked.invoke(model)
                deleteItem.invoke(adapterPosition)
            }
        }
    }

    var models:ArrayList<Task> = arrayListOf()
        set(value) {
            field=value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int=models.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

}