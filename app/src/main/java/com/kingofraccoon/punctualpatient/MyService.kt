package com.kingofraccoon.punctualpatient

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        generate(startId)

        return Service.START_STICKY
    }

    fun generate(startId: Int){
            FireStore().changeUser()
        stopSelf(startId)

    }
}