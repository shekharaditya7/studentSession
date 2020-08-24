package com.example.studentsessionapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsessionapp.R
import com.example.studentsessionapp.models.Session
import kotlinx.android.synthetic.main.layout_session_list_unsubscribed_item.view.*


class UnSubscribedRecyclerAdapter(var items:ArrayList<Session>, var clickListener: OnSessionItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SessionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_session_list_unsubscribed_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SessionViewHolder -> {
                holder.bind(items.get(position), clickListener)

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SessionViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val sessionTitle = itemView.session_title_un
        val sessionDescription = itemView.session_description_un
        val sessionDate = itemView.session_date_un
        val sessionSlot = itemView.session_slot_un
        fun bind(
            session: Session,
            action: OnSessionItemClickListener
        ) {
            sessionTitle.setText(session.title)
            sessionDescription.setText(session.description)
            sessionDate.setText(session.session_date)
            if (session.slots == "1")
                sessionSlot.setText("Time 9:00 am to 11:00 am")
            else if (session.slots == "2")
                sessionSlot.setText("Time 11:00 am to 1:00 pm")
            else if (session.slots == "3")
                sessionSlot.setText("Time 3:00 pm to 5:00 pm")
            itemView.btn_add.setOnClickListener() {
                action.onDeleteClick(session, adapterPosition)
            }
        }
    }

    interface OnSessionItemClickListener {

        fun onDeleteClick(item: Session, position: Int)
    }
}