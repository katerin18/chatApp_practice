package com.example.chat.adapter

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.Receiver.AlarmReceiver
import com.example.chat.activity.MainActivity
import com.example.chat.model.Message
import com.example.chat.util.sendNotification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_show_chat.*


class showChat : AppCompatActivity() {

    var chatList = ArrayList<Message>()
    var firebaseUser: FirebaseUser? = null
    var topic = ""
    lateinit var notifyPendingIntent: PendingIntent
    private val REQUEST_CODE = 0
    var message = "HI"
//    val switchNotif: SwitchCompat = findViewById(R.id.switch_notif)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_chat)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        recListChat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val butBack: ImageView = findViewById(R.id.imBack)
        val nickname = intent.getStringExtra("recNick")
        val senderNick = intent.getStringExtra("sendNick")
        val userId = intent.getStringExtra("idRec")
        val sendId = Firebase.auth.currentUser!!.uid

        titleNick.text = nickname

        butBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val notifyIntent = Intent(application, AlarmReceiver::class.java)

        notifyPendingIntent = PendingIntent.getBroadcast(
            application,
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        Log.d("meoww", "Application is $application")
        btnSendMessage.setOnClickListener {
            message = inputMessage.text.toString().trim()
            val receiveName: String = nickname!!
            val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (message.isNotEmpty()) {
                sendMessage(sendId, Firebase.auth.currentUser!!, userId!!, receiveName, message)

                if(firebaseUser?.uid  == userId){
                    // TODO: 4. Get an instance of NotificationManager and call sendNotification
                    val notificationManager = ContextCompat.getSystemService(
                        application,

                        NotificationManager::class.java
                    ) as NotificationManager
                    notificationManager.sendNotification(message, senderNick!!, application)

                    // TODO: 10. Cancelling notifications
                    //   notificationManager.cancelNotifications()

                    AlarmManagerCompat.setExactAndAllowWhileIdle(
                        alarmManager,
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        0,
                        notifyPendingIntent
                    )
                }

                // если receiverId == currentId, то показываем уведомление получателю.
                // учесть, что к этому надо дописать условие включенного switch'а

                inputMessage.setText("")
                topic = "$userId"
                println("RECEIVER ID = $userId")
            } else {
                Toast.makeText(this, "Put your message before sending", Toast.LENGTH_SHORT).show()
            }
        }
        readMessage(sendId, userId!!)
    }

    fun app(): Application? {
        println("APPLICATION = $application")
        return application
    }
    val a = app()



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
                val senderName = snapshot.child("userName").value.toString()
                println("ANS ${senderName}")
                val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
                val hashMap: HashMap<String, String> = HashMap()

                hashMap["senderId"] = senderId
                hashMap["senderName"] = senderName
                hashMap["receiverId"] = receiverId
                hashMap["receiverName"] = receiverName
                hashMap["txtMessage"] = txtMessage

                println("text = $txtMessage receiverId = $receiverId")

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