package com.kingofraccoon.punctualpatient

data class Doctor(
        var name : String,
        var number_cabinet: Int,
        var typeDoctor: TypeDoctors,
        var startWork : Int,
        var endWork: Int,
        var duration: Int,
        var number : String
) {
    override fun toString(): String {
        return "Врач: $name"
    }
}