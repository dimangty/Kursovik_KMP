package com.example.kursovikkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello, $name!",
        style = MaterialTheme.typography.titleMedium
    )
}

@Preview
@Composable
fun PreviewGreeting() {
    MaterialTheme {
        Greeting("Android Test")
    }
}