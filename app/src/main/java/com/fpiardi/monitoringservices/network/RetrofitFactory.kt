package com.fpiardi.monitoringservices.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    //REPLACE WITH YOUR BASE URL
    //Caused by: java.lang.IllegalArgumentException: baseUrl must end in /
    private const val BASE_URL = "replaceWithYourBaseUrl"

    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
}