package com.example.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat.R
import com.example.chat.fragments.ChatsFragment
import com.example.chat.model.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val context: Context, private val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUsername.text = user.userName
        Glide.with(context).load(user.userImage).into(holder.imgUser)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val txtUsername: TextView = view.findViewById(R.id.userName)
        val txtTemp: TextView = view.findViewById(R.id.temp)
        val imgUser:CircleImageView = view.findViewById(R.id.userImage)

    }
}