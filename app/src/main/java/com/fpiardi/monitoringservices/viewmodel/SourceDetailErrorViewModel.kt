package com.fpiardi.monitoringservices.viewmodel

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fpiardi.monitoringservices.data.SourceDetailErrorDao
import com.fpiardi.monitoringservices.model.SourceDetailError
import com.fpiardi.monitoringservices.network.RetrofitFactory
import kotlinx.coroutines.*

class SourceDetailErrorViewModel(private val dao: SourceDetailErrorDao) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _searchResults = MutableLiveData<List<SourceDetailError>>()
    val searchResults: LiveData<List<SourceDetailError>>
        get() = _searchResults

    private val _status = MutableLiveData<Status>()
    internal val status: LiveData<Status>
        get() = _status

    fun fetchData(hours: Int) {
        var list = mutableListOf<SourceDetailError>()

        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val sourceResponse = service.getSourceErrors(hours)

            if (sourceResponse.isSuccessful) {
                var sourceData = sourceResponse.body()

                sourceData?.forEach { source ->
                //var source = sourceData?.get(0)!!
                    val detailsResponse = service.getSourceErrorDetail(source.source, hours)
                    if (detailsResponse.isSuccessful) {
                        var details = detailsResponse.body()
                        details?.forEach {
                            list.add(
                                0,
                                SourceDetailError(
                                    source = source.source,
                                    date = it.date,
                                    name = it.name
                                )
                            )
                        }
                    }
                }

                list?.let {
                    withContext(Dispatchers.Main) {
                        _status.value = Status.Success(it)
                    }
                }

            }

        }
    }

    fun insertAll(data: List<SourceDetailError>) {
        viewModelScope.launch {
            dao.insertAll(data)
        }
    }

    fun search(query: Editable?) {
        viewModelScope.launch {
            if (query.isNullOrBlank()) {
                dao.all().let {
                    _searchResults.postValue(it)
                }
            } else {
                val sanitizedQuery = sanitizeSearchQuery(query)
                dao.search(sanitizedQuery).let {
                    _searchResults.postValue(it)
                }
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }

    private fun sanitizeSearchQuery(query: Editable?): String {
        if (query == null) {
            return ""
        }
        val queryWithEscapedQuotes = query.replace(Regex.fromLiteral("\""), "\"\"")
        return "%$queryWithEscapedQuotes%"
    }

    internal sealed class Status {
        data class Success(val list: List<SourceDetailError>?) : Status()
        data class Error(val message: String) : Status()
    }
}