package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(viewModel: OnboardingViewModel, sharedPreferences: SharedPreferences, items: List<MenuItemRoom>, startDest: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDest) {
        composable(HomeScreen.route) {
            Home(navController, items)
        }
        composable(OnboardingScreen.route) {
            OnBoarding(viewModel, sharedPreferences, navController)
        }
        composable(ProfileScreen.route) {
            Profile(viewModel, sharedPreferences, navController)
        }
    }
}