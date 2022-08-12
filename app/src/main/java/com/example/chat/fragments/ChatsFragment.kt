package com.example.chat.fragments
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.adapter.UserAdapter
import com.example.chat.model.User
import com.example.chat.activity.searchUsersDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_show_chat.*
import kotlinx.android.synthetic.main.fragment_chats.*

class ChatsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btn_newDialogs: Button
    private lateinit var dbref: DatabaseReference

   // var backTitle = view?.findViewById<TextView>(R.id.backTitle)
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var userlist = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                recyclerView.adapter = UserAdapter(requireActivity(), userlist)
                val setUsers = mutableMapOf<String, User>()

                if(snapshot.exists()){
                    for(dataSnapShot: DataSnapshot in snapshot.children){
                        val user = dataSnapShot.getValue(User::class.java)

                        println("SNAPSHOT = $user")

                        if(user!!.senderId == userId) {
                            setUsers[user.receiverId] = user
                        }

                        else if(user.receiverId == userId){
                            user.receiverName = user.senderName
                            setUsers[user.senderId] = user
                        }
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
}