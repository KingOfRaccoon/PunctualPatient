package com.kingofraccoon.punctualpatient

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore
import java.security.Provider

class GetTalonDoctorServise: Service() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        generate(startId)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generate(startId: Int) {
        User.setValueDoctor(FireStore().getAllTalonsDoctors(User.name))
//        FireStore().pullDoctorsOnFireStore()
        stopSelf(startId)
    }
}