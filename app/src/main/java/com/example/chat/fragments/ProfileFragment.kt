package com.example.chat.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chat.R
import com.example.chat.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var nickname: EditText
    private lateinit var mail: EditText
 //   private lateinit var usId: EditText

    private lateinit var txt_nickname: String
    private lateinit var txt_mail: String
  //  private lateinit var txt_id: String

    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseUser.uid)


//        databaseReference.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(error: DatabaseError){
//                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//
//            }
////            override fun onDataChange(snapshot: DataSnapshot){
////                var user = snapshot.getValue(User::class.java)
////                profile_name.text = user!!.userName
////
////            }
//        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nickname = view.findViewById(R.id.profile_name)
        mail = view.findViewById(R.id.profile_login)
      //  usId = view.findViewById(R.id.profile_id)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        showUser(firebaseUser)

        // upload the profile image
        val prof_image: ImageView = view.findViewById(R.id.user_im)
        prof_image.setOnClickListener {
            startActivity(Intent(activity, ImageProfile::class.java))
        }

        // Sign out of user
        val btn_exit : Button = view.findViewById(R.id.button_exit)
        btn_exit.setOnClickListener {

            Firebase.auth.signOut()

            Toast.makeText(activity, "You have logged out of your profile", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    fun showUser(firebaseUser: FirebaseUser){
        val userID : String = firebaseUser.uid

        val referenceProfile: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        referenceProfile.child(userID).addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                txt_nickname = snapshot.child("userName").value.toString()
                println("SNAPSHOT  ${snapshot.toString()}")
                txt_mail = firebaseUser.email!!
               // txt_id = firebaseUser.uid

                nickname.setText(txt_nickname)
                mail.setText(txt_mail)
                //usId.setText(txt_id)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Something wnt wrong!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}