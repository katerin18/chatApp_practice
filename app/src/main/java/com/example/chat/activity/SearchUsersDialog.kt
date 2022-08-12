package com.example.chat.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.adapter.SearchProfileAdapter
import com.example.chat.model.Profile
import com.google.firebase.database.*

class searchUsersDialog : AppCompatActivity() {

    private lateinit var list_ : ArrayList<Profile>
    private lateinit var recyclerView: RecyclerView
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_users_dialog)

        recyclerView = findViewById(R.id.rec_searchUsers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        list_ = arrayListOf<Profile>()
        getUserData()
    }

    fun getUserData(){
        dbref = FirebaseDatabase.getInstance().getReference("Users")  // обращаемся к БД для получения списка пользователей
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) { // what is snapshot??
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(Profile::class.java)
                        list_.add(user!!)
                    }
                    recyclerView.adapter = SearchProfileAdapter(list_)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", "Something went wrong!")
            }
        })
    }
}