package com.example.database

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.database.database.DatabaseHelper
import com.example.database.model.TaskListModel

class AddTask : AppCompatActivity() {
    lateinit var btn_save :Button
    lateinit var btn_del : Button
    lateinit var et_name :EditText
    lateinit var et_detail :EditText
    var dbHandler: DatabaseHelper?= null
    var isEditMode : Boolean = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_task_list)

        btn_save = findViewById(R.id.btn_save)
        btn_del = findViewById(R.id.btn_del)
        et_name = findViewById(R.id.et_name)
        et_detail = findViewById(R.id.et_detail)

        dbHandler = DatabaseHelper(this@AddTask)

        if (intent!= null &&intent.getStringExtra("Mode") == "E"){
            //For Update data
            isEditMode = true
            btn_save.text= "Update Data"
            btn_del.visibility= View.VISIBLE
            val tasks: TaskListModel = dbHandler!!.getTask(intent.getIntExtra("Id",0))
            et_name.setText(tasks.name)
            et_detail.setText(tasks.details)
        }else{
            // For insert new data
            isEditMode = false
            btn_save.text= "Save Data"
            btn_del.visibility= View.GONE
        }
        btn_save.setOnClickListener{
            var success: Boolean = false
            val tasks : TaskListModel = TaskListModel()
            if (isEditMode){
                //Update
                tasks.id =intent.getIntExtra("Id",0)
                tasks.name = et_name.text.toString()
                tasks.details = et_detail.text.toString()

                success = dbHandler?.updateTask(tasks) as Boolean

            }else{
                //Insert
                tasks.name = et_name.text.toString()
                tasks.details = et_detail.text.toString()

                success = dbHandler?.addTask(tasks) as Boolean
            }
            if (success){
                val i = Intent(applicationContext,MainActivity::class.java)
                startActivity(i)
                finish()
            }else{
                Toast.makeText(applicationContext,"Something went wrong!!", Toast.LENGTH_LONG).show()
            }
        }
        btn_del.setOnClickListener{
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click Yes If You " +
                    "Want To Delete The Task").setPositiveButton("yes",{dialog, i->
                val success = dbHandler?.deleteTask(intent.getIntExtra("Id",0))
                if (success == true) finish()
                dialog.dismiss()}).setNegativeButton("No",{dialog , i->dialog.dismiss()})



            dialog.show()

        }
    }
}