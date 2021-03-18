package com.fpiardi.monitoringservices

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_HOURS = "EXTRA_HOURS"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val intent = Intent(this, SourceErrorsActivity::class.java).apply {
                putExtra(EXTRA_HOURS, hours.text.toString().toInt())
            }
            startActivity(intent)
        }
    }
}