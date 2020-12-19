package com.kingofraccoon.punctualpatient.tools.firebase

import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

class FireBaseFunctions {
    val fireBaseFunctions = Firebase.functions

    fun addMessage(text: String): Task<String>{
        val data = hashMapOf(
            "text" to text,
            "push" to true
        )
        return fireBaseFunctions
            .getHttpsCallable("addMessage")
            .call(data)
            .continueWith(
                object : Continuation<HttpsCallableResult, String>{
                    override fun then(p0: Task<HttpsCallableResult>): String {
                        return p0.result?.data.toString()
                    }
                }
            )
    }
}