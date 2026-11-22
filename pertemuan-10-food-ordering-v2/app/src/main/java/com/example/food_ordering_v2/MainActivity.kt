package com.example.food_ordering_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.food_ordering_v2.ui.FoodOrderingApp
import com.example.food_ordering_v2.ui.theme.FoodOrderingV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemInDark = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemInDark) }

            FoodOrderingV2Theme(darkTheme = isDarkTheme) {
                FoodOrderingApp(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodOrderingAppPreview() {
    FoodOrderingV2Theme(darkTheme = false) {
        FoodOrderingApp(
            isDarkTheme = false,
            onToggleTheme = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FoodOrderingAppDarkPreview() {
    FoodOrderingV2Theme(darkTheme = true) {
        FoodOrderingApp(
            isDarkTheme = true,
            onToggleTheme = {}
        )
    }
}
