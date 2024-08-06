package com.example.sampleretrofitapp.network

import com.example.sampleretrofitapp.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getProducts(): Call<List<Product>>
}
