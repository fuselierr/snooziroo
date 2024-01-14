package com.example.snooziroo

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlin.math.abs
import kotlin.math.max

class SnoozieSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snoozie_search)

        val snoozie = Snoozie.getInstance()
        var expectedWake = 0f
        var expectedSleep = 0f
        val weight: Float = 1.0f / 7.0f
        for (i in 0..6) {
            expectedWake += Snoozie.convertHoursPastMidnight(snoozie.getWakeTime(i)) * weight
            expectedSleep += Snoozie.convertHoursPastMidnight(snoozie.getSleepTime(i)) * weight
        }
        val expectedSleepLength = abs(expectedWake - expectedSleep)

        var r = 255f
        var g = 255f
        var b = 255f
        var colourMultiplier = 1f

        var colourInt = -1
        if (expectedSleepLength <= 6) {
            colourInt = 0
        } else if (expectedSleepLength <= 7) {
            colourInt = 1
        } else if (expectedSleepLength <= 7.75) {
            colourInt = 2
        } else if (expectedSleepLength <= 8.25) {
            colourInt = 3
        } else if (expectedSleepLength <= 9) {
            colourInt = 4
        } else {
            colourInt = 5
        }

        if (expectedSleepLength <= 5.5) { // red
            r *= colourMultiplier
            g = 0f
            b = 0f
        } else if (expectedSleepLength <= 6.5f) { // orange
            val shadeValue = expectedSleepLength - 5.5f
            r *= colourMultiplier
            g = 128f * shadeValue * colourMultiplier
            b = 0f
        } else if (expectedSleepLength <= 7.5f) { // yellow
            val shadeValue = expectedSleepLength - 6.5f
            r *= colourMultiplier
            g = 128f + 128f * shadeValue * colourMultiplier
            b = 0f
        } else if (expectedSleepLength <= 8f) { // green
            val shadeValue = (expectedSleepLength - 7.5f) * 2f
            r = 255f - 191f * shadeValue * colourMultiplier
            g = 255f
            b = 0f
        } else if (expectedSleepLength <= 8.5f) { // blue
            val shadeValue = (expectedSleepLength - 8f) * 2f
            r = 64f - 64f * shadeValue * colourMultiplier
            g = 255f - 255f * shadeValue * colourMultiplier
            b = 255f * shadeValue * colourMultiplier
        } else if (expectedSleepLength <= 9.5f) { // violet
            val shadeValue = expectedSleepLength - 8.5f
            r = 128f * shadeValue * colourMultiplier
            g = 0f
            b = 255f * colourMultiplier
        } else {
            r = 128f * colourMultiplier
            g = 0f
            b = 255f * colourMultiplier
        }

        val message = when (colourInt) {
            0 -> "Red Snoozies need the least amount of sleep. They have warm, burning personalities that allow them to smolder for long periods of time with little rest."
            1 -> "Orange Snoozies are just yappers. Although slightly aggressive, they can be extremely sweet and outgoing as well as adapting quickly to different environments."
            2 -> "Yellow Snoozies are Snoozies of balance. They love following the flow of nature and they are curious and open to experiencing new things."
            3 -> "Green Snoozies are harmonious creatures that are strongly sociable and confident. They love engaging in entertaining activities and will wake up at the drop of a pen."
            4 -> "Blue Snoozies are thinkers and dreamers. They are strongly creative and often get lost in their own existential crises of Snoozie life."
            5 -> "Purple Snoozies are the most laid-back of the bunch. They enjoy long periods of rest, with a calm, soothing, and peaceful allure."
            else -> "Something went wrong."
        }

        val snoozieDescribe : TextView = findViewById(R.id.snoozieDescribe)
        snoozieDescribe.text = message

        snoozie.setSnoozieColour(Color.rgb(r.toInt(), g.toInt(), b.toInt()))

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener() {
            val intent = Intent(this,NameActivity::class.java)
            startActivity(intent)
        }
    }
}