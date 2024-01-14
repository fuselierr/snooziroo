package com.example.snooziroo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SnoozieSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snoozie_search)

        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener() {
            val intent = Intent(this,FunctionalActivity::class.java)
            startActivity(intent)
        }
    }
}