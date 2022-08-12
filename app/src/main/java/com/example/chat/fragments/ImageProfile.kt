package com.example.chat.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.chat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ImageProfile : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var imageUser: ImageView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_profile)

        supportActionBar?.title = "Upload profile image"

        auth = FirebaseAuth.getInstance()

        var btn_choose: Button = findViewById(R.id.btn_choose)
        var btn_upload: Button = findViewById(R.id.btn_upload)
        progressBar = findViewById(R.id.progressBar)
        imageUser = findViewById(R.id.avatar)

        val db = Firebase.firestore

    }
}