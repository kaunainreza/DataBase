package com.example.database.model.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.database.AddTask
import com.example.database.R
import com.example.database.model.TaskListModel

class TaskListAdapter (tasksList : List<TaskListModel>, internal var context: Context)
    :RecyclerView.Adapter<TaskListAdapter.TaskListHolder>(){
    internal var tasksList: List<TaskListModel> = ArrayList()
    init {
        this.tasksList = tasksList
    }


    inner class TaskListHolder (view: View) : RecyclerView.ViewHolder(view) {
        var name : TextView = view.findViewById(R.id.txt_name)
        var details : TextView = view.findViewById(R.id.txt_detail)
        var btn_edit: Button = view.findViewById(R.id.btn_edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_task_list,parent,false)
        return TaskListHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListHolder, position: Int) {
        val tasks = tasksList[position]
        holder.name.text = tasks.name
        holder.details.text = tasks.details

        holder.btn_edit.setOnClickListener {
            val i = Intent(context, AddTask::class.java)
            i.putExtra("Mode","E")
            i.putExtra("Id",tasks.id)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

}