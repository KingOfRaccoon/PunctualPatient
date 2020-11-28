package com.kingofraccoon.punctualpatient

import java.time.LocalDate

class TalonGenerator() {
    fun createTalons(doctor: Doctor, day: LocalDate): MutableList<Talon> {
        val listTalons = mutableListOf<Talon>()
        val quantity = (doctor.endWork - doctor.startWork) * 60 / doctor.duration
        val time = WorkTime(doctor.startWork)
        for (i in 0 until quantity){
            listTalons.add(
                    Talon(
                            i+1,
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