package com.kingofraccoon.punctualpatient.retrofit

import android.app.Person
import com.google.gson.annotations.SerializedName
import com.kingofraccoon.punctualpatient.Adress
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @GET("addMessage")
    fun addMessage(@Query("text") text: String): Call<Answer>

    @GET("check")
    fun check():Call<MutableList<DataPerson>>
}
data class Answer(
        @SerializedName("result")
     val result: String
)

data class DataPerson(
        var adress: String,
        var age: String,
        var date: String,
        var email: String,
        var name: String,
        var sex: String
)
