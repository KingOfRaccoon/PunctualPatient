package com.kingofraccoon.punctualpatient.tools.firebase

import com.kingofraccoon.punctualpatient.model.Doctor
import com.kingofraccoon.punctualpatient.model.Talon

interface FirebaseApi {
    fun getMyTalons(id: String, doctors:MutableList<Doctor>): MutableList<Talon>

    fun writeTalon(id:String, talon: Talon)
}