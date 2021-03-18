package com.fpiardi.monitoringservices

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fpiardi.monitoringservices.adapters.DetailsErrorAdapter
import com.fpiardi.monitoringservices.model.DetailsError
import com.fpiardi.monitoringservices.network.RetrofitFactory
import kotlinx.android.synthetic.main.activity_source_errors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsErrorsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_errors)

        val hours = intent.getIntExtra(EXTRA_HOURS,0)
        val source = intent.getStringExtra(EXTRA_SOURCE) ?:  String()

        title = source
        callAPI(source, hours)
    }

    private fun callAPI(source: String, hours: Int) {
        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getSourceErrorDetail(source, hours)
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

    private fun initRecyclerView(list: List<DetailsError>) {
        val orderedList = list.sortedByDescending { it.date }
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = DetailsErrorAdapter(orderedList, this)
        progressBar.visibility = View.GONE
    }
}