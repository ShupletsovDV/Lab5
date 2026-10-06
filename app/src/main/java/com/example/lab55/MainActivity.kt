package com.example.lab55

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lab55.ui.theme.Lab55Theme
import java.util.Locale

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
    var tipsPercent by remember { mutableStateOf(0f) }
    val selectedDiscount = discountForDishCount(dishCount.toIntOrNull())

    val amountValue = orderAmount.replace(',', '.').toDoubleOrNull() ?: 0.0
    val discountPercent = selectedDiscount ?: 0
    val discountAmount = amountValue * discountPercent / 100.0
    val tipsAmount = amountValue * (tipsPercent / 100.0)
    val totalAmount = (amountValue - discountAmount) + tipsAmount

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
        Spacer(modifier = Modifier.height(14.dp))
        TipsSlider(
            tipsPercent = tipsPercent,
            onTipsPercentChange = { tipsPercent = it }
        )
        Spacer(modifier = Modifier.height(20.dp))
        DiscountRadioGroup(selectedDiscount = selectedDiscount)
        Spacer(modifier = Modifier.height(10.dp))
        SummaryPanel(
            tipsAmount = tipsAmount,
            totalAmount = totalAmount
        )
    }
}

private fun discountForDishCount(dishCount: Int?): Int? = when {
    dishCount == null || dishCount <= 0 -> null
    dishCount <= 2 -> 3
    dishCount <= 5 -> 5
    dishCount <= 10 -> 7
    else -> 10
}

@Composable
private fun SummaryPanel(
    tipsAmount: Double,
    totalAmount: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F4FA), shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Сумма чаевых:",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = String.format(Locale.US, "%.2f", tipsAmount),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Итоговая сумма:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = String.format(Locale.US, "%.2f", totalAmount),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun DiscountRadioGroup(selectedDiscount: Int?) {
    val discounts = listOf(3, 5, 7, 10)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "Скидка:",
            modifier = Modifier
                .width(96.dp)
                .padding(top = 10.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            discounts.forEach { discount ->
                Column(
                    modifier = Modifier.width(48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RadioButton(
                        selected = selectedDiscount == discount,
                        onClick = null,
                        enabled = false,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF7654C9),
                            unselectedColor = Color(0xFF444444),
                            disabledSelectedColor = Color(0xFF7654C9),
                            disabledUnselectedColor = Color(0xFF444444)
                        )
                    )
                    Text(
                        text = "$discount%",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun TipsSlider(tipsPercent: Float, onTipsPercentChange: (Float) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = "Чаевые:", style = MaterialTheme.typography.bodyLarge)
        Slider(
            value = tipsPercent,
            onValueChange = onTipsPercentChange,
            modifier = Modifier.fillMaxWidth(),
            valueRange = 0f..25f,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF526EA8),
                activeTrackColor = Color(0xFF526EA8),
                inactiveTrackColor = Color(0xFFE1E6F6)
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0", style = MaterialTheme.typography.headlineSmall)
            Text(text = "25", style = MaterialTheme.typography.headlineSmall)
        }
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