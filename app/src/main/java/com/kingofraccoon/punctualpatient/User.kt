package com.kingofraccoon.punctualpatient

import androidx.lifecycle.MutableLiveData

object User {
    var id = "Я"
    var name = ""
    var date = ""
//    var adress = Adress()
    var adress = ""
    var sex = ""

    var email = ""
    var age = 0
    var number = ""

    var typeOfUser = ""

    var firstName = ""
    var secondName = ""
    var thirdName = ""

    var mutableListTalon = mutableListOf<Talon>()
    val mutableLiveDataTalons = MutableLiveData<MutableList<Talon>>()

    fun setValue(mutableList: MutableList<Talon>){
        mutableListTalon = mutableList
        mutableLiveDataTalons.value = mutableListTalon
    }

    var mutableListTalonDoctor = mutableListOf<TalonUser>()
    val mutableLiveDataTalonsDoctor = MutableLiveData<MutableList<TalonUser>>()

    fun setValueDoctor(mutableList: MutableList<TalonUser>){
        mutableListTalonDoctor = mutableList
        mutableLiveDataTalonsDoctor.value = mutableList
    }

    fun setUser(person: Person){
        name = person.name
        date = person.date
        adress = person.adress
        sex = person.sex
        email = person.email
        age = person.age
        number = person.number
    }

    var listTalonsID = mutableListOf<String>()
}