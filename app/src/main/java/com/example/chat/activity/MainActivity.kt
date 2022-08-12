package com.example.chat.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_screen.*
import kotlinx.android.synthetic.main.fragment_profile.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            Log.i(TAG, "User=null")
        }
        else{
            startActivity(Intent(this, Navigation::class.java))
        }

        // hide / show password
        val edit_pass : EditText = findViewById(R.id.user_pass0)

        val imgShowHide: ImageView = findViewById(R.id.show_psw_login)
        imgShowHide.setImageResource(R.drawable.visiblel)
        imgShowHide.setOnClickListener {
            if(edit_pass.transformationMethod.equals(HideReturnsTransformationMethod.getInstance())){
                edit_pass.transformationMethod = PasswordTransformationMethod.getInstance()
                imgShowHide.setImageResource(R.drawable.visiblel)
            }
            else{
                edit_pass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imgShowHide.setImageResource(R.drawable.invisible)
            }
        }

        log_in.setOnClickListener{
            val email = user_email.text.toString().trim()
            val pass = user_pass0.text.toString().trim()

            if (email.isEmpty()) {
                user_email.error = "Input your email"
            }
            if (pass.isEmpty()) {
                user_pass0.error = "Input your password"
            }
            if(email.isNotEmpty() && pass.isNotEmpty()){
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this){
                        if(it.isSuccessful){
                            user_email.setText("")
                            user_pass0.setText("")
                            startActivity(Intent(this, Navigation::class.java))
                        }
                }
            }
        }
        sign_up.setOnClickListener{
            startActivity(Intent(this, SignScreen::class.java))
        }
    }
}