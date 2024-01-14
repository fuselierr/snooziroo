package com.example.snooziroo

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.preference.PreferenceDataStore
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Snoozie private constructor() {

    private var doneSetup = false
    private var fed = false

    private var snoozieName: String? = null
    private var snoozieColour: Int = Color.BLACK
    private var snoozieHealth: Int = 5

    private var wakeTimes = Array(7) { "12:00 AM" }
    private var sleepTimes = Array(7) { "12:00 AM" }

    fun doneSetup() : Boolean {
        return doneSetup
    }

    fun setupComplete() {
        this.doneSetup = true
    }

    fun isFed() : Boolean {
        return fed
    }

    fun setFed(state: Boolean) {
        this.fed = state
    }

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

    fun setWakeTime(dayIndex: Int, time: String) {
        require(dayIndex in 0..6) { "Invalid day index" }
        wakeTimes[dayIndex] = time
    }

    fun getWakeTime(dayIndex: Int) : String {
        require(dayIndex in 0..6) {"Invalid day index"}
        return wakeTimes[dayIndex]
    }

    fun setSleepTime(dayIndex: Int, time: String) {
        require(dayIndex in 0..6) {"Invalid day index"}
        sleepTimes[dayIndex] = time
    }

    fun getSleepTime(dayIndex: Int) : String {
        require(dayIndex in 0..6) {"Invalid day index"}
        return sleepTimes[dayIndex]
    }

    companion object {
        private var instance: Snoozie? = null
        var isBedtime = false

        @Synchronized
        fun getInstance(): Snoozie {
            if (instance == null) {
                instance = Snoozie()
            }
            return instance as Snoozie
        }

        fun convertHoursPastMidnight(time: String): Float {
            val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val date = formatter.parse(time) ?: Date()
            val calendar = Calendar.getInstance()
            calendar.time = date
            val hours = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)

            return hours.toFloat() + minutes.toFloat() / 60.0f
        }

        fun isTimeAfter(time1: String, time2: String): Boolean {
            val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

            val date1 = formatter.parse(time1)
            val date2 = formatter.parse(time2)

            if (date1 == null || date2 == null) {
                return false
            }

            return date2.after(date1)
        }

        fun sendNotification(message: String, context: Context) {
            val channelID = "Snooziroo Notifications"

            createNotificationChannel(message, context)

            val builder = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Snooziroo")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED) {
                    return
                }
                notify(1, builder.build())
            }
        }

        private fun createNotificationChannel(message: String, context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel("Snooziroo Notifications", "Snooziroo", NotificationManager.IMPORTANCE_DEFAULT)
                channel.description = message

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

    }

}
