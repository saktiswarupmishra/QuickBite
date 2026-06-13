package com.example.quickbitelocal.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.quickbitelocal.domain.model.Restaurant
import com.example.quickbitelocal.ui.components.QuickBiteLogo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onRestaurantClick: (String) -> Unit,
    onCartClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val restaurants by viewModel.restaurants.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val aiRecommendation by viewModel.aiRecommendation.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val categories = listOf("All", "Burgers", "Pizza", "Healthy", "Italian", "Desserts")
    val offers = listOf(
        "Flat 50% OFF on your first order!",
        "Free Delivery on orders above $20",
        "BOGO: Buy 1 Get 1 on select Pizzas"
    )
    val popularFoods = listOf(
        "Whopper" to "https://images.unsplash.com/photo-1550547660-d9450f859349?w=200&q=80",
        "Pepperoni Pizza" to "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=200&q=80",
        "Veggie Sub" to "https://images.unsplash.com/photo-1534353473418-4cfa6c56fd38?w=200&q=80",
        "Chocolate Cake" to "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=200&q=80"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { QuickBiteLogo(size = 32.dp) },
                actions = {
                    IconButton(onClick = onProfileClick) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    IconButton(onClick = onCartClick) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search restaurants or cuisines") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = MaterialTheme.shapes.medium
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(offers) { offer ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                            modifier = Modifier.width(280.dp)
                        ) {
                            Text(
                                text = offer,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { viewModel.onCategorySelect(category) },
                            label = { Text(category) }
                        )
                    }
                }
            }

            aiRecommendation?.let { recommendation ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "AI Recommendation for You",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = recommendation,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            item {
                Text(text = "Popular Foods", style = MaterialTheme.typography.titleMedium)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(popularFoods) { (name, url) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            AsyncImage(
                                model = url,
                                contentDescription = name,
                                modifier = Modifier.size(80.dp).background(Color.LightGray, CircleShape).padding(4.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(text = name, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Nearby Restaurants",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            items(restaurants) { restaurant ->
                RestaurantItem(
                    restaurant = restaurant,
                    onClick = { onRestaurantClick(restaurant.id) },
                    onToggleFavorite = { viewModel.toggleFavorite(restaurant) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantItem(
    restaurant: Restaurant, 
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = restaurant.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (restaurant.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (restaurant.isFavorite) Color.Red else Color.Black
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = restaurant.name, style = MaterialTheme.typography.titleLarge)
                Text(text = restaurant.cuisine, style = MaterialTheme.typography.bodyMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "⭐ ${restaurant.rating}", style = MaterialTheme.typography.bodySmall)
                    Text(text = restaurant.deliveryTime, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
