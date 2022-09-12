package com.example.chat.fragments

//import com.example.chat.MyFirebaseInstanceIDService.Companion.sharedPref
import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.activity.searchUsersDialog
import com.example.chat.adapter.UserAdapter
import com.example.chat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.android.synthetic.main.notification.*

class ChatsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btn_newDialogs: Button
    private lateinit var dbref: DatabaseReference
    private val TOPIC = "Message"

    // var backTitle = view?.findViewById<TextView>(R.id.backTitle)
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var userlist = ArrayList<User>()

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // TODO: 6. Calling channel
        createChannel(
            getString(R.string.notif_channel_id),
            getString(R.string.notif_channel_name)
        )

        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.usersRecycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        getUserList()

        btn_newDialogs = view.findViewById(R.id.btn_newDialogs)
        btn_newDialogs.setOnClickListener {
            startActivity(Intent(activity, searchUsersDialog::class.java))
        }
    }

    fun getUserList(){

        dbref = FirebaseDatabase.getInstance().getReference("Chat")
        dbref.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                recyclerView.adapter = UserAdapter(requireActivity(), userlist) // fragment not attached to an activity.
                val setUsers = mutableMapOf<String, User>()

                if(snapshot.exists()){
                    for(dataSnapShot: DataSnapshot in snapshot.children){
                        val user = dataSnapShot.getValue(User::class.java)

                        if(user!!.senderId == userId) {
                            setUsers[user.receiverId] = user
                        }

                        else if(user.receiverId == userId){
                            user.receiverName = user.senderName
                            setUsers[user.senderId] = user
                        }
                        FirebaseMessaging.getInstance().subscribeToTopic("$userId")
                    }
                    for (usr in setUsers){
                        userlist.add(usr.value)
                    }
                    recyclerView.adapter = UserAdapter(requireActivity(), userlist)
                    if (setUsers.isNotEmpty()){
                        backTitle!!.text = ""
                        ch_back.setImageResource(0)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // TODO: 5. Creating channel
    private fun createChannel(channelId: String, channelName: String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.description = "Time to reading the message!"

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}