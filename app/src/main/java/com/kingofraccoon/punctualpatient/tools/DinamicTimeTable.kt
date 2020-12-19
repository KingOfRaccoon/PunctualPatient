package com.kingofraccoon.punctualpatient.tools

import com.kingofraccoon.punctualpatient.Timetable
import com.kingofraccoon.punctualpatient.WorkTime
import com.kingofraccoon.punctualpatient.model.Talon
import java.time.Duration

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

    fun getDifference(talon:Talon, time:String, duration: Int): Int {
        val arrayTime = talon.time.split(":")
        val intArrayTime = mutableListOf<Int>()
        arrayTime.forEach {
            intArrayTime.add(
                    it.trim().toInt()
            )
        }
        val wt1 = WorkTime(intArrayTime.first(), intArrayTime.last()+duration)

        val arrayTime1 = time.split(":")
        val intArrayTime1 = mutableListOf<Int>()
        arrayTime1.forEach {
            intArrayTime1.add(
                    it.trim().toInt()
            )
        }
        val wt2 = WorkTime(intArrayTime1.first(), intArrayTime1.last())

        return wt2.minus(wt1).getAllMinutes()
    }
}