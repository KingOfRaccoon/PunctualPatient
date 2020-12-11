package com.kingofraccoon.punctualpatient.firebase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.Person
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.fixedRateTimer

class FireStore: FirebaseApi {
    var firebase = FirebaseFirestore.getInstance()

    override fun getMyTalons(id: String, doctors: MutableList<Doctor>): MutableList<Talon> {
        val myTalons = mutableListOf<Talon>()
        firebase.collection("users")
                .document(id)
                .collection("talons")
            .addSnapshotListener( object : EventListener<QuerySnapshot>{
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    value?.documents?.forEach {
                        myTalons.add(
                            Talon(
                                it.getString("date") as String,
                                    doctors.find { doc ->
                                    doc.name == it.getString("doctor")
                                }!!,
                                it.getString("time") as String
                            )
                        )
                    }
                }
            })
        return myTalons
    }

    fun getProfileTalon(id: String): MutableList<Talon> {
        val doctors = mutableListOf<Doctor>()
        var talons = mutableListOf<Talon>()
        val task = firebase.collection("doctors").get()

        task.addOnSuccessListener {
            it.documents.forEach { value ->
                doctors.add(
                        Doctor(
                                value.getString("name") as String,
                                (value.get("cabinet") as Long).toInt(),
                                getEnumDoctor(value.getString("nameType") as String)!!,
                                (value.get("start") as Long).toInt(),
                                (value.get("end") as Long).toInt(),
                                (value.get("duration") as Long).toInt(),
                                value.getString("number") as String
                        )
                )
            }
            Log.d("Fire", doctors.size.toString())
        }
                .addOnFailureListener {
                    Log.d("Fire", it.message.toString())
                }
                .continueWith {
                    talons = getMyTalons(id, doctors)
                }
        return talons
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun writeTalon(id: String, talon: Talon) {
        val hashMap = hashMapOf(
                "date" to talon.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                "doctor" to talon.doctor.name,
                "time" to talon.time
        )
        firebase.collection("users")
                .document(User.number)
                .collection("talons")
                .document("${UUID.randomUUID().mostSignificantBits}")
                .set(hashMap)
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener {
                    e -> Log.w("TAG", "Error writing document", e)
                }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDoctors(): MutableList<Doctor>{
        val doctors = mutableListOf<Doctor>()
        val task = firebase.collection("doctors").get()

        task.addOnSuccessListener {
                it.documents.forEach { value ->
                    doctors.add(
                            Doctor(
                                    value.getString("name") as String,
                                    (value.get("cabinet") as Long).toInt(),
                                    getEnumDoctor(value.getString("nameType") as String)!!,
                                    (value.get("start") as Long).toInt(),
                                    (value.get("end") as Long).toInt(),
                                    (value.get("duration") as Long).toInt(),
                                    value.getString("number") as String
                        )
                    )
                }
                Log.d("Fire", doctors.size.toString())
                LocalHospital.doctors = doctors
                LocalHospital.liveDataHospital.value = LocalHospital.hospital
            }
            .addOnFailureListener {
                Log.d("Fire", it.message.toString())
            }
                .continueWith {
                    LocalHospital.hospital.doctors = doctors
                    LocalHospital.hospital.createTalons(LocalDate.now())
                    LocalHospital.liveDataHospital.value = LocalHospital.hospital
//                    User.setValue(getMyTalons(User.number, doctors))
                }
        return doctors
    }

    fun checkUserOrDoctor(number: String): Boolean {
        var doctor : Doctor? = null
        firebase.collection("doctors")
            .whereEqualTo("number", number)
            .get()
            .addOnSuccessListener {
                it.documents.forEach { value ->
                    doctor = Doctor(
                        value.getString("name") as String,
                        (value.get("cabinet") as Long).toInt(),
                        getEnumDoctor(value.getString("nameType") as String)!!,
                        (value.get("start") as Long).toInt(),
                        (value.get("end") as Long).toInt(),
                        (value.get("duration") as Long).toInt(),
                        value.getString("number") as String
                    )
                }
            }
        return doctor != null
    }
    fun getAllTalonsDoctors(nameDoctor : String): MutableList<TalonUser> {
        val talonsDoctor = mutableListOf<TalonUser>()
        val f = firebase.collection("users")
                f.get()
                .addOnSuccessListener {
                    it.documents.forEach { doc ->
                        Log.d("Fire", doc.reference.id)
                        doc.reference.collection("talons")
//                                .whereEqualTo("doctor", nameDoctor)
                                .get()
                                .addOnSuccessListener { talons ->
                                    talons.documents.forEach { talon ->
                                        talonsDoctor.add(
                                                TalonUser(
                                                        talon.getString("date") as String,
                                                        doc.id,
                                                        talon.getString("time") as String
                                                )
                                        )
                                    }
                                }
                    }
                }
        User.setValueDoctor(talonsDoctor)

        return talonsDoctor
    }

    fun registerNewUser(userNumber: String, person: Person){
        val hashMap = hashMapOf(
                "name" to person.name,
                "sex" to person.sex,
                "date" to person.date,
                "email" to person.email,
                "adress" to person.adress.toString(),
                "age" to person.age.toString()
        )
        firebase.collection("users")
                .document(userNumber)
                .set(hashMap)
    }

    fun pullDoctorsOnFireStore(){
        LocalHospital.doctors.forEach {
            val set = hashMapOf(
                "name" to it.name,
                "cabinet" to it.number_cabinet,
                "nameType" to it.typeDoctor.nameType,
                "start" to it.startWork,
                "end" to it.endWork,
                "duration" to it.duration,
                "number" to it.number
            )
            firebase.collection("doctors")
                .document(it.name)
                .set(set)
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener {
                        e -> Log.w("TAG", "Error writing document", e)
                }
        }
    }

    fun changeUser(){
        val list = mutableListOf<TalonUser>()
        User.mutableListTalonDoctor.forEach {
            val  f = firebase.collection("users")
                .document(it.name)
                .get()
            if (f.isComplete)
            list.add(
                TalonUser(
                it.date,
                f.result?.data?.get("name").toString(),
                it.time
                )
            )
        }
        User.setValueDoctor(list)
    }
    fun getEnumDoctor(string: String): TypeDoctors? {
        return when(string){
            TypeDoctors.CARDIOLOGIST.nameType -> TypeDoctors.CARDIOLOGIST
            TypeDoctors.PEDIATRICIAN.nameType -> TypeDoctors.PEDIATRICIAN
            TypeDoctors.SURGEON.nameType -> TypeDoctors.SURGEON
            TypeDoctors.TRAUMATOLOGIST.nameType -> TypeDoctors.TRAUMATOLOGIST
            else -> null
        }
    }

}