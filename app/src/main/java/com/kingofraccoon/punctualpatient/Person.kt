package com.kingofraccoon.punctualpatient

import com.kingofraccoon.punctualpatient.Adress

class Person() {
    var adress = ""
    var date = ""
    var email = ""
    var name = ""
    var sex = ""
    var age = 0
    var number = ""
    constructor(
            adress: String,
            date: String,
            email: String,
            name: String,
            sex: String,
            age: Int,
            number: String
    ): this(){
        this.adress = adress
        this.date = date
        this.email = email
        this.name = name
        this.sex = sex
        this.age = age
        this.number = number
    }
}