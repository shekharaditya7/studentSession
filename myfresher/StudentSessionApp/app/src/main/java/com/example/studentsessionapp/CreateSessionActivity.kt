package com.example.studentsessionapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import com.example.studentsessionapp.models.Session
import com.example.studentsessionapp.services.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_session.*
import java.text.SimpleDateFormat
import java.util.*

class CreateSessionActivity : AppCompatActivity() ,DatePickerDialog.OnDateSetListener{
    private var client = ApiClient()
    private lateinit var newSession: Session
    private var selected_slot: Long = 0
    var formate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_session)
//        setSupportActionBar(toolbar3)
//        toolbar3.title=getString(R.string.activity3_title)
        sp_slot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selected_slot = adapterView?.getItemIdAtPosition(position)!!
                selected_slot = selected_slot + 1

            }

        }
        pk_date.setOnClickListener {

            val calendar: Calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val now =calendar.timeInMillis
            val datePickerDialog =
                DatePickerDialog(this@CreateSessionActivity, this@CreateSessionActivity, year, month, day)
            datePickerDialog.datePicker.minDate =now - 1000
            datePickerDialog.show()

        }

        button_session_create.setOnClickListener{
            newSession=Session()
            newSession.title=et_title.text.toString()
            newSession.description=et_description.text.toString()
            newSession.session_date=tv_date.text.toString()
            newSession.slots=selected_slot.toString()

            client.addNewSession(newSession).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    finish()
                }
        }


    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val month_year=month+1
        tv_date.setText("$year-$month_year-$day")
    }
}