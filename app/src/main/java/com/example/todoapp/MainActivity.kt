package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_adding_new_task.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var adapter=TaskAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter=adapter

        addBtn.setOnClickListener {
            val intent=Intent(this,AddingNewTask::class.java)
            startActivity(intent)
        }

        getAllPost()
        var itemTouchHelper=ItemTouchHelper(SwipeToDelete(adapter,this,this))
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

        private fun getAllPost() {


            var result:ArrayList<Task> = arrayListOf()

            db.collection("task").addSnapshotListener { value, error ->
                if(error!=null){
                    Toast.makeText(this,error.message.toString(),Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                db.collection("task").get().addOnSuccessListener {
                    it.documents.forEach {doc ->
                        val model= doc.toObject(Task::class.java)
                        model?.let {
                            result.add(model)
                        }
                    }
                    adapter.models=result
                    Log.d("malumotlar",result.toString())

                }
            }
        }
    }