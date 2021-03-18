package com.fpiardi.monitoringservices

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fpiardi.monitoringservices.adapters.DetailsErrorAdapter
import com.fpiardi.monitoringservices.model.DetailsError
import com.fpiardi.monitoringservices.viewmodel.DetailsErrorViewModel
import kotlinx.android.synthetic.main.activity_source_errors.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsErrorsActivity : AppCompatActivity() {

    private val viewModel: DetailsErrorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_errors)

        val hours = intent.getIntExtra(EXTRA_HOURS,0)
        val source = intent.getStringExtra(EXTRA_SOURCE) ?:  String()

        title = source

        viewModel.fetchData(source, hours)
        viewModel.status.observe(this, Observer {
            when (it) {
                is DetailsErrorViewModel.Status.Error -> {
                    showError(it.message)
                }
                is DetailsErrorViewModel.Status.Success -> {
                    it.list?.let { data -> showSuccess(data) }

                    progressBar.visibility = View.GONE
                }
            }

        })
    }

    private fun showError(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT)
    }

    private fun showSuccess(list: List<DetailsError>) {
        val orderedList = list.sortedByDescending { it.date }
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = DetailsErrorAdapter(orderedList, this)
    }
}