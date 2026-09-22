package com.example.android_app.assignments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_app.ui.theme.ProfileCardTheme

/**
 * =======================================================================
 * HOW TO USE THIS TEMPLATE FOR NEW ASSIGNMENTS:
 * =======================================================================
 * 1. Copy this file into a new folder, e.g.:
 *    `assignments/pertemuan_4/assignment1_calculator.kt`
 *
 * 2. Update the `package` line at the very top of your new file:
 *    `package com.example.android_app.assignments.pertemuan_4`
 *
 * 3. Rename `AssignmentTemplateScreen` and its preview to your new screen name:
 *    e.g., `CalculatorScreen` and `CalculatorPreview`
 *
 * 4. Register the new screen in:
 *    - `MainActivity.kt` (add to `sealed class Screen` and `when (currentScreen)` block)
 *    - `HomeScreen.kt` (add a new `AssignmentCard` to the list)
 * =======================================================================
 */

@Composable
fun AssignmentTemplateScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Assignment Title",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Replace this with your assignment UI / composables.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Example interactive state (feel free to remove or modify)
            var count by remember { mutableIntStateOf(0) }

            Button(onClick = { count++ }) {
                Text("Clicked $count times")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AssignmentTemplatePreview() {
    ProfileCardTheme {
        AssignmentTemplateScreen()
    }
}
