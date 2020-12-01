package com.kingofraccoon.punctualpatient

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import java.time.LocalDate

class GenerateTalonService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        generate(startId)
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generate(startId: Int) {
        LocalHospital.hospital.createTalons(LocalDate.now())
        LocalHospital.liveDataHospital.value = LocalHospital.hospital
        stopSelf(startId)
    }
}