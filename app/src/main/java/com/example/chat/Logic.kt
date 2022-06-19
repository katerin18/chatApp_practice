package com.example.chat
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

public class logic {
    public fun check_2(email_log: String, pass_log: String): String{
        if (email_log=="p@mail.ru"){
            return "Not valid email"
        }
        return ""
    }
}