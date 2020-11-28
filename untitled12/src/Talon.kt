package com.kingofraccoon.punctualpatient

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Talon(
        var number: Int,
        var date: LocalDate,
        var doctor: Doctor,
        var time: String
){
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