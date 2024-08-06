package com.example.sampleretrofitapp.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sampleretrofitapp.model.Product
import com.example.sampleretrofitapp.network.ApiService
import com.example.sampleretrofitapp.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProductListingScreen() {
    val context = LocalContext.current
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val apiService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        apiService.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        products = it
                    } ?: run {
                        Toast.makeText(context, "No products available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Error fetching products", Toast.LENGTH_SHORT).show()
                }
                isLoading = false
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(context, "Error fetching products", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Text(text = "Loading...", textAlign = TextAlign.Center)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(products) { product ->
                    Column(
                        modifier = Modifier
                            .clickable {
                                Toast.makeText(context, "Clicked on ${product.title}", Toast.LENGTH_SHORT).show()
                            }
                            .padding(8.dp)
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Text(text = product.title, textAlign = TextAlign.Center, color = Color.Black)
                        Text(text = product.description, color = Color.DarkGray)
                        Text(text = "Category: ${product.category}", color = Color.Gray)
                        Text(text = "Price: \$${product.price}", color = Color.Black)
                    }
                }
            }
        }
    }
}
