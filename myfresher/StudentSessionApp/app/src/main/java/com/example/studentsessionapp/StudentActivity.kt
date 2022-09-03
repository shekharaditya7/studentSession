package com.example.studentsessionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studentsessionapp.models.Session
import com.example.studentsessionapp.models.Student
import com.example.studentsessionapp.services.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {
    private lateinit var name:String
    private lateinit var username:String
    private  var client= ApiClient()
    private lateinit var student_session_list:List<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        val bundle:Bundle?=intent.extras
        username = bundle!!.get("USERNAME").toString().trim()
//        rv_student_session!!.layoutManager=LinearLayoutManager(this)
        client.loadStudentDataByUsername(username).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponseStudent)

    }
    private fun handleResponseStudent(student: Student){

        tv_name.setText(student.name)
        tv_username.setText(student.username)
        student_session_list=student.student_session_list
        name = student.name.toString()
        client.loadSessionData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponseSession)

    }
    override fun onResume() {
        super.onResume()

        client.loadStudentDataByUsername(username).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponseStudent)

    }

    private fun handleResponseSession(list : List<Session>){
        val session_subscribed = ArrayList(list.filter { session-> session_search(session.id)
        })
        val session_unsubscribed = ArrayList(list.filterNot { session -> session_search(session.id)})

        btn_session_subscribed.setOnClickListener(){
            val intent= Intent(this,SubscribedSessionActivity::class.java)
            intent.putExtra("Subscribed",session_subscribed)
            intent.putExtra("USERNAME",username)
            intent.putExtra("NAME",name)
            intent.putIntegerArrayListExtra("SUBS_SESSION_INT",student_session_list as ArrayList<Int>)
            startActivity(intent)
        }
        btn_session_unsubscribed.setOnClickListener(){
            val intent= Intent(this,UnsubscribedSessionActivity::class.java)
            intent.putExtra("UnSubscribed",session_unsubscribed)
            intent.putExtra("USERNAME",username)
            intent.putExtra("NAME",name)
            intent.putExtra("Subscribed",session_subscribed)
            intent.putIntegerArrayListExtra("SUBS_SESSION_INT",student_session_list as ArrayList<Int>)
            startActivity(intent)
        }
    }
    private fun session_search(id:Int): Boolean {
        for (i in student_session_list){
            if(i==id)
                return true
        }
        return false

    }
}