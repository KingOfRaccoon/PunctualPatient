package com.kingofraccoon.punctualpatient.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Authorization {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun auth(number: String, password: String) {
        auth.createUserWithEmailAndPassword(number,password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val user = auth.currentUser
                        updateUI(user)
                    }
                    else{
                        updateUI(null)
                    }
                }
    }
    fun updateUI(user: FirebaseUser?) { }
}