package com.example.mytrainer

import com.example.mytrainer.component.DBUser
import org.junit.Test

class MyTrainerTest {

    @Test
    fun test(){

        var user = DBUser()
        user.email = "roshka"
        user.password = "anatoliy"

        println(user)

    }
}