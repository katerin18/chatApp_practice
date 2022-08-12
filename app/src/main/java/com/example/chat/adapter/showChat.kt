package com.example.chat.adapter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.activity.MainActivity
import com.example.chat.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_show_chat.*
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.coroutines.sync.Semaphore

class showChat : AppCompatActivity() {

    var chatList = ArrayList<Message>()
    var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_chat)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        recListChat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val butBack: ImageView = findViewById(R.id.imBack)
        val nickname = intent.getStringExtra("recNick")
        val userId = intent.getStringExtra("idRec")
        val sendId = Firebase.auth.currentUser!!.uid

        titleNick.text = nickname

        butBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSendMessage.setOnClickListener {
            val message: String = inputMessage.text.toString()
            val receiveName: String = nickname!!

            if (message.isNotEmpty()) {
                sendMessage(sendId, Firebase.auth.currentUser!!, userId!!, receiveName, message)
                inputMessage.setText("")
            } else {
                Toast.makeText(this, "Put your message before sending", Toast.LENGTH_SHORT).show()
            }
        }

        readMessage(sendId, userId!!)
    }

    fun sendMessage(
        senderId: String,
        firebaseUser: FirebaseUser,
        receiverId: String,
        receiverName: String,
        txtMessage: String
    ) {
        val userID: String = firebaseUser.uid

        val referenceProfile: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users")
        referenceProfile.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val senderName = snapshot.child("userName   ").value.toString()
                println("ANS ${senderName}")
                val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
                val hashMap: HashMap<String, String> = HashMap()

                hashMap["senderId"] = senderId
                hashMap["senderName"] = senderName
                hashMap["receiverId"] = receiverId
                hashMap["receiverName"] = receiverName
                hashMap["txtMessage"] = txtMessage

                reference.child("Chat").push().setValue(hashMap)

            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(activity, "Something wnt wrong!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun readMessage(senderId: String, receiverId: String) {

        val dbref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chat")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()

                for (userSnapshot in snapshot.children) {
                    println(userSnapshot.value.toString())
                    val chat = userSnapshot.getValue(Message::class.java)

                    if (chat!!.senderId.equals(senderId) && chat.receiverId.equals(receiverId) ||
                        chat.senderId.equals(receiverId) && chat.receiverId.equals(senderId)
                    ) {
                        chatList.add(chat)
                    }
                }
                val chatAdapter = ChatAdapter(chatList)
                recListChat.adapter = chatAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}