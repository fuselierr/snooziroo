package com.example.snooziroo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.StartButton)
        startButton.setOnClickListener {
            val intent = Intent(this,ExplainActivity::class.java)
            startActivity(intent)
        }

    }
}
