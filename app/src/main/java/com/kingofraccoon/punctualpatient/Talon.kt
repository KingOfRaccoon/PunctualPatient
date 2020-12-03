package com.kingofraccoon.punctualpatient

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Talon(
        var number: Int,
        var date: String,
        var doctor: Doctor,
        var time: String
){
        @RequiresApi(Build.VERSION_CODES.O)
        constructor(
                number: Int,
                date: LocalDate,
                doctor: Doctor,
                time: String): this(number, date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), doctor, time)
        @RequiresApi(Build.VERSION_CODES.O)
        override fun toString(): String {
                return "Номер: $number" +
                        "\n" +
                        "Дата: ${date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}" +
                        "\n" +
                        "$doctor" +
                        "\n" +
                        time
        }
}