package com.kingofraccoon.punctualpatient

import android.os.Build
import androidx.annotation.RequiresApi
import com.kingofraccoon.punctualpatient.firebase.FireStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Talon(
        var date: String,
        var doctor: Doctor,
        var time: String,
){
        var uuid = ""
        var idDoctor = ""
        @RequiresApi(Build.VERSION_CODES.O)
        constructor(
                date: LocalDate,
                doctor: Doctor,
                time: String): this(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), doctor, time)
        init{
            this.uuid = UUID.randomUUID().mostSignificantBits.toString()
            this.idDoctor = doctor.number
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun toString(): String {
                return "Дата: ${date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}" +
                        "\n" +
                        "$doctor" +
                        "\n" +
                        time
        }
    companion object {
        fun convertToTalon(talonData: TalonData): Talon {
            var nameDoctor = ""
            var doctor: Doctor? = null
            FireStore().firebase.collection("doctors")
                .whereEqualTo("number", talonData.idDoctor)
                .get()
                .addOnSuccessListener {
                    nameDoctor = it.first().getString("name") as String
                }.continueWith {
                    doctor = FireStore().getDoctor(talonData.idDoctor)
                }
            return Talon(talonData.date, doctor!!, talonData.time)
        }
    }
}