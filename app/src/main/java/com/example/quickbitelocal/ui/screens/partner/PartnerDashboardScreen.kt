package com.example.quickbitelocal.ui.screens.partner

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerDashboardScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Partner Dashboard") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome, Restaurant Partner!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Here you will manage your menu, track daily earnings, and accept orders.")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Manage Menu")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /* TODO */ }, modifier = Modifier.fillMaxWidth()) {
                Text("View Sales Report")
            }
        }
    }
}
