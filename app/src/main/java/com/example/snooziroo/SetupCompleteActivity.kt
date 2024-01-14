package com.example.snooziroo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SetupCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_complete)

        val getStartedButton = findViewById<Button>(R.id.startButton2)
        getStartedButton.setOnClickListener() {
            val intent = Intent(this,FunctionalActivity::class.java)
            startActivity(intent)
        }
    }
}