package com.example.studentsessionapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsessionapp.adapters.SubscribeRecyclerAdapter
import com.example.studentsessionapp.models.Session
import com.example.studentsessionapp.models.Student
import com.example.studentsessionapp.services.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_subscribed_session.*
import java.time.LocalDate

class SubscribedSessionActivity : AppCompatActivity(),SubscribeRecyclerAdapter.OnSessionItemClickListener {
    private lateinit var student: Student
    private var client = ApiClient()
    private lateinit var username:String
    private lateinit var name:String
    private lateinit var session_subscribed: ArrayList<Session>
    private lateinit var session_subscribed_int: ArrayList<Int>
    private var is_admin:Boolean=false
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribed_session)
        val bundle:Bundle?=intent.extras
        username = bundle!!.get("USERNAME").toString().trim()
        name = bundle.get("NAME").toString().trim()
        session_subscribed = intent.getSerializableExtra("Subscribed") as ArrayList<Session>
        session_subscribed_int= intent.getIntegerArrayListExtra("SUBS_SESSION_INT") as ArrayList<Int>
        rcv_subscribed!!.layoutManager = LinearLayoutManager(this)

        setRecyclerViewAdapter(session_subscribed)
        rcv_subscribed.addItemDecoration(TopSpacingItemDecorator(30))

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerViewAdapter(list : List <Session>) {
        val session_filtered =list.filter { session -> LocalDate.parse(session.session_date).isAfter(
            LocalDate.now())
        }
        rcv_subscribed.adapter = SubscribeRecyclerAdapter(session_filtered as ArrayList<Session>, this)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDeleteClick(item: Session, position: Int) {
        val final_session = session_subscribed.filterNot { session -> session.id == item.id }
        this.setRecyclerViewAdapter(final_session)
        val subscribed_int=session_subscribed_int.filterNot {itr -> itr == item.id}

        student = Student(username =username,name = name,isadmin = is_admin,student_session_list = subscribed_int)
        client.updateStudentSessionList(username,student).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                error-> Log.e("Error","Error aa gaya")

            }
    }
}