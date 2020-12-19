package com.kingofraccoon.punctualpatient.model

data class Doctor(
    var name : String,
    var number_cabinet: Int,
    var typeDoctor: TypeDoctors,
    var startWork : Int,
    var endWork: Int,
    var duration: Int,
    var number : String
){
    var doctorID = ""

}