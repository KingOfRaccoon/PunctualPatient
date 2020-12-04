package com.kingofraccoon.punctualpatient

import android.graphics.Region

class Adress(
//    var country: String,

) {
    var region = ""
    var city = ""
    var street = ""
    var number_house = ""
    companion object{
        fun instance(string: String): Adress{
            val array = string.split(',')
            return Adress().apply {
                region = array[0]
                city = array[1]
                street = array[2]
                number_house = array[3]
            }
        }
    }
}