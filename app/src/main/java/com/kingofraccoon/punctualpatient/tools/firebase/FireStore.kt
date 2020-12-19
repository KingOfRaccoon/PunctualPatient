package com.kingofraccoon.punctualpatient.tools.firebase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.kingofraccoon.punctualpatient.*
import com.kingofraccoon.punctualpatient.model.*
import com.kingofraccoon.punctualpatient.tools.encoder.Cript
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FireStore: FirebaseApi {

    companion object{
        val TALONS = "talons"
    }

    var firebase = FirebaseFirestore.getInstance()

    override fun getMyTalons(id: String, doctors: MutableList<Doctor>): MutableList<Talon> {
        val myTalons = mutableListOf<Talon>()
        return myTalons
    }

    fun translationDoctorTalons(minutes : Int, uuid : String){
        firebase.collection(TALONS)
            .whereEqualTo("doctorID", uuid)
            .whereEqualTo("flag",true)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    for (item in value?.documents!!){

                        firebase.collection(TALONS).document(item.reference.path)
                            .update("time",10)
                    }
                }
            })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun writeTalon(id: String, talon: Talon) {
        val hashMap = hashMapOf(
                "date" to talon.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                "doctorID" to talon.idDoctor,
                "time" to talon.time,
                "userID" to id,
                "flag" to true
        )
        firebase.collection("talons")
                .document(talon.uuid)
                .set(hashMap)
                .addOnSuccessListener {
                    Log.d("TAG", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener {
                    e -> Log.w("TAG", "Error writing document", e)
                }.continueWith {
                    firebase.collection("usersCrypt/$id/talons")
                            .document(talon.uuid)
                            .set(hashMapOf(
                                    "idTalon" to talon.uuid
                            ))
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
                        ).apply { doctorID = value.id }
                    )
                }
                Log.d("Fire", doctors.size.toString())
                LocalHospital.hospital.doctors = doctors
                LocalHospital.liveDataHospital.value = LocalHospital.hospital
            }
            .addOnFailureListener {
                Log.d("Fire", it.message.toString())
            }
                .continueWith {
                    LocalHospital.hospital.doctors = doctors
                    LocalHospital.hospital.createTalons(LocalDate.now())
                    LocalHospital.hospital.timetables.forEach {
                        pullTimeTable(it)
                    }
                    LocalHospital.liveDataHospital.value = LocalHospital.hospital
//                    User.setValue(getMyTalons(User.number, doctors))
                }
        return doctors
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun pullTimeTable(timetable: Timetable){
        var count = 0
        firebase.collection("talons").whereEqualTo("date",
                timetable.day.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .get()
                .addOnSuccessListener {
                    count = it.size()
                }
                .continueWith {
                    if (count == 0) {
                        timetable.talons.forEach { talon ->
                            firebase.collection("talons")
                                    .document(talon.uuid)
                                    .set(hashMapOf(
                                            "date" to talon.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                                            "doctorID" to talon.idDoctor,
                                            "time" to talon.time,
                                            "userID" to "",
                                            "flag" to false
                                    ))
                        }
                    }
                }
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
//                "email" to person.password,
                "adress" to person.adress.toString(),
                "age" to person.age.toString()
        )
        firebase.collection("users")
                .document(userNumber)
                .set(hashMap)
    }

    fun registerNewUserCrypt(userNumber: String, person: Person){
        val hashMap = hashMapOf(
            "text" to Cript().cryptPersonForFireStore(person)
        )
        firebase.collection("usersCrypt")
            .document(userNumber)
            .set(hashMap)
    }

    fun pullDoctorsOnFireStore(){
        LocalHospital.hospital.doctors.forEach {
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
                .document(it.doctorID)
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
            val f = firebase.collection("users")
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


    fun getDoctor(idDoctor: String): Doctor {
        var doctor : Doctor? = null
        val task = firebase.document("doctors/$idDoctor").get()

        task.addOnSuccessListener {value ->
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
        return doctor!!
    }
    fun deleteTalon(talonID: String){
        firebase.document("talons/${talonID}").set(hashMapOf("flag" to false))
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