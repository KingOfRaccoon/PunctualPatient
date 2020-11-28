package com.kingofraccoon.punctualpatient

import java.time.Duration

class Doctor(
        var name : String,
        var number_cabinet: Int,
        var typeDoctor: TypeDoctors,
        var startWork : Int,
        var endWork: Int,
        var duration: Int
) {
    override fun toString(): String {
        return "Врач: $name"
    }
}