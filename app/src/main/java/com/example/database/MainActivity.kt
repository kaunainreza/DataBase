package com.example.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database.model.adapter.TaskListAdapter
import com.example.database.database.DatabaseHelper
import com.example.database.model.TaskListModel


class MainActivity : AppCompatActivity() {
    lateinit var recycler_task: RecyclerView
    lateinit var bt_add_item : Button
    var taskListAdapter : TaskListAdapter?= null
    var dbHandler : DatabaseHelper?= null
    var tasklist : List<TaskListModel> = ArrayList<TaskListModel>()
    var linearLayoutManager: LinearLayoutManager ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_task = findViewById(R.id.rv_list)
        bt_add_item = findViewById(R.id.ft_add_item)

        dbHandler = DatabaseHelper(this)
        fetchlist()
        bt_add_item.setOnClickListener {
            val i = Intent(this@MainActivity,AddTask::class.java)
            startActivity(i)
        }
    }
    private fun fetchlist(){
        tasklist = dbHandler !!.getAllTask()
        taskListAdapter= TaskListAdapter(tasklist,applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        recycler_task.layoutManager = linearLayoutManager
        recycler_task.adapter = taskListAdapter
        taskListAdapter?.notifyDataSetChanged()
    }
}