package com.fpiardi.monitoringservices

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fpiardi.monitoringservices.adapters.SourceErrorsAdapter
import com.fpiardi.monitoringservices.model.SourceErrors
import com.fpiardi.monitoringservices.network.RetrofitFactory
import kotlinx.android.synthetic.main.activity_source_errors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val EXTRA_SOURCE = "EXTRA_SOURCE"

class SourceErrorsActivity : AppCompatActivity() {

    private var intentHours = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_errors)

        intentHours = intent.getIntExtra(EXTRA_HOURS,1)
        title = "Errors by source in last $intentHours hours"
        callAPI(intentHours)
    }

    private fun callAPI(hours: Int) {
        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getSourceErrors(hours)
            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let { initRecyclerView(it) }
                    } else {
                        showError("Error network operation failed with ${response.code()} ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                showError("Exception ${e.message}")
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT)
    }

    private fun initRecyclerView(list: List<SourceErrors>) {
        val orderedList = list.sortedByDescending { it.noErrors }
        val adapter = SourceErrorsAdapter(orderedList, this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
        progressBar.visibility = View.GONE

        adapter.onItemClick = { item ->
            val intent = Intent(this, DetailsErrorsActivity::class.java).apply {
                putExtra(EXTRA_HOURS, intentHours)
                putExtra(EXTRA_SOURCE, item.source)
            }
            startActivity(intent)
        }
    }
}