package com.kingofraccoon.punctualpatient

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore

class ConvertToTalonServise : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        generate(startId)
        return START_STICKY
    }

    fun generate(startId: Int) {
        FireStore().firebase.collection("usersCrypt/${User.number}/talons")
            .get()
            .addOnSuccessListener {
                it.documents.forEach {
                    User.listTalonsID.add(it.getString("idTalon") as String)
                }
            }
        stopSelf(startId)
    }
}