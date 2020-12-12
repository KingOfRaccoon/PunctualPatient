package com.kingofraccoon.punctualpatient

import org.junit.Test

import org.junit.Assert.*

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
}