package com.example.quickbitelocal.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun CheckoutScreen(
    onBackClick: () -> Unit,
    onManageAddressesClick: () -> Unit,
    onProceedToPayment: (Double) -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddressSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
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
            Text("Delivery Address", style = MaterialTheme.typography.titleMedium)
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                onClick = { if (uiState.addresses.isNotEmpty()) showAddressSheet = true else onManageAddressesClick() }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        if (uiState.selectedAddress != null) {
                            Text(uiState.selectedAddress!!.label, style = MaterialTheme.typography.titleSmall)
                            Text(uiState.selectedAddress!!.fullAddress, style = MaterialTheme.typography.bodySmall)
                        } else {
                            Text("No address selected", style = MaterialTheme.typography.bodyMedium)
                            Text("Tap to add address", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    Text("Change", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Order Summary", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.cartItems) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${item.name} x ${item.quantity}")
                        Text("$${String.format("%.2f", item.price * item.quantity)}")
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Amount", style = MaterialTheme.typography.titleLarge)
                Text("$${String.format("%.2f", uiState.totalAmount)}", style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onProceedToPayment(uiState.totalAmount) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.cartItems.isNotEmpty() && uiState.selectedAddress != null
            ) {
                Text("Proceed to Payment")
            }
        }

        if (showAddressSheet) {
            AlertDialog(
                onDismissRequest = { showAddressSheet = false },
                title = { Text("Select Address") },
                text = {
                    LazyColumn {
                        items(uiState.addresses) { address ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = address.id == uiState.selectedAddress?.id,
                                    onClick = { viewModel.selectAddress(address); showAddressSheet = false }
                                )
                                Column(modifier = Modifier.padding(start = 8.dp)) {
                                    Text(address.label, style = MaterialTheme.typography.titleSmall)
                                    Text(address.fullAddress, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAddressSheet = false; onManageAddressesClick() }) {
                        Text("Manage Addresses")
                    }
                }
            )
        }
    }
}
