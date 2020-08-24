package com.example.studentsessionapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsessionapp.adapters.SessionRecyclerAdapter
import com.example.studentsessionapp.models.Session
import com.example.studentsessionapp.services.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_session_list.*
import java.time.LocalDate

class SessionListActivity : AppCompatActivity() , SessionRecyclerAdapter.OnSessionItemClickListener{
    private lateinit var mAndroidArrayList: ArrayList<Session>
    private var client = ApiClient()
    private lateinit var sessionadapter: SessionRecyclerAdapter
    private lateinit var session_list : List<Session>

    //    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_list)
//        setSupportActionBar(toolbar)
//        this.toolbar.title=title
        recycler_view!!.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(TopSpacingItemDecorator(30))
        client.loadSessionData().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse)
        val fab : View = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener(){
            var intent = Intent(this,CreateSessionActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()

        client.loadSessionData().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleResponse(list: List<Session>) {
        session_list =list.filter { session ->(LocalDate.parse(session.session_date).isAfter(
            LocalDate.now()) || LocalDate.parse(session.session_date).isEqual(LocalDate.now()))
        }
        mAndroidArrayList = ArrayList(session_list)

        sessionadapter = SessionRecyclerAdapter(mAndroidArrayList, this)
        //setting the adapter

        recycler_view.adapter = sessionadapter

    }

    override fun onItemClick(item: Session, position: Int) {

        var intent = Intent(this,SessionDetailActivity::class.java)
        intent.putExtra("session_id",item.id)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDeleteClick(item: Session, position: Int) {
        client.deleteSessionById(item.id).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()


//                finish()
//                error-> Log.e("Error","Error aa gaya")

        session_list = session_list.filterNot{ itr-> itr.id == item.id  }
        if (session_list.isNullOrEmpty()){
            Toast.makeText(this,"Sessiion list is empty",Toast.LENGTH_SHORT).show()
//            finish()
        }
        handleResponse(this.session_list)
    }
}