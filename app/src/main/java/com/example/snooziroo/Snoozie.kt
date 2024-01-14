package com.example.snooziroo

import android.graphics.Color

class Snoozie private constructor() {

    private var snoozieName: String? = null
    private var snoozieColour: Int = Color.BLACK
    private var snoozieHealth: Int = 5

    private val wakeTimes = mutableListOf<List<String>>()
    private val sleepTimes = mutableListOf<List<String>>()

    fun setSnoozieName(name: String) {
        this.snoozieName = name
    }

    fun getSnoozieName(): String? {
        return snoozieName
    }

    fun setSnoozieColour(colour: Int) {
        this.snoozieColour = colour
    }

    fun getSnoozieColour(): Int {
        return snoozieColour
    }

    fun getSnoozieHealth(): Int {
        return snoozieHealth
    }

    fun setSnoozieHealth(health: Int) {
        this.snoozieHealth = health
    }

    companion object {

        private var instance: Snoozie? = null

        @Synchronized
        fun getInstance(): Snoozie {
            if (instance == null) {
                instance = Snoozie()
            }
            return instance as Snoozie
        }

    }

}
