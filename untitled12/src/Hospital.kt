package com.kingofraccoon.punctualpatient

import java.time.LocalDate

class Hospital(
    var doctors: MutableList<Doctor>,

    var name: String
//    var adress: Adress
) {
    var timetables: MutableList<Timetable> = mutableListOf()
    fun getTalon(day: LocalDate, typeDoctors: TypeDoctors): Talon? {
        var talon : Talon? = null
        for (it in timetables){
            if (it.day == day){
                talon = it.talons.first { it.date == day }
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