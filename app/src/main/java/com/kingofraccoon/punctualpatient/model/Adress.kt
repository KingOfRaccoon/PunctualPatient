package com.kingofraccoon.punctualpatient.model

class Adress(
//    var country: String,

) {
    var region = ""
    var city = ""
    var street = ""
    var number_house = ""
    companion object{
        fun instance(string: String): Adress {
            val array = string.split(',')
            return Adress().apply {
                region = array[0]
                city = array[1]
                street = array[2]
                number_house = array[3]
            }
        }
    }

    override fun toString(): String {
        return "$region, $city, $street, $number_house"
    }
}