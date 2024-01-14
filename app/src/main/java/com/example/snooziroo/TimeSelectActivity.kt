package com.example.snooziroo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import java.util.Calendar
import java.util.Date

class TimeSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_select)

        val times = generateTimes("12:00 AM")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, times)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val weekdayWakeSpinner: Spinner = findViewById(R.id.weekdayWake)
        val weekdaySleepSpinner: Spinner = findViewById(R.id.weekdaySleep)
        val weekendWakeSpinner: Spinner = findViewById(R.id.weekendWake)
        val weekendSleepSpinner: Spinner = findViewById(R.id.weekendSleep)

        weekdayWakeSpinner.adapter = adapter
        weekdaySleepSpinner.adapter = adapter
        weekendWakeSpinner.adapter = adapter
        weekendSleepSpinner.adapter = adapter

        val doneButton = findViewById<Button>(R.id.doneButton)
        doneButton.setOnClickListener() {
            val intent = Intent(this,SnoozieSearchActivity::class.java)
            startActivity(intent)
        }

    }

    private fun generateTimes(startTime: String): List<String> {
        val times = mutableListOf<String>()
        val formatter = java.text.SimpleDateFormat("hh:mm a")

        val calendar = Calendar.getInstance()
        calendar.time = formatter.parse(startTime) ?: Date()
        for (i in 0 until 48) {
            times.add(formatter.format(calendar.time))
            calendar.add(java.util.Calendar.MINUTE, 30)
        }

        return times
    }
}