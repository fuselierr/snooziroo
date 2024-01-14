package com.example.snooziroo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FunctionalActivity : AppCompatActivity() {

    private lateinit var snoozieSharedPreferences: SnoozieSharedPreferences

    private lateinit var timeTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var handler: Handler
    private lateinit var updateTimeRunnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_functional)

        snoozieSharedPreferences = SnoozieSharedPreferences(this)

        val snoozie = Snoozie.getInstance()

        var currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2
        if (currentDay == -1) {
            currentDay = 6
        }
        var nextDay = currentDay + 1
        if (nextDay > 6) {
            nextDay = 0
        }

        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        if (currentTime == snoozie.getWakeTime(currentDay)) {
            Snoozie.isBedtime = false
            snoozie.setFed(false)
        } else if (currentTime == snoozie.getSleepTime(currentDay)) {
            Snoozie.isBedtime = true
        }

        val feedButton = findViewById<Button>(R.id.feedButton)
        feedButton.setOnClickListener() {
            Snoozie.sendNotification("This is a test", applicationContext)
            if (!Snoozie.isBedtime && Snoozie.isTimeAfter(currentTime, addMinutesToTime(snoozie.getWakeTime(currentDay), 5))) {
                Toast.makeText(applicationContext, "${snoozie.getSnoozieName()} has been fed!", Toast.LENGTH_SHORT).show()
                snoozie.setFed(true)
            } else {
                Toast.makeText(applicationContext, "Cannot feed! ${snoozie.getSnoozieName()} is not hungry yet.", Toast.LENGTH_SHORT).show()
            }
        }

        val snoozieName : TextView = findViewById(R.id.snoozieName)
        snoozieName.setTextColor(Color.WHITE)
        snoozieName.text = snoozie.getSnoozieName()

        val nextSleepTime : TextView = findViewById(R.id.nextSleepTime)
        val nextWakeTime : TextView = findViewById(R.id.nextWakeTime)

        nextSleepTime.text = "Next Sleep: ${snoozie.getSleepTime(currentDay)}"
        nextWakeTime.text = "Next Wake: ${snoozie.getWakeTime(nextDay)}"

        timeTextView = findViewById(R.id.timeText)
        dateTextView = findViewById(R.id.dateText)

        handler = Handler(Looper.getMainLooper())
        updateTimeRunnable = object : Runnable {
            override fun run() {
                updateTime()
                updateDate()
            }
        }

        handler.post(updateTimeRunnable)

    }

    override fun onPause() {
        super.onPause()
        saveSnoozieData()
    }

    override fun onResume() {
        super.onResume()
        val temp = Snoozie.getInstance()
        //loadSnoozieData()
    }

    private fun updateDate() {
        val currentDate = getCurrentDate()
        dateTextView.text = currentDate
    }

    private fun updateTime() {
        val currentTime = getCurrentTime()
        timeTextView.text = currentTime
        handler.postDelayed(updateTimeRunnable, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        saveSnoozieData()
        handler.removeCallbacks(updateTimeRunnable)
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("h:mm:ss a", Locale.getDefault())
        return timeFormat.format(calendar.time)
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun saveSnoozieData() {
        val snoozie = Snoozie.getInstance()

        snoozieSharedPreferences.snoozieName = snoozie.getSnoozieName()
        snoozieSharedPreferences.snoozieColour = snoozie.getSnoozieColour()
        snoozieSharedPreferences.snoozieHealth = snoozie.getSnoozieHealth()

        for (dayIndex in 0..6) {
            snoozieSharedPreferences.setWakeTime(dayIndex, snoozie.getWakeTime(dayIndex))
            snoozieSharedPreferences.setSleepTime(dayIndex, snoozie.getSleepTime(dayIndex))
        }

        snoozieSharedPreferences.setupComplete = snoozie.doneSetup()
    }

    private fun loadSnoozieData() {
        val snoozie = Snoozie.getInstance()

        snoozie.setSnoozieName(snoozieSharedPreferences.snoozieName ?: "")
        snoozie.setSnoozieColour(snoozieSharedPreferences.snoozieColour)
        snoozie.setSnoozieHealth(snoozieSharedPreferences.snoozieHealth)

        for (dayIndex in 0..6) {
            snoozie.setWakeTime(dayIndex, snoozieSharedPreferences.getWakeTime(dayIndex) ?: "")
            snoozie.setSleepTime(dayIndex, snoozieSharedPreferences.getSleepTime(dayIndex) ?: "")
        }

        snoozie.setupComplete()
    }

    private fun addMinutesToTime(startTime: String, minutesToAdd: Int): String {
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

        val date = formatter.parse(startTime) ?: Date()

        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MINUTE, minutesToAdd)

        return formatter.format(calendar.time)
    }

}