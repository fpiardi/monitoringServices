package com.fpiardi.monitoringservices

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fpiardi.monitoringservices.viewmodel.SourceDetailErrorViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        buttonInsert.setOnClickListener {
            val intent = Intent(this, SourceDetailErrorActivity::class.java).apply {
                putExtra(EXTRA_HOURS, hours.text.toString().toInt())
            }
            startActivity(intent)
        }

        buttonDelete.setOnClickListener {
            val viewModel: SourceDetailErrorViewModel by viewModel()
            viewModel.deleteAll()
        }
    }
}