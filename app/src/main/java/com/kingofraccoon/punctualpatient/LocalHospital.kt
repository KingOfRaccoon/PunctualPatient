package com.kingofraccoon.punctualpatient

import androidx.lifecycle.MutableLiveData

object LocalHospital{
    private val doctor = Doctor(
        "Зубенко Михаил Петрович",
        1,
        TypeDoctors.PEDIATRICIAN,
        8,
        16, 61)
    private val doctor1 = Doctor(
        "Петр Аркдьевич Столыпин",
        2,
        TypeDoctors.CARDIOLOGIST,
        6,
        14, 27
    )
    private val doctor2 = Doctor(
        "Владимир Ильич Ленин",
        3,
        TypeDoctors.TRAUMATOLOGIST,
        7, 10, 12
    )
    private val doctor3 = Doctor(
        "Бедарев Николай Васильевич",
        4,
        TypeDoctors.SURGEON,
        5, 12, 11
    )
    private val doctor4 = Doctor(
        "Алексеевна Ангелина Ивановна",
        5,
        TypeDoctors.PEDIATRICIAN,
        4, 9, 20
    )
    private val doctor5 = Doctor(
        "Костлев Алексей Владимирович",
        6,
        TypeDoctors.CARDIOLOGIST,
        6, 10, 30
    )
    private val doctors = mutableListOf(
        doctor,
        doctor1,
        doctor2,
        doctor3,
        doctor4,
        doctor5
    )
    val hospital = Hospital(doctors, "Больница")
    val liveDataHospital = MutableLiveData<Hospital>()
    var thisTypeDoctorString = ""

}