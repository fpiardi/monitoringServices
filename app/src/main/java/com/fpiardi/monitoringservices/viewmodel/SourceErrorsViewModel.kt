package com.fpiardi.monitoringservices.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fpiardi.monitoringservices.model.SourceErrors
import com.fpiardi.monitoringservices.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SourceErrorsViewModel : ViewModel() {

    private val _status = MutableLiveData<Status>()
    internal val status: LiveData<Status>
        get() = _status

    fun fetchData(hours: Int) {
        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getSourceErrors(hours)
            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _status.value = Status.Success(response.body())
                    } else {
                        _status.value = Status.Error("Error network operation failed with ${response.code()} ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                _status.value = Status.Error("Exception ${e.message}")
            }
        }
    }

    internal sealed class Status {
        data class Success(val list: List<SourceErrors>?) : Status()
        data class Error(val message: String) : Status()
    }
}