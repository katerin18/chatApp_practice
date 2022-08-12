package com.example.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChatAdapter(private val listChat: ArrayList<Message>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT = 1
    var firebaseUser : FirebaseUser? = null

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtMessageField: TextView = view.findViewById(R.id.txt_mess)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == MESSAGE_TYPE_RIGHT){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_right_item, parent, false)
            return ViewHolder(view)
        }
        else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_left_item, parent, false)
            return ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = listChat[position]

        holder.txtMessageField.text = chat.txtMessage
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (listChat[position].senderId == firebaseUser!!.uid){
            return MESSAGE_TYPE_RIGHT
        }
        else return MESSAGE_TYPE_LEFT
    }
}