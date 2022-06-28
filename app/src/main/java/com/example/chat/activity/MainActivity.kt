package com.example.chat.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

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