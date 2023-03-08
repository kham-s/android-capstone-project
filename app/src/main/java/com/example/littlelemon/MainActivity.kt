package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")
    var email = mutableStateOf("")

    val sharedPreferences by lazy { getSharedPreferences("LittleLemon", Context.MODE_PRIVATE) }
    val sharedPreferencesListener =
        OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "firstName") {
                firstName = mutableStateOf(sharedPreferences.getString(key, "")!!)
            }
            if (key == "lastName") {
                lastName = mutableStateOf(sharedPreferences.getString(key, "")!!)
            }
            if (key == "email") {
                email = mutableStateOf(sharedPreferences.getString(key, "")!!)
            }
        }
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "menu.db"
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = OnboardingViewModel()
        viewModel.firstName = sharedPreferences.getString("firstName", "")!!
        viewModel.lastName = sharedPreferences.getString("lastName", "")!!
        viewModel.email = sharedPreferences.getString("email", "")!!

        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
        setContent {
            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val databaseMenuItems by database.menuItemDao().getAll()
                        .observeAsState(emptyList())
                    val startDest : String
                    if (sharedPreferences.getString("firstName", "") != "" && sharedPreferences.getString("lastName", "") != "" && sharedPreferences.getString("lastName", "") != "") {
                        startDest = HomeScreen.route
                    } else {
                        startDest = OnboardingScreen.route
                    }
                    Navigation(viewModel, sharedPreferences, databaseMenuItems, startDest)
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                // add code here
                val menuItems = fetchMenu()
                saveMenuToDatabase(menuItems)
            }
        }
    }
    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response =
            httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                .body<MenuNetwork>()

        return response.menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}