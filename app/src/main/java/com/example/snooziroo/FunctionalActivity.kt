package com.example.snooziroo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FunctionalActivity : AppCompatActivity() {

    private lateinit var timeTextView: TextView
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_functional)

        timeTextView = findViewById(R.id.timeText)

    }

    private fun updateTime() {
        val updateTimeRunnable = object : Runnable {
            override fun run() {
                val currentTime = getCurrentTime()
                timeTextView.text = currentTime
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        return timeFormat.format(Date())
    }
}