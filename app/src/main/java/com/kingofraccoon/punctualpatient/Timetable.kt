package com.kingofraccoon.punctualpatient

import java.time.LocalDate
import java.util.*

class Timetable(
        var day: LocalDate,
        var talons : MutableList<Talon>
) {
    fun getTalon(typeDoctors: TypeDoctors): Talon? {
        var talon : Talon? = null
        for (it in talons) {
            if (it.doctor.typeDoctor == typeDoctors) {
                talon = it
                break
            }
        }
        return talon
    }
}