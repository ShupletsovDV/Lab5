package com.example.lab55

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import com.example.lab55.ui.theme.Lab55Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab55Theme {
                OrderFormScreen()
            }
        }
    }
}

@Composable
fun OrderFormScreen() {
    var orderAmount by remember { mutableStateOf("") }
    var dishCount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 56.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        InputRow(
            label = "Сумма заказа:",
            value = orderAmount,
            onValueChange = { orderAmount = it },
            keyboardType = KeyboardType.Decimal,
            fieldWidth = 156.dp
        )
        InputRow(
            label = "Количество блюд:",
            value = dishCount,
            onValueChange = { dishCount = it },
            keyboardType = KeyboardType.Number,
            fieldWidth = 60.dp
        )
    }
}

@Composable
private fun InputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    fieldWidth: Dp
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .width(fieldWidth)
                .height(40.dp)
                .background(Color(0xFFFFA3C1))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OrderFormScreenPreview() {
    Lab55Theme {
        OrderFormScreen()
    }
}