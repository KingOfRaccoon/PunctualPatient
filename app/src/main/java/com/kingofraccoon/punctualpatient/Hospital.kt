package com.kingofraccoon.punctualpatient

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class Hospital(
        var doctors: MutableList<Doctor>,
        var name: String
//    var adress: Adress
) {
    var timetables: MutableList<Timetable> = mutableListOf()
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTalon(day: LocalDate, typeDoctors: TypeDoctors): Talon? {
        var talon : Talon? = null
        for (it in timetables){
            if (it.day == day){
                talon = it.talons.first { it.date == day.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) }
                break
            }
        }
        return talon
    }
    fun createTalons(day: LocalDate){
        doctors.forEach {
            timetables.add(
                    Timetable(
                            day,
                            TalonGenerator().createTalons(it, day)
                    )
            )
        }
    }

}