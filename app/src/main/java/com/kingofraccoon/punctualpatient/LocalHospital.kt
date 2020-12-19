package com.kingofraccoon.punctualpatient

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.kingofraccoon.punctualpatient.model.Doctor
import com.kingofraccoon.punctualpatient.model.TypeDoctors
import com.kingofraccoon.punctualpatient.tools.firebase.FireStore

object LocalHospital {
    private val doctor = Doctor(
        "Зубенко Михаил Петрович",
        1,
        TypeDoctors.PEDIATRICIAN,
        8,
        16, 61,
        "89831037111"
    ).apply { doctorID = "76iqbILQxEY8CBxlnKtCaOhLvXw1" }
    private val doctor1 = Doctor(
        "Петр Аркдьевич Столыпин",
        2,
        TypeDoctors.CARDIOLOGIST,
        6,
        14, 27,
        "89831037211"
    ).apply { doctorID = "Cjws4dPxBgZgCEl06QdQJILd0Z12" }
    private val doctor2 = Doctor(
        "Владимир Ильич Ленин",
        3,
        TypeDoctors.TRAUMATOLOGIST,
        7, 10, 12,
        "89831037311"
    ).apply { doctorID = "vP8oOND5d8cVaYz5nhvZhqEpsMT2" }
    private val doctor3 = Doctor(
        "Бедарев Николай Васильевич",
        4,
        TypeDoctors.SURGEON,
        5,
        12, 11,
        "89831037411"
    ).apply { doctorID = "WNIKgq8N3AR0NCtZDB22MbxlVFp1" }
    private val doctor4 = Doctor(
        "Алексеева Ангелина Ивановна",
        5,
        TypeDoctors.PEDIATRICIAN,
        4,
        9, 20,
        "89831037511"
    ).apply { doctorID = "NRCcufEAU6MzFJlyi8wpTzDBRlE2" }
    private val doctor5 = Doctor(
        "Костлев Алексей Владимирович",
        6,
        TypeDoctors.CARDIOLOGIST,
        6,
        10, 30,
        "89831037611"
    ).apply { doctorID = "1BSMSjWvfAR2wAMJ9Nz3fS4rilz2" }
    val doctorsFireStore = mutableListOf(
        doctor,
        doctor1,
        doctor2,
        doctor3,
        doctor4,
        doctor5
    )
    @RequiresApi(Build.VERSION_CODES.O)
    private var doctors = FireStore().getDoctors()

    @RequiresApi(Build.VERSION_CODES.O)
    val hospital = Hospital(doctors, "Больница")
    val liveDataHospital = MutableLiveData<Hospital>()
}