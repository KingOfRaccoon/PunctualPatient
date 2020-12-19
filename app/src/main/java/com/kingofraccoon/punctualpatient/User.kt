package com.kingofraccoon.punctualpatient

import androidx.lifecycle.MutableLiveData
import com.kingofraccoon.punctualpatient.model.Person
import com.kingofraccoon.punctualpatient.model.Talon
import com.kingofraccoon.punctualpatient.model.TalonUser

object User {
    var uid = ""
    var name = ""
    var date = ""

    var adress = ""
    var sex = ""

    var password = ""
    var age = 0
    var number = ""

    var typeOfUser = ""

    var firstName = ""
    var secondName = ""
    var thirdName = ""

    var mutableListTalon = mutableListOf<Talon>()
    val mutableLiveDataTalons = MutableLiveData<MutableList<Talon>>()

    fun setValue(mutableList: MutableList<Talon>) {
        mutableListTalon = mutableList
        mutableLiveDataTalons.value = mutableListTalon
    }

    var mutableListTalonDoctor = mutableListOf<TalonUser>()
    val mutableLiveDataTalonsDoctor = MutableLiveData<MutableList<TalonUser>>()

    fun setValueDoctor(mutableList: MutableList<TalonUser>) {
        mutableListTalonDoctor = mutableList
        mutableLiveDataTalonsDoctor.value = mutableList
    }

    fun setUser(person: Person) {
        name = person.name
        date = person.date
        adress = person.adress
        sex = person.sex
        age = person.age
        number = person.number
    }

    var listTalonsID = mutableListOf<String>()
}