package com.example.quickbitelocal.ui.screens.payment

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onBackClick: () -> Unit,
    onPaymentSuccess: (String) -> Unit,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.orderSuccessId) {
        uiState.orderSuccessId?.let { id ->
            onPaymentSuccess(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Payment") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Total Payable: $${String.format("%.2f", uiState.totalAmount)}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(24.dp))

            PaymentMethodItem("UPI", uiState.selectedMethod == "UPI") { viewModel.selectMethod("UPI") }
            PaymentMethodItem("Credit/Debit Card", uiState.selectedMethod == "Card") { viewModel.selectMethod("Card") }
            PaymentMethodItem("Net Banking", uiState.selectedMethod == "NetBanking") { viewModel.selectMethod("NetBanking") }
            PaymentMethodItem("Cash on Delivery", uiState.selectedMethod == "COD") { viewModel.selectMethod("COD") }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.processPayment() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isProcessing
            ) {
                if (uiState.isProcessing) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                } else {
                    Text("Pay Now")
                }
            }
        }
    }
}

@Composable
fun PaymentMethodItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = isSelected, onClick = onClick)
            Spacer(modifier = Modifier.width(16.dp))
            Text(label, style = MaterialTheme.typography.titleMedium)
        }
    }
}
