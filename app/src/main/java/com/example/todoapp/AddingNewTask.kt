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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_adding_new_task.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.YearMonth
import java.util.*

class AddingNewTask : AppCompatActivity(),TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {

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
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {e->
                    Toast.makeText(this,e.localizedMessage!!,Toast.LENGTH_LONG).show()
                    setLoading(false)
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
        }
        else
            Toast.makeText(this,"Please fill fields ",Toast.LENGTH_SHORT).show()
    }
        pickData()
    }

    private fun pickData() {
        timepicker.setOnClickListener {
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