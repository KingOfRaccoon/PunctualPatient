package com.kingofraccoon.punctualpatient

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Talon(
        var date: String,
        var doctor: Doctor,
        var time: String
){
        @RequiresApi(Build.VERSION_CODES.O)
        constructor(
                date: LocalDate,
                doctor: Doctor,
                time: String): this(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), doctor, time)
        @RequiresApi(Build.VERSION_CODES.O)
        override fun toString(): String {
                return "Дата: ${date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}" +
                        "\n" +
                        "$doctor" +
                        "\n" +
                        time
        }
}