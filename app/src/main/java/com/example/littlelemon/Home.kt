package com.example.littlelemon

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun Home(navController: NavHostController, items: List<MenuItemRoom>) {

    Column() {
            MenuItemsList(navController, items)
        }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItemsList(navController: NavHostController, items: List<MenuItemRoom>) {
    var menuItems = items
    var searchString by remember { mutableStateOf(TextFieldValue()) }
    var category by remember { mutableStateOf<String>("") }
    if (searchString.text != "") {
        menuItems = menuItems.filter { it.title.contains(searchString.text, ignoreCase = true)  }
    }
    if (category != "") {
        menuItems = menuItems.filter { it.category == category  }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        item {

            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(200.dp)
                        .aspectRatio(16f / 9f)
                        .align(Alignment.Center)

                )
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(75.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { navController.navigate(ProfileScreen.route) }
                )
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFF495E57))
                    .padding(10.dp)
            ) {

                Column {
                    Text(
                        text = stringResource(id = R.string.home_heading1),
                        style = MaterialTheme.typography.h1,
                        color = Color.Yellow,
                        lineHeight = 5.sp,
                    )
                    Text(
                        text = stringResource(id = R.string.home_heading2),
                        style = MaterialTheme.typography.h2,
                        color = Color.White,
                        lineHeight = 5.sp
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.fillMaxWidth(0.6f)) {
                            Text(
                                text = stringResource(id = R.string.home_description),
                                style = MaterialTheme.typography.body1,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                            )
                        }
                        Box {
                            Image(
                                painter = painterResource(id = R.drawable.heroimage),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(bottom = 10.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentDescription = "hero",
                            )
                        }
                    }




                    TextField(
                        value = searchString,
                        onValueChange = { searchString = it },
                        label = { Text(stringResource(id = R.string.home_search)) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),

                        )
                }
            }
            Column() {
                Text(
                    text = stringResource(id = R.string.home_order),
                    style = MaterialTheme.typography.h2,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { category = "starters" },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                0xFFAFAFAF
                            )
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Text("Starters")
                    }
                    Button(
                        onClick = { category = "mains" },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                0xFFAFAFAF
                            )
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Text("Mains")
                    }
                    Button(
                        onClick = { category = "desserts" },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                0xFFAFAFAF
                            )
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Text("Desserts")
                    }
                    Button(
                        onClick = { category = "drinks" },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                0xFFAFAFAF
                            )
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Text("Drinks")
                    }
                }

                Divider(
                    color = Color.Gray,
                    thickness = 2.dp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
        items(
            items = menuItems,
            itemContent = { menuItem ->
                Column(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                ) {
                    Text(menuItem.title,
                        style = MaterialTheme.typography.h2,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Row {
                        Column (modifier = Modifier.fillMaxWidth(0.7f)){
                            Text(text = menuItem.description,modifier = Modifier.padding(bottom = 10.dp))
                            Text( "$" + menuItem.price.toString(),
                                style = MaterialTheme.typography.h2,
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                        Column() {
                            GlideImage(
                                model = menuItem.image,
                                contentDescription = null
                            )
                        }
                    }
                    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                }
            }
        )
    }
}
