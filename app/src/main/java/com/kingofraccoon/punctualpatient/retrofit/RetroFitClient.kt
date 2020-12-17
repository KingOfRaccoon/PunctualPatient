package com.kingofraccoon.punctualpatient.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitClient {
    var retrofit : Retrofit? = null
    get() {
        if (field == null) {
            field = Retrofit.Builder()
                    .baseUrl("https://us-central1-punctual-patient.cloudfunctions.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return field
    }
    fun instance(): RetrofitApi?{
        return retrofit?.create(RetrofitApi::class.java)
    }
}