package com.example.snooziroo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class NameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        val nameEnter: EditText = findViewById(R.id.nameTextBox)

        val doneButton = findViewById<Button>(R.id.doneButton2)
        doneButton.setOnClickListener() {
            val enteredName = nameEnter.text.toString()

            if (enteredName.isNotEmpty()) {
                Toast.makeText(this, "$enteredName is a great name!", Toast.LENGTH_SHORT).show()
                val snoozie = Snoozie.getInstance()
                snoozie.setSnoozieName(enteredName)
                val intent = Intent(this,SetupCompleteActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a valid name.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}