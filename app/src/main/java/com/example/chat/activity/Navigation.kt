package com.example.chat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chat.R
import com.example.chat.fragments.ChatsFragment
import com.example.chat.fragments.ProfileFragment
import com.example.chat.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_navigation.*

class Navigation : AppCompatActivity() {

    private val chatFrag = ChatsFragment()
    private val profFrag = ProfileFragment()
    private val settFrag = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        replaceFragment(profFrag)

        bottom_navigation.setOnItemReselectedListener { // работает при двойном нажатии
            when(it.itemId){
                R.id.chats -> replaceFragment(chatFrag)
                R.id.profile -> replaceFragment(profFrag)
                R.id.settings -> replaceFragment(settFrag)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}