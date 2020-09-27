package com.example.todoapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_adding_new_task.*
import kotlinx.android.synthetic.main.activity_adding_new_task.etDeadline
import kotlinx.android.synthetic.main.activity_adding_new_task.etDesc
import kotlinx.android.synthetic.main.activity_adding_new_task.etTask
import kotlinx.android.synthetic.main.activity_adding_new_task.progressBarAdding
import kotlinx.android.synthetic.main.activity_editing_task.*
import java.util.*

class EditingTask : AppCompatActivity(),TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{


    private val db = FirebaseFirestore.getInstance()
    private var currentTask =""
    private var currentNote =0
    private var adapter=TaskAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editing_task)
        currentTask= intent.getStringExtra("taskId")?:  ""
        currentNote= intent.getIntExtra("noteId",0)
        save.setOnClickListener {
            updateTask(currentTask)
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        pickData()

        delete.setOnClickListener {
            deleteTask(currentTask)
            adapter.removeNote(currentNote)
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun deleteTask(id:String){
        db.collection("task").document(id).delete()
    }

    private fun removeRecyclerViewItem(){

    }

    private fun updateTask(id:String){
        if(!etTask.text.isNullOrEmpty()&&!etDesc.text.isNullOrEmpty()&&!etDeadline.text.isNullOrEmpty()){
            val map:MutableMap<String,Any> = mutableMapOf()
            map["taskName"]=etTask.text.toString()
            map["taskDescription"]=etDesc.text.toString()
            map["Deadline"]= etDeadline.text.toString()
            db.collection("task").document(id).set(map)
                .addOnSuccessListener {
                    Toast.makeText(this,"Your note is updated ", Toast.LENGTH_SHORT).show()
                    setLoading(false)
                }
                .addOnFailureListener {e->
                    Toast.makeText(this,e.localizedMessage!!, Toast.LENGTH_LONG).show()
                    setLoading(false)
                }
        }
        else
            Toast.makeText(this,"Please fill fields ", Toast.LENGTH_SHORT).show()
    }

    private fun pickData() {
        timepickerEdit.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this,this,year,month,day).show()
        }
    }

    private fun getDateTimeCalendar(){
        var cal =Calendar.getInstance()
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        day=cal.get(Calendar.DAY_OF_MONTH)
        hour=cal.get(Calendar.HOUR)
        minute=cal.get(Calendar.MINUTE)
    }

    private fun setLoading(isLoading:Boolean){
        progressBarAdding.visibility= if(isLoading)  View.VISIBLE else View.GONE
        etDeadline.isEnabled=!isLoading
        etDesc.isEnabled=!isLoading
        etTask.isEnabled=!isLoading
        create.isEnabled=!isLoading
        cancel.isEnabled=!isLoading

    }
    //Date picker code
    var year =0
    var month =0
    var day =0
    var hour =0
    var minute =0

    var savedYear =0
    var savedMonth =0
    var savedDay =0
    var savedHour =0
    var savedMinute =0

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour=hourOfDay
        savedMinute=minute
        etDeadline.text="$savedDay-$savedMonth-$savedYear \n Hour: $savedHour  Minute: $savedMinute"
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay=dayOfMonth
        savedMonth=month
        savedYear=year

        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute,true).show()
    }
}


