package com.kingofraccoon.punctualpatient.auth

import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthMultiFactorException

class Authorization {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(number: String, password: String) =
            auth.createUserWithEmailAndPassword(number,password)

    fun singIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    fun signOut(){
        auth.signOut()
    }

    fun checkForMultiFactorFailure(e: Exception){
        if (e is FirebaseAuthMultiFactorException){
            val intent = Intent()
            val resolver = e.resolver
            Log.d("Fire", e.message.toString())
            intent.putExtra("EXTRA_RESOLVER", resolver)
            //setResult(MultiFactorActivity.RESULT_NEED_NFA_SIGN_IN, intent)
            //finish()
        }
    }
}
