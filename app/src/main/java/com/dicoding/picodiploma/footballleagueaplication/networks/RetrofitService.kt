package com.dicoding.picodiploma.footballleagueaplication.networks

import com.dicoding.picodiploma.footballleagueaplication.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private var retrofit: Retrofit? = null
    fun <S>createService(serviceClass: Class<S>):S? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit?.create(serviceClass)
    }
}
