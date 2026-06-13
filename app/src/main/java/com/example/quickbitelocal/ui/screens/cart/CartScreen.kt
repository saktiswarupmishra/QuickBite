package com.example.quickbitelocal.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quickbitelocal.domain.model.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Cart") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (uiState.items.isNotEmpty()) {
                Surface(shadowElevation = 8.dp) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        var couponCode by remember { mutableStateOf("") }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                value = couponCode,
                                onValueChange = { couponCode = it },
                                label = { Text("Coupon Code") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            TextButton(onClick = { viewModel.applyCoupon(couponCode) }) {
                                Text("Apply")
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        PriceRow("Subtotal", uiState.subtotal)
                        PriceRow("Delivery Fee", uiState.deliveryFee)
                        PriceRow("Tax (5%)", uiState.tax)
                        
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Payable:", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text("$${String.format("%.2f", uiState.totalAmount)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onCheckoutClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Checkout")
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (uiState.items.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Your cart is empty")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.items) { item ->
                    CartItemRow(
                        item = item,
                        onIncrease = { viewModel.updateQuantity(item.menuItemId, item.quantity + 1) },
                        onDecrease = { viewModel.updateQuantity(item.menuItemId, item.quantity - 1) },
                        onRemove = { viewModel.removeFromCart(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun PriceRow(label: String, amount: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text("$${String.format("%.2f", amount)}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text("$${item.price} x ${item.quantity}", style = MaterialTheme.typography.bodySmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease, enabled = item.quantity > 1) {
                    Text("-")
                }
                Text("${item.quantity}")
                IconButton(onClick = onIncrease) {
                    Text("+")
                }
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }
    }
}
