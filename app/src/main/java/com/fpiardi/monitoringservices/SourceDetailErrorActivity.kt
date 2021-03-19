package com.fpiardi.monitoringservices

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fpiardi.monitoringservices.adapters.SourceDetailsErrorAdapter
import com.fpiardi.monitoringservices.model.SourceDetailError
import com.fpiardi.monitoringservices.viewmodel.SourceDetailErrorViewModel
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SourceDetailErrorActivity : AppCompatActivity() {

    private val viewModel: SourceDetailErrorViewModel by viewModel()
    private var intentHours = 0
    private var adapter: SourceDetailsErrorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        intentHours = intent.getIntExtra(EXTRA_HOURS,1)
        title = "Errors from room database"

        viewModel.fetchData(intentHours)
        viewModel.status.observe(this, Observer {
            when (it) {
                is SourceDetailErrorViewModel.Status.Error -> {
                    showError(it.message)
                }
                is SourceDetailErrorViewModel.Status.Success -> {
                    it.list?.let { data ->
                        addToDatabase(data)
                        showSuccess(data)
                    }

                    progressBar.visibility = View.GONE
                }
            }
        })

        viewModel.searchResults.observe(this, Observer {
            showSuccess(it)
        })

        button.setOnClickListener {
            search()
        }
    }

    private fun search() {
        viewModel.search(searchBox.editableText)
    }

    private fun addToDatabase(data: List<SourceDetailError>) {
        viewModel.insertAll(data)
        search()
    }

    private fun showError(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT)
    }

    private fun showSuccess(list: List<SourceDetailError>) {
        val orderedList = list.sortedByDescending { it.date }
        adapter = SourceDetailsErrorAdapter(orderedList, this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }
}