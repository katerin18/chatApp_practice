package com.example.chat.activity

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
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

        // hide / show password
        var edit_pass : EditText = findViewById(R.id.user_pass1)

        var imgShowHide: ImageView = findViewById(R.id.show_psw)
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

        // наконец, корректная регистрация по логину и паролю
        button_registration.setOnClickListener{
            val nick_name = user_name.text.toString()
            val login = user_login.text.toString()
            val pass = user_pass1.text.toString()
            val pass_c = user_pass2.text.toString()

            if (nick_name.isEmpty()) {
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
                nick_name.isNotEmpty())
            {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()){
                    user_login.error = "Input valid email"
                }
                else{
                    registerUser(nick_name, login, pass)
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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?){

    }

}