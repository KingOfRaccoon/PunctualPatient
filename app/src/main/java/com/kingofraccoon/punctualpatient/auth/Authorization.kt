package com.kingofraccoon.punctualpatient.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Authorization {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun register(number: String, password: String): String {
        var uid = ""
        auth.createUserWithEmailAndPassword(number,password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val user = auth.currentUser
                        updateUI(user)
                        uid = if (user?.uid == null) "" else user.uid
                    }
                    else{
                        updateUI(null)

                    }
                }
        return uid
    }
    fun updateUI(user: FirebaseUser?) {

    }
}