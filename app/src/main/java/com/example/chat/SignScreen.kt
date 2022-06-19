package com.example.chat

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_screen.*


class SignScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_screen)
        auth = Firebase.auth

        button_registration.setOnClickListener {
            val name = user_name.text
            val login = user_login.text
            val pass = user_pass1.text
            val pass_c = user_pass2.text

            if(name.trim().isEmpty()){
                user_name.error = "Input your name"
            }

            if(login.trim().isEmpty()){
                user_login.error = "Input your email"
             //   Toast.makeText(this, "EMAIL", Toast.LENGTH_LONG).show()
            }
            if(pass.trim().isEmpty()){
                user_pass1.error = "Input your password"
            }
            if(pass_c.trim().isEmpty()){
                user_pass2.error = "Confirm your password"
            }
            else if (login.trim().isNotEmpty() && pass.trim().isNotEmpty() && pass_c.trim().isNotEmpty()){
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(user_login.text.toString()).matches()){
                    user_login.error = "Input valid email"
                }
                else{
                    if(pass.toString() != pass_c.toString()){
                        user_pass2.error = "Passwords should be same"
                    }
                    else{
                        Toast.makeText(this, "Great. Welcome.", Toast.LENGTH_LONG).show()
                        auth.createUserWithEmailAndPassword(user_login.text.toString(), user_pass1.text.toString())
                            .addOnCompleteListener(this) { task->
                                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                                    Toast.makeText(this, "Registration was successfully", Toast.LENGTH_SHORT).show()
                                    updateUI(user)
                                    startActivity(Intent(this, MainActivity::class.java))
                                    // Sign in success, update UI with the signed-in user's information
//                                    Toast.makeText(this, "Authentication was successfully.",
//                                        Toast.LENGTH_SHORT).show()

                                } else {
                                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
//                                    Toast.makeText(this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show()
                                    updateUI(null)
                                }
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                    }
                }
            }
        }
        cancel.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        im_cancel.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?){

    }

}