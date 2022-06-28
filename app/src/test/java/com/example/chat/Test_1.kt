package com.example.chat
import com.example.chat.activity.MainActivity
import org.junit.Test

import org.junit.Assert.*

class Test_1 {
    @Test
    fun test_valid_email(){
        var res = MainActivity().check_1("p@mail.ru", "123")
        assertEquals(res, "")
        res = MainActivity().check_1("p", "123")
        assertEquals(res, "Invalid e-mail pattern")
    }
}