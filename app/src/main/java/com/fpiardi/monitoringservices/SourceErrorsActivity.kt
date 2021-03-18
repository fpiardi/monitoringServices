package com.fpiardi.monitoringservices

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.fpiardi.monitoringservices.adapters.SourceErrorsAdapter
import com.fpiardi.monitoringservices.model.SourceErrors
import com.fpiardi.monitoringservices.viewmodel.SourceErrorsViewModel
import kotlinx.android.synthetic.main.activity_source_errors.*


const val EXTRA_SOURCE = "EXTRA_SOURCE"

class SourceErrorsActivity : AppCompatActivity() {

    private val viewModel: SourceErrorsViewModel by viewModel()
    private var intentHours = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_errors)

        intentHours = intent.getIntExtra(EXTRA_HOURS,1)
        title = "Errors by source in last $intentHours hours"

        viewModel.fetchData(intentHours)
        viewModel.status.observe(this, Observer {
            when (it) {
                is SourceErrorsViewModel.Status.Error -> {
                    showError(it.message)
                }
                is SourceErrorsViewModel.Status.Success -> {
                    it.list?.let { data -> showSuccess(data) }

                    progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun showError(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT)
    }

    private fun showSuccess(list: List<SourceErrors>) {
        val orderedList = list.sortedByDescending { it.noErrors }
        val adapter = SourceErrorsAdapter(orderedList, this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        adapter.onItemClick = { item ->
            val intent = Intent(this, DetailsErrorsActivity::class.java).apply {
                putExtra(EXTRA_HOURS, intentHours)
                putExtra(EXTRA_SOURCE, item.source)
            }
            startActivity(intent)
        }
    }
}