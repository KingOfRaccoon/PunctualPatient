package com.kingofraccoon.punctualpatient.encoder

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.kingofraccoon.punctualpatient.Doctor
import com.kingofraccoon.punctualpatient.Person

class CriptConverter {
    var json = GsonBuilder().create()

    fun<T> toJson(doc: T) = json.toJson(doc)

    fun fromJsontoDoctor(jobject: String) = json.fromJson(jobject, Doctor::class.java)

    fun fromJsontoPerson(jobject: String) = json.fromJson(jobject, Person::class.java)


}
