package com.kingofraccoon.punctualpatient.auth

import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthMultiFactorException
import com.google.firebase.auth.FirebaseUser

class Authorization {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun register(number: String, password: String): FirebaseUser? {
        var thisUser : FirebaseUser? = null
        auth.createUserWithEmailAndPassword(number,password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val user = auth.currentUser
                        updateUI(user)
                        thisUser = user
                    }
                    else{
                        updateUI(null)

                    }
                }
        return thisUser
    }
    fun singIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        updateUI(user)
                        Log.d("Fire", "True")
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
            Log.d("Fire", e.message.toString())
            intent.putExtra("EXTRA_RESOLVER", resolver)
            //setResult(MultiFactorActivity.RESULT_NEED_NFA_SIGN_IN, intent)
            //finish()
        }
    }
    fun updateUI(user: FirebaseUser?){}
}
