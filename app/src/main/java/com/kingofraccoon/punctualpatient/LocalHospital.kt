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
    ).apply { doctorID = "HaUKoez6ivW79LpwE0ARjv2XApD2" }
    private val doctor1 = Doctor(
        "Петр Аркдьевич Столыпин",
        2,
        TypeDoctors.CARDIOLOGIST,
        6,
        14, 27,
        "89831037211"
    ).apply { doctorID = "mdYUUbtDgueREyAtBNWMxb1lGV02" }
    private val doctor2 = Doctor(
        "Владимир Ильич Ленин",
        3,
        TypeDoctors.TRAUMATOLOGIST,
        7, 10, 12,
        "89831037311"
    ).apply { doctorID = "pKgT5V8poMTamDNnwvz2Kr1J3wn2" }
    private val doctor3 = Doctor(
        "Бедарев Николай Васильевич",
        4,
        TypeDoctors.SURGEON,
        5,
        12, 11,
        "89831037411"
    ).apply { doctorID = "C7sZYB72ONOqg8hCnUnSpm6Ws4o1" }
    private val doctor4 = Doctor(
        "Алексеева Ангелина Ивановна",
        5,
        TypeDoctors.PEDIATRICIAN,
        4,
        9, 20,
        "89831037511"
    ).apply { doctorID = "QroFSNEWdkhoh7MeZDpGNs7BChf2" }
    private val doctor5 = Doctor(
        "Костлев Алексей Владимирович",
        6,
        TypeDoctors.CARDIOLOGIST,
        6,
        10, 30,
        "89831037611"
    ).apply { doctorID = "vWRPIGPOcgNZLkCFHeYyHBxliIT2" }


    @RequiresApi(Build.VERSION_CODES.O)
    private var doctors = FireStore().getDoctors()

    @RequiresApi(Build.VERSION_CODES.O)
    val hospital = Hospital(doctors, "Больница")
    val liveDataHospital = MutableLiveData<Hospital>()
}