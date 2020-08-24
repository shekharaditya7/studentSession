package com.example.studentsessionapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsessionapp.R
import com.example.studentsessionapp.models.Session
import kotlinx.android.synthetic.main.layout_session_list_item.view.*
import kotlinx.android.synthetic.main.layout_session_list_subscribed_item.view.*

class SessionRecyclerAdapter(var items:ArrayList<Session>, var clickListener: OnSessionItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SessionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_session_list_item,parent,false)
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SessionViewHolder -> {
                holder.bind(items.get(position),clickListener)

            }
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }


    class SessionViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val sessionTitle = itemView.session_title
        val sessionDescription =itemView.session_description
        fun bind(session : Session , action:OnSessionItemClickListener){
            sessionTitle.setText(session.title)
            sessionDescription.setText(session.description)
            itemView.setOnClickListener{
                action.onItemClick(session,adapterPosition)
            }
            itemView.btn_delete_session.setOnClickListener(){
                action.onDeleteClick(session,adapterPosition)
            }

        }
    }
    interface OnSessionItemClickListener{
        fun onItemClick(item:Session , position: Int )
        fun onDeleteClick(item: Session,position: Int)
    }

}