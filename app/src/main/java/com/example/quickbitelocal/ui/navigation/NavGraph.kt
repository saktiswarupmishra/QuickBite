package com.example.quickbitelocal.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quickbitelocal.ui.screens.address.AddressScreen
import com.example.quickbitelocal.ui.screens.admin.AdminDashboardScreen
import com.example.quickbitelocal.ui.screens.auth.LoginScreen
import com.example.quickbitelocal.ui.screens.cart.CartScreen
import com.example.quickbitelocal.ui.screens.chat.ChatScreen
import com.example.quickbitelocal.ui.screens.checkout.CheckoutScreen
import com.example.quickbitelocal.ui.screens.checkout.OrderSuccessScreen
import com.example.quickbitelocal.ui.screens.delivery.DeliveryDashboardScreen
import com.example.quickbitelocal.ui.screens.details.RestaurantDetailsScreen
import com.example.quickbitelocal.ui.screens.home.HomeScreen
import com.example.quickbitelocal.ui.screens.orders.OrderHistoryScreen
import com.example.quickbitelocal.ui.screens.partner.PartnerDashboardScreen
import com.example.quickbitelocal.ui.screens.payment.PaymentScreen
import com.example.quickbitelocal.ui.screens.profile.ProfileScreen
import com.example.quickbitelocal.ui.screens.splash.SplashScreen
import com.example.quickbitelocal.ui.screens.tracking.TrackingScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object RestaurantDetails : Screen("restaurant_details/{restaurantId}") {
        fun createRoute(restaurantId: String) = "restaurant_details/$restaurantId"
    }
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object Payment : Screen("payment/{amount}") {
        fun createRoute(amount: Double) = "payment/$amount"
    }
    object OrderSuccess : Screen("order_success/{orderId}") {
        fun createRoute(orderId: String) = "order_success/$orderId"
    }
    object Tracking : Screen("tracking/{orderId}") {
        fun createRoute(orderId: String) = "tracking/$orderId"
    }
    object OrderHistory : Screen("order_history")
    object Address : Screen("address")
    object Profile : Screen("profile")
    object Chat : Screen("chat")
    object PartnerDashboard : Screen("partner_dashboard")
    object DeliveryDashboard : Screen("delivery_dashboard")
    object AdminDashboard : Screen("admin_dashboard")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onSplashFinished = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onRestaurantClick = { restaurantId ->
                    navController.navigate(Screen.RestaurantDetails.createRoute(restaurantId))
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }
        composable(
            route = Screen.RestaurantDetails.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
        ) {
            RestaurantDetailsScreen(
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(Screen.Cart.route) }
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(
                onBackClick = { navController.popBackStack() },
                onCheckoutClick = {
                    navController.navigate(Screen.Checkout.route)
                }
            )
        }
        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onBackClick = { navController.popBackStack() },
                onManageAddressesClick = { navController.navigate(Screen.Address.route) },
                onProceedToPayment = { amount ->
                    navController.navigate(Screen.Payment.createRoute(amount))
                }
            )
        }
        composable(
            route = Screen.Payment.route,
            arguments = listOf(navArgument("amount") { type = NavType.StringType })
        ) {
            PaymentScreen(
                onBackClick = { navController.popBackStack() },
                onPaymentSuccess = { orderId ->
                    navController.navigate(Screen.OrderSuccess.createRoute(orderId)) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(
            route = Screen.OrderSuccess.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { entry ->
            val orderId = entry.arguments?.getString("orderId") ?: ""
            OrderSuccessScreen(
                orderId = orderId,
                onGoToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onTrackOrder = {
                    navController.navigate(Screen.Tracking.createRoute(orderId))
                }
            )
        }
        composable(
            route = Screen.Tracking.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) {
            TrackingScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Screen.OrderHistory.route) {
            OrderHistoryScreen(
                onBackClick = { navController.popBackStack() },
                onManageAddressesClick = { navController.navigate(Screen.Address.route) }
            )
        }
        composable(Screen.Address.route) {
            AddressScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Chat.route) {
            ChatScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onOrderHistoryClick = { navController.navigate(Screen.OrderHistory.route) },
                onAddressesClick = { navController.navigate(Screen.Address.route) },
                onChatClick = { navController.navigate(Screen.Chat.route) },
                onPartnerDashboardClick = { navController.navigate(Screen.PartnerDashboard.route) },
                onDeliveryDashboardClick = { navController.navigate(Screen.DeliveryDashboard.route) },
                onAdminDashboardClick = { navController.navigate(Screen.AdminDashboard.route) },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.PartnerDashboard.route) {
            PartnerDashboardScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Screen.DeliveryDashboard.route) {
            DeliveryDashboardScreen(onBackClick = { navController.popBackStack() })
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(onBackClick = { navController.popBackStack() })
        }
    }
}
