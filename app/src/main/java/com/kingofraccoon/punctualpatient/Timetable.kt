package com.kingofraccoon.punctualpatient

import com.kingofraccoon.punctualpatient.model.Talon
import java.time.LocalDate

class Timetable(
        var day: LocalDate,
        var talons : MutableList<Talon>
)