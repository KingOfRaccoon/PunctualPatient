package com.kingofraccoon.punctualpatient.view.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.kingofraccoon.punctualpatient.R
import com.kingofraccoon.punctualpatient.model.TalonData
import com.kingofraccoon.punctualpatient.view.adapters.TalonFirebaseAdapter

class FirebaseActivity : AppCompatActivity() {


    var firebase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
                Log.w("TAG", "$token")

            // Log and toast
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })


        var query = firebase.collection("talons")

        var fireRecycler = findViewById<RecyclerView>(R.id.firebaseRecyclerView)

        fireRecycler.adapter =
            TalonFirebaseAdapter(query)

//        query.addSnapshotListener(object : EventListener<QuerySnapshot> {
//            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                for (item in value?.documents!!) {
//                    Log.d("TEST", TalonData(
//                        item["date"].toString(),
//                        item["doctorID"].toString(),
//                        item["time"].toString(),
//                        item["userID"].toString()
//                    ).toString())
//                }
//            }
//        })

        fireRecycler.layoutManager = LinearLayoutManager(this)

    }

}