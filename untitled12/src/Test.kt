package com.kingofraccoon.punctualpatient

import java.time.LocalDate
import java.util.*

fun main(){
    val doctor = Doctor(
        "Зубенко Михаил Петрович",
        1,
        TypeDoctors.PEDIATRICIAN,
        8,
        16, 61)
//    val doctor1 = Doctor(
//        "Петр Аркдьевич Столыпин",
//        2,
//        TypeDoctors.CARDIOLOGIST,
//            6,
//            14
//    )
//    val doctor2 = Doctor(
//        "Владимир Ильич Ленин",
//        3,
//        TypeDoctors.TRAUMATOLOGIST,
//            7, 10
//    )
//    val doctor3 = Doctor(
//            "Бедарев Николай Васильевич",
//            4,
//            TypeDoctors.SURGEON,
//            5, 12
//    )
    val doctor4 = Doctor(
            "Алексеевна Ангелина Ивановна",
            5,
            TypeDoctors.PEDIATRICIAN,
            4, 9, 20
    )
    val doctor5 = Doctor(
            "Костлев Алексей Владимирович",
            6,
            TypeDoctors.CARDIOLOGIST,
            6, 10, 30
    )
//    val talon = Talon(
//        1,
//        LocalDate.now(),
//        doctor
//    )
//    val talon1 = Talon(
//        2,
//        LocalDate.now(),
//        doctor1
//    )
//    val talon2 = Talon(
//        3,
//        LocalDate.now(),
//        doctor2
//    )
//    val talon3
//    val timetable = Timetable(LocalDate.now(), mutableListOf(talon, talon1, talon2))
//    println(timetable.getTalon(TypeDoctors.CARDIOLOGIST))
//    println(TalonGenerator().createTalons(doctor5, 15, LocalDate.now()))
    val hospital = Hospital(mutableListOf(doctor), "Больница")
    hospital.createTalons(LocalDate.now())
//    hospital.timetables.forEach {
//        println(it.talons)
//        println()
//        println()
//    }
//    println(LocalDate.now())
//    val a = mutableListOf(1, 2, 3, 4, 5)
//    println(a.filter {
//        it > 3
//    })
//    println(a)
//    println(UUID.randomUUID().mostSignificantBits)
    val a = (1..3).random()
    val b = (1..7).random()
    println("$a $b")
}