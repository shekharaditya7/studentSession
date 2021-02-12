package com.example.studentsessionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studentsessionapp.models.Student
import com.example.studentsessionapp.services.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var client=ApiClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener(){
            val username = et_username.text.toString().trim()
            client.loadStudentDataByUsername(username).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        }
    }
    private fun handleResponse(student: Student){
        val is_admin = student.isadmin
        if(is_admin == true)
        {
            var intent=Intent(this,AdminActivity::class.java)
            startActivity(intent)
        }
        else
        {
            var intent = Intent(this,StudentActivity::class.java)
            intent.putExtra("USERNAME",student.username)
            startActivity(intent)
        }
    }
}