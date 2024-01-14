package com.example.snooziroo

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

class SnoozieSharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("snoozie_preferences", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val snoozieNameKey = "snoozie_name"
    private val snoozieColourKey = "snoozie_colour"
    private val snoozieHealthKey = "snoozie_health"
    private val wakeTimesKey = "wake_times"
    private val sleepTimesKey = "sleep_times"
    private val setupCompleteKey = "setup_complete"

    var snoozieName: String?
        get() = sharedPreferences.getString(snoozieNameKey, null)
        set(value) = sharedPreferences.edit().putString(snoozieNameKey, value).apply()

    var snoozieColour: Int
        get() = sharedPreferences.getInt(snoozieColourKey, Color.BLACK)
        set(value) = sharedPreferences.edit().putInt(snoozieColourKey, value).apply()

    var snoozieHealth: Int
        get() = sharedPreferences.getInt(snoozieHealthKey, 5)
        set(value) = sharedPreferences.edit().putInt(snoozieHealthKey, value).apply()

    var wakeTimes: Set<String>
        get() = sharedPreferences.getStringSet(wakeTimesKey, setOf("12:00 AM")) ?: setOf("12:00 AM")
        set(value) = sharedPreferences.edit().putStringSet(wakeTimesKey, value).apply()

    var sleepTimes: Set<String>
        get() = sharedPreferences.getStringSet(sleepTimesKey, setOf("12:00 AM")) ?: setOf("12:00 AM")
        set(value) = sharedPreferences.edit().putStringSet(sleepTimesKey, value).apply()

    var setupComplete: Boolean
        get() = sharedPreferences.getBoolean(setupCompleteKey, false)
        set(value) = sharedPreferences.edit().putBoolean(setupCompleteKey, value).apply()

    fun setWakeTime(dayIndex: Int, time: String) {
        editor.putString("wakeTime_$dayIndex", time)
        editor.apply()
    }

    fun getWakeTime(dayIndex: Int): String? {
        return sharedPreferences.getString("wakeTime_$dayIndex", null)
    }

    fun setSleepTime(dayIndex: Int, time: String) {
        editor.putString("sleepTime_$dayIndex", time)
        editor.apply()
    }

    fun getSleepTime(dayIndex: Int): String? {
        return sharedPreferences.getString("sleepTime_$dayIndex", null)
    }
}