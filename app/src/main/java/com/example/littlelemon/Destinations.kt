package com.example.littlelemon

import android.content.SharedPreferences

interface Destinations {
    val route: String
}

object HomeScreen : Destinations {
    override val route = "HomeScreen"
}

object ProfileScreen : Destinations {
    override val route = "ProfileScreen"
}

object OnboardingScreen : Destinations {
    override val route = "OnboardingScreen"
}