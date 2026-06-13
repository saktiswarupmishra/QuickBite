package com.example.quickbitelocal.ui.screens.address

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quickbitelocal.domain.model.Address

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    onBackClick: () -> Unit,
    viewModel: AddressViewModel = hiltViewModel()
) {
    val addresses by viewModel.addresses.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Addresses") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Address")
            }
        }
    ) { padding ->
        if (addresses.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No addresses saved")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(addresses) { address ->
                    AddressItem(
                        address = address,
                        onDelete = { viewModel.deleteAddress(address) },
                        onSetDefault = { viewModel.setDefaultAddress(address.id) }
                    )
                }
            }
        }

        if (showDialog) {
            AddAddressDialog(
                onDismiss = { showDialog = false },
                onConfirm = { label, fullAddress ->
                    viewModel.addAddress(label, fullAddress)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddressItem(
    address: Address,
    onDelete: () -> Unit,
    onSetDefault: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onSetDefault
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = address.label, style = MaterialTheme.typography.titleMedium)
                Text(text = address.fullAddress, style = MaterialTheme.typography.bodySmall)
                if (address.isDefault) {
                    Text(
                        text = "Default",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun AddAddressDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var label by remember { mutableStateOf("") }
    var fullAddress by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Address") },
        text = {
            Column {
                TextField(value = label, onValueChange = { label = it }, label = { Text("Label (e.g. Home)") })
                Spacer(modifier = Modifier.height(8.dp))
                TextField(value = fullAddress, onValueChange = { fullAddress = it }, label = { Text("Full Address") })
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(label, fullAddress) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
