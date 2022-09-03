package com.example.studentsessionapp

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_admin.*
class AdminActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        btn_session_list.setOnClickListener(){
        var intent = Intent(this,SessionListActivity::class.java)
            startActivity(intent)

        }
//        btn_student_list.setOnClickListener(){
//            var intent = Intent(this,StudentListActivity::class.java)
//            startActivity(intent)
//        }

    }


}