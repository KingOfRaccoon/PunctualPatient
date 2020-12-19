package com.kingofraccoon.punctualpatient

import com.kingofraccoon.punctualpatient.tools.encoder.Cript
import com.kingofraccoon.punctualpatient.tools.encoder.CriptConverter
import com.kingofraccoon.punctualpatient.model.Doctor
import com.kingofraccoon.punctualpatient.model.Person
import com.kingofraccoon.punctualpatient.model.TypeDoctors
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        Person()
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
//        var json = GsonBuilder()
//                .create()
        var docJson = CriptConverter().toJson(doc)
        println(docJson)


        var docFromJson = CriptConverter().fromJsontoDoctor(docJson)
        println(docFromJson)
    }

}