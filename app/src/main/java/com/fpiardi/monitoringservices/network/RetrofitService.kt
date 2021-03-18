package com.fpiardi.monitoringservices.network

import com.fpiardi.monitoringservices.model.DetailsError
import com.fpiardi.monitoringservices.model.SourceErrors
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("{hours}")
    suspend fun getSourceErrors(@Path("hours") hours: Int): Response<List<SourceErrors>>

    @GET("{source}/errors")
    suspend fun getSourceErrorDetail(@Path("source") source: String,
                                     @Query("hours") hours: Int): Response<List<DetailsError>>
}