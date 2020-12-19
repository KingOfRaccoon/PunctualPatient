package com.kingofraccoon.punctualpatient.tools.encoder

import com.google.gson.GsonBuilder
import com.kingofraccoon.punctualpatient.model.Doctor
import com.kingofraccoon.punctualpatient.model.Person

class CriptConverter {
    var json = GsonBuilder().create()

    fun<T> toJson(doc: T) = json.toJson(doc)

    fun fromJsontoDoctor(jobject: String) = json.fromJson(jobject, Doctor::class.java)

    fun fromJsontoPerson(jobject: String) = json.fromJson(jobject, Person::class.java)


}
