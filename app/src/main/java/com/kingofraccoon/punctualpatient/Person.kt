package com.kingofraccoon.punctualpatient

import com.kingofraccoon.punctualpatient.Adress

data class Person(
    var adress: String = "",
    var date: String = "",
    var email: String = "",
    var name: String = "",
    var sex: String = "",
    var age: Int = 0,
    var number: String = ""
)