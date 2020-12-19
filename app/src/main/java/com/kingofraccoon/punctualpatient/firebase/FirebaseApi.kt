package com.kingofraccoon.punctualpatient.firebase

import com.kingofraccoon.punctualpatient.Doctor
import com.kingofraccoon.punctualpatient.Talon

interface FirebaseApi {
    fun getMyTalons(id: String, doctors:MutableList<Doctor>): MutableList<Talon>

    fun writeTalon(id:String, talon: Talon)
}