package com.kingofraccoon.punctualpatient

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

class TalonGenerator {
    @RequiresApi(Build.VERSION_CODES.O)
    fun createTalons(doctor: Doctor, day: LocalDate): MutableList<Talon> {
        val listTalons = mutableListOf<Talon>()
        val quantity = (doctor.endWork - doctor.startWork) * 60 / doctor.duration
        val time = WorkTime(doctor.startWork)
        for (i in 0 until quantity){
            listTalons.add(
                    Talon(
                            day,
                            doctor,
                            time.toString()
                    )
            )
            time.minutes += doctor.duration
        }
        return listTalons
    }

    fun getTime(time: Int, duration: Int): String{
        var string = ""
        string += ((time * 60 + duration) / 60).toInt().toString()
        string += ":"
        string += ((time * 60 + duration) % 60).toInt().toString()
        return string
    }
}