package com.example.snooziroo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ExplainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explain)

        val okButton = findViewById<Button>(R.id.okButton)
        okButton.setOnClickListener {
            val intent = Intent(this,TimeSelectActivity::class.java)
            startActivity(intent)
        }
    }
}