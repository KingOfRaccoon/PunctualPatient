package com.kingofraccoon.punctualpatient

import androidx.lifecycle.MutableLiveData

object User {
    var id = "Я"
    var name = ""
    var date = ""
    var adress = Adress()
    var sex = ""
    var email = ""
















    var typeOfUser = ""

    var mutableListTalon = mutableListOf<Talon>()
    val mutableLiveDataTalons = MutableLiveData<MutableList<Talon>>()

    fun setValue(mutableList: MutableList<Talon>){
        mutableListTalon = mutableList
        mutableLiveDataTalons.value = mutableList
    }
}