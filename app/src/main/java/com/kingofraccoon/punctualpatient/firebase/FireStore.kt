package com.kingofraccoon.punctualpatient.firebase

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.kingofraccoon.punctualpatient.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FireStore: FirebaseApi {
    var firebase = FirebaseFirestore.getInstance()

    override fun getMyTalons(id: String, doctors: MutableList<Doctor>): MutableList<Talon> {
        val myTalons = mutableListOf<Talon>()
        firebase.collection(id)
            .addSnapshotListener( object : EventListener<QuerySnapshot>{
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    value?.documents?.forEach {
                        myTalons.add(
                            Talon(
                                    (it.get("number") as Long).toInt(),
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun writeTalon(id: String, talon: Talon) {
        val hashMap = hashMapOf(
                "number" to talon.number,
                "date" to talon.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                "doctor" to talon.doctor.name,
                "time" to talon.time
        )
        firebase.collection(id)
                .document(talon.number.toString())
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
                                    (value.get("duration") as Long).toInt()
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
                    User.setValue(getMyTalons(User.id, doctors))
                }
        return doctors
    }
    fun getDoctor(name: String): Doctor? {
        var doctor : Doctor? = null
        firebase.collection("doctors")
                .document(name)
                .get()
                .addOnSuccessListener { value ->
                    doctor = Doctor(
                            value.getString("name") as String,
                            (value.get("cabinet") as Long).toInt(),
                            getEnumDoctor(value.getString("nameType") as String)!!,
                            (value.get("start") as Long).toInt(),
                            (value.get("end") as Long).toInt(),
                            (value.get("duration") as Long).toInt()
                    )
                }
                .addOnFailureListener {
                    Log.d("Fire", it.message.toString())
                }
        return doctor
    }

    fun pullDoctorsOnFireStore(){
        LocalHospital.doctors.forEach {
            val set = hashMapOf(
                "name" to it.name,
                "cabinet" to it.number_cabinet,
                "nameType" to it.typeDoctor.nameType,
                "start" to it.startWork,
                "end" to it.endWork,
                "duration" to it.duration
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

    private fun getEnumDoctor(string: String): TypeDoctors? {
        return when(string){
            TypeDoctors.CARDIOLOGIST.nameType -> TypeDoctors.CARDIOLOGIST
            TypeDoctors.PEDIATRICIAN.nameType -> TypeDoctors.PEDIATRICIAN
            TypeDoctors.SURGEON.nameType -> TypeDoctors.SURGEON
            TypeDoctors.TRAUMATOLOGIST.nameType -> TypeDoctors.TRAUMATOLOGIST
            else -> null
        }
    }

}