package com.kingofraccoon.punctualpatient

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.kingofraccoon.punctualpatient.encoder.Cript
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var doc = Doctor("fgjhk", 78, TypeDoctors.CARDIOLOGIST, 67,89,787,"ytdffhg")
        var cript: Cript = Cript()
        var result = cript.encrypt(doc.toString())
        result.forEach { print(it) }
        println()
        var result2 = cript.decrypt(result)
        result2.forEach { print(it) }





    }
    @Test
    fun test2(){
        var doc = Doctor("fgjhk", 78, TypeDoctors.CARDIOLOGIST, 67,89,787,"ytdffhg")
        println(doc)
        var json = GsonBuilder()
                .create()
        var docJson = json.toJson(doc)
        println(docJson)

        var docFromJson = json.fromJson(docJson, Doctor::class.java)
        println(docFromJson)
    }
}