package com.example.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat.R
import com.example.chat.fragments.ChatsFragment
import com.example.chat.model.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val context: Context, private val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var receiverName: TextView = view.findViewById(R.id.ch_userName)
        var receiverId = "Receiver Id"
        var userMes: TextView = view.findViewById(R.id.ch_Mes)
        var userImage: ImageView = view.findViewById(R.id.ch_userImage)
        var userId = "Sender id"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.userId = user.senderId
        holder.userImage.setImageResource(R.drawable.im_user)
        holder.userMes.text = user.txtMessage
        holder.receiverName.text = user.receiverName
        holder.receiverId = user.receiverId
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}