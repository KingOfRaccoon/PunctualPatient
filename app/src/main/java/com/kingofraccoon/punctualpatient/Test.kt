package com.kingofraccoon.punctualpatient

import android.os.Build
import android.util.Base64.encodeToString
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.DocumentSnapshot
import com.kingofraccoon.punctualpatient.model.Doctor
import com.kingofraccoon.punctualpatient.tools.encoder.Cript
import com.kingofraccoon.punctualpatient.model.Person
import com.kingofraccoon.punctualpatient.model.TypeDoctors
import com.kingofraccoon.punctualpatient.tools.DinamicTimeTable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

@RequiresApi(Build.VERSION_CODES.O)
fun main(){
    val doctor = Doctor(
            "Зубенко Михаил Петрович",
            1,
            TypeDoctors.PEDIATRICIAN,
            8,
            16, 61,
            "89831037111"
    ).apply { doctorID = "yBfD7c7zi1PMsdaHz9ZzrbcXEj13" }
    val doctor1 = Doctor(
            "Петр Аркдьевич Столыпин",
            2,
            TypeDoctors.CARDIOLOGIST,
            6,
            14, 27,
            "89831037211"
    ).apply { doctorID = "mdYUUbtDgueREyAtBNWMxb1lGV02" }
    val doctor2 = Doctor(
            "Владимир Ильич Ленин",
            3,
            TypeDoctors.TRAUMATOLOGIST,
            7, 10, 12,
            "89831037311"
    ).apply { doctorID = "pKgT5V8poMTamDNnwvz2Kr1J3wn2" }
    val doctor3 = Doctor(
            "Бедарев Николай Васильевич",
            4,
            TypeDoctors.SURGEON,
            5,
            12, 11,
            "89831037411"
    ).apply { doctorID = "C7sZYB72ONOqg8hCnUnSpm6Ws4o1" }
    val doctor4 = Doctor(
            "Алексеева Ангелина Ивановна",
            5,
            TypeDoctors.PEDIATRICIAN,
            4,
            9, 20,
            "89831037511"
    ).apply { doctorID = "QroFSNEWdkhoh7MeZDpGNs7BChf2" }
    val doctor5 = Doctor(
            "Костлев Алексей Владимирович",
            6,
            TypeDoctors.CARDIOLOGIST,
            6,
            10, 30,
            "89831037611"
    ).apply { doctorID = "vWRPIGPOcgNZLkCFHeYyHBxliIT2" }
    val doctors = mutableListOf(
        doctor,
        doctor1,
        doctor2,
        doctor3,
        doctor4,
        doctor5
    )
    val hospital = Hospital(doctors, "")
    hospital.createTalons(LocalDate.now())
    val dinamicTimeTable = DinamicTimeTable()
    println(hospital.timetables.first().talons)
    dinamicTimeTable.updateTimeTable(12, hospital.timetables.first())
    println(hospital.timetables.first().talons)
    print(LocalDate.parse("20-12-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy")))
}
