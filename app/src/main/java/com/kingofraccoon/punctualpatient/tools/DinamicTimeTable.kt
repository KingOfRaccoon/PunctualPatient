package com.kingofraccoon.punctualpatient.tools

import com.kingofraccoon.punctualpatient.Timetable
import com.kingofraccoon.punctualpatient.WorkTime

class DinamicTimeTable {
    fun updateTimeTable(minutes: Int, timetable: Timetable): Timetable{
         timetable.talons.forEach {
             val arrayTime = it.time.split(":")
             val intArrayTime = mutableListOf<Int>()
             arrayTime.forEach {
                 intArrayTime.add(
                        it.trim().toInt()
                 )
             }
             it.time = WorkTime(intArrayTime.first(), intArrayTime.last()).apply { this.minutes += minutes }.toString()
        }
        return timetable
    }
}