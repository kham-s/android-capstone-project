package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController

@Composable
fun Profile(viewModel: OnboardingViewModel, sharedPreferences: SharedPreferences, navController: NavHostController) {

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Upper Panel Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(20.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF495E57)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.profile_information),
                style = MaterialTheme.typography.h1,
                color = Color.White,
                fontSize = 24.sp,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_personal_information),
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp, bottom = 30.dp)
                )
                OutlinedTextField(
                    value = viewModel.firstName,
                    enabled = false,
                    onValueChange = { viewModel.firstName = it },
                    label = { Text(text = "First name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Gray,
                        unfocusedBorderColor = Gray
                    )
                )
                OutlinedTextField(
                    value = viewModel.lastName,
                    onValueChange = { viewModel.lastName = it },
                    enabled = false,
                    label = { Text(text = "Last name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    enabled = false,
                    label = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        sharedPreferences.edit(commit = true) {
                            putString("firstName", "")
                            putString("lastName", "")
                            putString("email", "")
                        }
                        viewModel.firstName = ""
                        viewModel.lastName = ""
                        viewModel.email = ""
                        navController.navigate(OnboardingScreen.route)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color(0xFFF4CE14)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.profile_logout),
                        color = Color.Black
                    )
                }
            }
        }
    }
}