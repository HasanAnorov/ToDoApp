package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_adding_new_task.*
import kotlinx.android.synthetic.main.activity_main.*

class AddingNewTask : AppCompatActivity() {

    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_new_task)

    create.setOnClickListener {
        setLoading(true)
        if(!etTask.text.isNullOrEmpty()&&!etDesc.text.isNullOrEmpty()&&!etDeadline.text.isNullOrEmpty()){
         val map:MutableMap<String,Any> = mutableMapOf()
            map["taskName"]=etTask.text.toString()
            map["taskDescription"]=etDesc.text.toString()
            map["Deadline"]= etDeadline.text.toString()
            db.collection("task").document().set(map)
                .addOnSuccessListener {
                    Toast.makeText(this,"Your note is created ",Toast.LENGTH_SHORT).show()
                    setLoading(false)
                }
                .addOnFailureListener {e->
                    Toast.makeText(this,e.localizedMessage!!,Toast.LENGTH_LONG).show()
                    setLoading(false)
                }
        }
    }

    }
    private fun setLoading(isLoading:Boolean){
        progressBarAdding.visibility= if(isLoading)  View.VISIBLE else View.GONE
        etDeadline.isEnabled=!isLoading
        etDesc.isEnabled=!isLoading
        etTask.isEnabled=!isLoading
        create.isEnabled=!isLoading
        cancel.isEnabled=!isLoading

    }
}