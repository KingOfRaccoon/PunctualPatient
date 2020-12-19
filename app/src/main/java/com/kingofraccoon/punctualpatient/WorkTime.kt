package com.kingofraccoon.punctualpatient

class WorkTime(
    var hours: Int,
    var minutes: Int = 0
) {

    override fun toString(): String {
        if (minutes < 60) {
            return getTime()
        } else {
            hours += minutes / 60
            minutes %= 60
            return getTime()
        }
    }

    private fun getTime(): String {
        if (minutes < 10)
            return "$hours : 0$minutes"
        else
            return "$hours : $minutes"
    }

    operator fun minus(workTime: WorkTime): WorkTime {
        this.hours -= workTime.hours
        this.minutes -= workTime.minutes
        if (this.minutes < 0) {
            this.hours -= 1
            this.minutes += 60
        }
        return this
    }

    fun getAllMinutes(): Int {
        return hours * 60 + minutes
    }
}