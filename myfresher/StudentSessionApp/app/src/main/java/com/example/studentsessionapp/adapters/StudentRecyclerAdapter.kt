package com.example.studentsessionapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsessionapp.R
import com.example.studentsessionapp.models.Student
import kotlinx.android.synthetic.main.layout_student_list_item.view.*


class StudentRecyclerAdapter(var items:ArrayList<Student>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StudentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_student_list_item,parent,false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is StudentViewHolder -> {
                holder.bind(items.get(position))

            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }


    class StudentViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val studentUsername = itemView.student_username
        val studentName =itemView.student_name
        fun bind(student : Student ){
            studentUsername.setText(student.username)
            studentName.setText(student.name)
        }
    }
}