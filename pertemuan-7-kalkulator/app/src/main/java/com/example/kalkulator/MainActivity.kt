package com.example.kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalkulator.ui.theme.KalkulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFF5F5F5)
                ) { innerPadding ->
                    KalkulatorApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun KalkulatorApp(modifier: Modifier = Modifier) {
    var angka1 by remember { mutableStateOf("") }
    var angka2 by remember { mutableStateOf("") }
    var hasil by remember { mutableStateOf("") }

    fun hitung(operator: String) {
        val num1 = angka1.toDoubleOrNull()
        val num2 = angka2.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            hasil = "Masukkan kedua angka!"
            return
        }

        val result = when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> {
                if (num2 != 0.0) num1 / num2
                else {
                    hasil = "Tidak bisa dibagi nol!"
                    return
                }
            }
            else -> {
                hasil = "Operator tidak valid!"
                return
            }
        }

        hasil = if (result.isFinite()) {
            if (result % 1.0 == 0.0) {
                result.toLong().toString()
            } else {
                result.toString()
            }
        } else {
            "Hasil tidak valid!"
        }
    }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color(0xFF121212),
        unfocusedTextColor = Color(0xFF121212),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        focusedBorderColor = Color(0xFF121212),
        unfocusedBorderColor = Color(0xFFCCCCCC),
        focusedLabelColor = Color(0xFF121212),
        unfocusedLabelColor = Color(0xFF666666)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Kalkulator",
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF121212)
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = angka1,
            onValueChange = { angka1 = it },
            label = { Text("Input Angka 1") },
            placeholder = { Text("Masukkan angka...") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = angka2,
            onValueChange = { angka2 = it },
            label = { Text("Input Angka 2") },
            placeholder = { Text("Masukkan angka...") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hasil Perhitungan",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color(0xFF333333),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(
                    Color.White,
                    RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = hasil.ifEmpty { "0" },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF121212)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TombolOperasi(
                simbol = "+",
                warna = Color(0xFF2C2C2C),
                warnaTeks = Color.White,
                modifier = Modifier.weight(1f)
            ) { hitung("+") }

            TombolOperasi(
                simbol = "−",
                warna = Color(0xFF2C2C2C),
                warnaTeks = Color.White,
                modifier = Modifier.weight(1f)
            ) { hitung("-") }

            TombolOperasi(
                simbol = "×",
                warna = Color(0xFF2C2C2C),
                warnaTeks = Color.White,
                modifier = Modifier.weight(1f)
            ) { hitung("*") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TombolOperasi(
                simbol = "÷",
                warna = Color(0xFF2C2C2C),
                warnaTeks = Color.White,
                modifier = Modifier.weight(1f)
            ) { hitung("/") }

            TombolOperasi(
                simbol = "Clear",
                warna = Color(0xFFE0E0E0),
                warnaTeks = Color(0xFF121212),
                modifier = Modifier.weight(2f)
            ) {
                angka1 = ""
                angka2 = ""
                hasil = ""
            }
        }
    }
}

@Composable
fun TombolOperasi(
    simbol: String,
    warna: Color,
    warnaTeks: Color = Color.White,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(55.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = warna,
            contentColor = warnaTeks
        ),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        Text(
            text = simbol,
            fontSize = if (simbol == "Clear") 18.sp else 26.sp,
            fontWeight = FontWeight.Bold,
            color = warnaTeks
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KalkulatorPreview() {
    KalkulatorTheme {
        KalkulatorApp()
    }
}