package com.example.hyeon.siksha2android.dev.Retrofit

import com.example.hyeon.siksha2android.dev.Model.Data
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @get:GET("menus")
    val data: Call<Data>
}
