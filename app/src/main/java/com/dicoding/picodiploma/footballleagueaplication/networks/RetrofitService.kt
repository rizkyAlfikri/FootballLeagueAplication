package com.dicoding.picodiploma.footballleagueaplication.networks

import com.dicoding.picodiploma.footballleagueaplication.BuildConfig
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okreplay.OkReplayInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val okReplayInterceptor = OkReplayInterceptor()

    private fun init(): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(okReplayInterceptor)
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(builder.build())
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return init().create(service)
    }
}