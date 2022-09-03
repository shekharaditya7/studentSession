package com.example.studentsessionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsessionapp.adapters.StudentRecyclerAdapter
import com.example.studentsessionapp.models.Session
import com.example.studentsessionapp.models.Student
import com.example.studentsessionapp.services.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_session_detail.*

class SessionDetailActivity : AppCompatActivity() {
    private lateinit var student_Array: ArrayList<Student>
    private var client = ApiClient()
    private lateinit var studentadapter: StudentRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_detail)
//        setSupportActionBar(toolbar2)
//        toolbar2.title = getString(R.string.activity2_title)
        val bundle:Bundle?=intent.extras
        val sessionId =bundle!!.getInt("session_id")
        recycler_view_student!!.layoutManager = LinearLayoutManager(this)
        recycler_view_student.addItemDecoration(TopSpacingItemDecorator(30))
        client.loadSessionDataByID(sessionId).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse)



    }
    private fun handleResponse(item: Session){
        student_Array= item.students as ArrayList<Student>
        textView_title.setText(item.title)
        textView_description.setText(item.description)
        textView_date.setText(item.session_date)
        var slot:String=""
        if(item.slots == "1")
            slot="Time 9:00 am to 11:00 am"
        else if (item.slots=="2")
            slot="Time 11:00 am to 1:00 pm"
        else if(item.slots=="3")
            slot="Time 3:00 pm to 5:00 pm"
//        Toast.makeText(this, slot, Toast.LENGTH_SHORT).show()
        textView_slot.setText(slot)
        studentadapter = StudentRecyclerAdapter(student_Array)
        //setting the adapter

        recycler_view_student.adapter = studentadapter


    }
}