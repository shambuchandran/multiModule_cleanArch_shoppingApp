package com.example.shoppingproto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppingproto.ui.feature.home.HomeScreen
import com.example.shoppingproto.ui.theme.ShoppingprotoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingprotoTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "Home"){
                    composable("home") {
                        HomeScreen(navController)
                    }
                }
            }
        }
    }
}
