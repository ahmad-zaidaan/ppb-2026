package com.example.android_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.android_app.assignments.pertemuan_2.ProfileScreen
import com.example.android_app.assignments.pertemuan_3.GreetingCardScreen
import com.example.android_app.assignments.pertemuan_3.Pertemuan31Screen
import com.example.android_app.ui.theme.ProfileCardTheme

/**
 * Screen destination definitions.
 * Add new weeks/assignments here!
 */
sealed class Screen {
    data object Home : Screen()
    data object Pertemuan2 : Screen()
    data object Pertemuan31 : Screen()
    data object Pertemuan32 : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfileCardTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

                // Intercept back button/gesture: return to Home if currently on an assignment screen
                BackHandler(enabled = currentScreen !is Screen.Home) {
                    currentScreen = Screen.Home
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            is Screen.Home -> HomeScreen(
                                onSelectScreen = { screen -> currentScreen = screen }
                            )
                            is Screen.Pertemuan2 -> ProfileScreen()
                            is Screen.Pertemuan31 -> Pertemuan31Screen()
                            is Screen.Pertemuan32 -> GreetingCardScreen()
                        }
                    }
                }
            }
        }
    }
}