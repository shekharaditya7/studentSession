package com.example.studentsessionapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsessionapp.adapters.UnSubscribedRecyclerAdapter
import com.example.studentsessionapp.models.Session
import com.example.studentsessionapp.models.Student
import com.example.studentsessionapp.services.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_unsubcribe_session.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.*
import kotlin.collections.ArrayList


class UnsubscribedSessionActivity : AppCompatActivity() , UnSubscribedRecyclerAdapter.OnSessionItemClickListener {
    private lateinit var student: Student
    private var client = ApiClient()
    private lateinit var username: String
    private lateinit var name:String
    private lateinit var session_unsubscribed: ArrayList<Session>
    private lateinit var session_subscribed_int: ArrayList<Int>
    private lateinit var session_subscribed:ArrayList<Session>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unsubcribe_session)
        val bundle: Bundle? = intent.extras
        username = bundle!!.get("USERNAME").toString().trim()
        name = bundle.get("NAME").toString().trim()
        session_subscribed_int = intent.getIntegerArrayListExtra("SUBS_SESSION_INT") as ArrayList<Int>
        session_unsubscribed = intent.getSerializableExtra("UnSubscribed") as ArrayList<Session>
        session_subscribed=intent.getSerializableExtra("Subscribed") as ArrayList<Session>
        rcv_unsubscribed!!.layoutManager = LinearLayoutManager(this)
        setRecyclerViewAdapter(session_unsubscribed)
        rcv_unsubscribed.addItemDecoration(TopSpacingItemDecorator(30))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setRecyclerViewAdapter(list : List <Session>) {
//        val sdf = SimpleDateFormat("YYYY-MM-DD", Locale.US)

        var session_filtered =list.filter { session -> (LocalDate.parse(session.session_date).isAfter(
            LocalDate.now()) || LocalDate.parse(session.session_date).isEqual(LocalDate.now()) )
        }
//        val date = sdf.parse(Date().toString())


//        var session_filtered =list.filter{session -> sdf.parse(session.session_date).after(date) }

        rcv_unsubscribed.adapter = UnSubscribedRecyclerAdapter(session_filtered as ArrayList<Session>, this)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDeleteClick(item: Session, position: Int) {
        val final_session_list = session_unsubscribed.filterNot { session -> session.id == item.id }
        val subs_session = session_unsubscribed.filter { session -> session.id == item.id }
        val checkSession = checkSubscribedSession(session_subscribed , subs_session[0])
        if (checkSession == true) {

            session_subscribed_int.add(item.id)
            session_subscribed.add(subs_session[0])
            student = Student(
                username = username,
                name=name,
                isadmin = false,
                student_session_list = session_subscribed_int
            )
            client.updateStudentSessionList(username, student).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { error ->
                    Log.e("Error", "Error aa gaya")
                }
            if (final_session_list.isNullOrEmpty())
            {
                Toast.makeText(this,"Sessiion list is empty",Toast.LENGTH_SHORT).show()
                finish()
            }
            session_unsubscribed= final_session_list as ArrayList<Session>
            this.setRecyclerViewAdapter(final_session_list)
        }
        else{
            Toast.makeText(this, "Same slot and same date session cannot be subscribed",Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkSubscribedSession(subscribed : ArrayList<Session> , subs_session:Session): Boolean {
            for ( i in subscribed){
                if(i.session_date == subs_session.session_date &&  i.slots == subs_session.slots)
                {
                    return false
                }
            }
        return true
    }
}