package com.example.chat.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SearchProfileAdapter(private val list_ : ArrayList<Profile>) : RecyclerView.Adapter<SearchProfileAdapter.ProfilesViewHolder>() {

    class ProfilesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtNick: TextView = itemView.findViewById(R.id.searchName)
        val profIm: ImageView = itemView.findViewById(R.id.searchImage)
        var profId: String = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ProfilesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfilesViewHolder, position: Int) {
        val currentUser = list_[position]

        holder.txtNick.text = currentUser.userName
        holder.profIm.setImageResource(R.drawable.im_user)
        holder.profId = currentUser.userId

        println("RECEIVER ID = ${currentUser.userId}")

        openChat(holder, currentUser.userName, currentUser.userId, FirebaseAuth.getInstance().currentUser.toString())
    }

    override fun getItemCount(): Int {
        return list_.size
    }


    fun openChat(holder: ProfilesViewHolder, nickname : String, id: String, sendNick : String){
        val view = holder.itemView

        println("CUR USER = ${FirebaseAuth.getInstance().currentUser.toString()}")

        view.setOnClickListener {
            val activity = holder.itemView.context as Activity
            val i = Intent(activity, showChat::class.java)

            i.putExtra("recNick", nickname)
            i.putExtra("sendNick", sendNick)
            i.putExtra("idRec", id)
            activity.startActivity(i)
        }
    }
}