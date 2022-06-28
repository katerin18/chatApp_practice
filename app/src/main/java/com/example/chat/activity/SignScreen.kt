package com.example.chat.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_screen.*


class SignScreen : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_screen)
        auth = FirebaseAuth.getInstance()

        // наконец, корректная регистрация по логину и паролю
        button_registration.setOnClickListener{
            val name = user_name.text.toString()
            val login = user_login.text.toString()
            val pass = user_pass1.text.toString()
            val pass_c = user_pass2.text.toString()

            if (name.isEmpty()) {
                user_name.error = "Input your name"
            }
            if (login.isEmpty()) {
                user_login.error = "Input your email"
            }
            if (pass.isEmpty()) {
                user_pass1.error = "Input your password"
            }
            if (pass_c.isEmpty()) {
                user_pass2.error = "Confirm your password"
            }
            if(pass != pass_c){
                user_pass2.error = "Passwords don't match"
            }
            if (login.isNotEmpty() &&
                pass.isNotEmpty() &&
                pass_c.trim().isNotEmpty() &&
                name.isNotEmpty()
            ) {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()){
                    user_login.error = "Input valid email"
                }
                else{
                    registerUser(name, login, pass)
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

    private fun registerUser(userName:String, email: String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val useR:FirebaseUser? = auth.currentUser
                    val userId: String = useR!!.uid
                    Log.w("Sign", "Registration was success")

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                    val hashMap:HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("userName", userName)
                    hashMap.put("userImage", "")

                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                        if(it.isSuccessful){
                            user_name.setText("")
                            user_login.setText("")
                            user_pass1.setText("")
                            user_pass2.setText("")
                            Log.w("Sign", "Registration was successfully")
                            Toast.makeText(this, "Registration was successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                    }
                }
            }
    }

//    fun something(){
//       // button_registration.setOnClickListener {
//            val name = findViewById<EditText>(R.id.user_name)
//            val login = findViewById<EditText>(R.id.user_login)
//            val pass = findViewById<EditText>(R.id.user_pass1)
//            val pass_c = findViewById<EditText>(R.id.user_pass2)
//
//            if (name.text.isEmpty()) {
//                user_name.error = "Input your name"
//            }
//            if (login.text.isEmpty()) {
//                user_login.error = "Input your email"
//            }
//            if (pass.text.isEmpty()) {
//                user_pass1.error = "Input your password"
//            }
//            if (pass_c.text.isEmpty()) {
//                user_pass2.error = "Confirm your password"
//            }
//            if (login.text.isNotEmpty() &&
//                pass.text.isNotEmpty() &&
//                pass_c.text.trim().isNotEmpty() &&
//                name.text.isNotEmpty()
//            ) {
//
//                Toast.makeText(this, "OKAY", Toast.LENGTH_SHORT).show()
//
//                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user_login.text.toString())
//                        .matches()
//                ) {
//                    user_login.error = "Input valid email"
//                } else {
//                    if (pass.text.toString() == pass_c.text.toString()) {
//                        auth.createUserWithEmailAndPassword(
//                            login.toString().trim(),
//                            pass.toString().trim()
//                        )
//                            .addOnCompleteListener(this) { task ->
//                                if (task.isSuccessful) {
//                                    val user = auth.currentUser
//                                    Log.w(
//                                        ContentValues.TAG,
//                                        "SIGN UP WAS SUCCESSFULLY",
//                                        task.exception
//                                    )
//                                    //  Log.d(ContentValues.TAG, "signInWithEmail:success")
//                                    Toast.makeText(
//                                        this,
//                                        "Registration was successfully",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    updateUI(user)
//                                    startActivity(Intent(this, MainActivity::class.java))
//
//                                } else {
//                                    Toast.makeText(
//                                        this,
//                                        "Registration was failed",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                    // Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
//                                    Log.w(ContentValues.TAG, "SIGN UP WAS FAILED", task.exception)
//                                    updateUI(null)
//                                }
//                                //startActivity(Intent(this, MainActivity::class.java))
//                            }
//                    } else {
//                        user_pass2.error = "Passwords should be same"
//                    }
//                }
//         //   }
//        }
//
//    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?){

    }

}