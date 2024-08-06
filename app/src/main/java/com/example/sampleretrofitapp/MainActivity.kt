package com.example.sampleretrofitapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sampleretrofitapp.ui.theme.SampleRetroFitAppTheme
import com.example.sampleretrofitapp.screens.ProductListingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleRetroFitAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { ButtonWithClickCounter(navController) }
        composable("product_list") { ProductListingScreen() }
    }
}

@Composable
fun ButtonWithClickCounter(navController: NavHostController) {
    val context = LocalContext.current
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Cyan, shape = RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No. of clicks :) : $count",
            color = Color.Red,
            style = TextStyle(fontFamily = FontFamily.Cursive, fontSize = 24.sp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //Button to decrement the count
            Button(
                onClick = {
                    if (count > 0) {
                        count--
                        myToast(context, "Bola tha click mat karo :) ")
                    } else {
                        myToast(context, "Zero ke niche ??? Seriously -_-")
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                content = {
                    Text(
                        text = "Click Mat Karna Mujhe !!!",
                        color = Color.Cyan,
                        style = TextStyle(fontFamily = FontFamily.Cursive)
                    )
                }
            )
            //Button to navigate to ProductListingScreen
            Button(
                onClick = {
                    navController.navigate("product_list")
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                content = {
                    Text(
                        text = "See Products",
                        color = Color.White,
                        style = TextStyle(fontFamily = FontFamily.Cursive)
                    )
                }
            )
            //Button to increment the count
            Button(
                onClick = {
                    myToast(context, "Clicked <3 ")
                    count++
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                content = {
                    Text(
                        text = "Click karo mujhe :)",
                        color = Color.Cyan,
                        style = TextStyle(fontFamily = FontFamily.Cursive)
                    )
                }
            )
        }
    }
}

fun myToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleRetroFitAppTheme {
        val navController = rememberNavController()
        AppNavHost(navController)
    }
}
