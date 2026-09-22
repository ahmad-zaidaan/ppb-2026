package com.example.android_app.assignments.pertemuan_3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_app.ui.theme.ProfileCardTheme

@Composable
fun Pertemuan31Screen(modifier: Modifier = Modifier) {
    GreetingText(
        message = "Happy\nBirthday\nSam!",
        from = "From Emma",
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    )
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            fontSize = 90.sp,
            lineHeight = 105.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = from,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(top = 16.dp, end = 20.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Pertemuan31Preview() {
    ProfileCardTheme {
        Pertemuan31Screen()
    }
}
