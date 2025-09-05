package com.example.kursovikkmp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import kursovikkmp.shared.generated.resources.Res
import kursovikkmp.shared.generated.resources.ic_example
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Greeting(name: String) {
    Column {
        Text(
            text = "Hello, $name!",
            style = MaterialTheme.typography.titleMedium
        )

        Image(imageVector = vectorResource(Res.drawable.ic_example),
                contentDescription = "ic_example",
                modifier = Modifier
        )
    }

}

@Preview
@Composable
fun PreviewGreeting() {
    MaterialTheme {
        Greeting("Android Test")
    }
}

