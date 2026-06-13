package com.example.quickbitelocal.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OrderSuccessScreen(
    orderId: String,
    onGoToHome: () -> Unit,
    onTrackOrder: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = Color.Green
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Order Placed Successfully!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Order ID: $orderId", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onTrackOrder, modifier = Modifier.fillMaxWidth()) {
            Text("Track Order")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onGoToHome, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Home")
        }
    }
}
