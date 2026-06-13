package com.example.quickbitelocal.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.quickbitelocal.domain.model.MenuItem
import com.example.quickbitelocal.domain.model.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailsScreen(
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    viewModel: RestaurantDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.restaurant?.name ?: "Loading...") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    uiState.restaurant?.let { restaurant ->
                        AsyncImage(
                            model = restaurant.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = restaurant.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                            Text(text = restaurant.cuisine, style = MaterialTheme.typography.bodyLarge)
                            Text(text = "⭐ ${restaurant.rating} • ${restaurant.deliveryTime}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                uiState.reviewSummary?.let { summary ->
                    item {
                        Card(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = summary, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                items(uiState.menuItems) { menuItem ->
                    MenuItemRow(
                        menuItem = menuItem,
                        onAddToCart = { viewModel.addToCart(menuItem) }
                    )
                }

                item {
                    Text(
                        text = "Recent Reviews",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                items(uiState.reviews) { review ->
                    ReviewRow(review = review)
                }
            }
        }
    }
}

@Composable
fun ReviewRow(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = review.userName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(text = "⭐ ${review.rating}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.comment, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun MenuItemRow(menuItem: MenuItem, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = menuItem.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = menuItem.name, style = MaterialTheme.typography.titleMedium)
                Text(text = menuItem.description, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                Text(text = "$${menuItem.price}", style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = onAddToCart, contentPadding = PaddingValues(horizontal = 12.dp)) {
                Text("Add")
            }
        }
    }
}
