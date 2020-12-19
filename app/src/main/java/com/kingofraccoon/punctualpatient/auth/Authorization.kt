package com.kingofraccoon.punctualpatient.auth

import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthMultiFactorException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.MultiFactor

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
    fun updateUI(user: FirebaseUser?) { }

    fun singIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        updateUI(user)
                    }
                    else{
                        updateUI(null)
                        checkForMultiFactorFailure(task.exception!!)
                    }
                }
    }

    fun signOut(){
        auth.signOut()
        updateUI(null)
    }
    fun checkForMultiFactorFailure(e: Exception){
        if (e is FirebaseAuthMultiFactorException){
            val intent = Intent()
            val resolver = e.resolver
            intent.putExtra("EXTRA_RESOLVER", resolver)
            //setResult(MultiFactorActivity.RESULT_NEED_NFA_SIGN_IN, intent)
            //finish()
        }
    }
}