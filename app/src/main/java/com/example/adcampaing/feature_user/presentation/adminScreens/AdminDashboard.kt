package com.example.adcampaing.feature_user.presentation.adminScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.adcampaing.R
import com.example.adcampaing.navigations.BottomNavAdminMenu
import com.example.adcampaing.navigations.Screens
import com.example.adcampaing.ui.theme.ADCampaingTheme


@Composable
fun Dashboard(
    navController: NavController
) {
    ADCampaingTheme {
        Scaffold(
            bottomBar = {
                BottomNavAdminMenu(navController)
            },
            topBar = { TopBar() },
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .background(Color(0XFFEAFBFF))
                    .padding(
                        bottom = paddingValues.calculateBottomPadding(),
                        top = paddingValues.calculateTopPadding()
                    )
            ) {
                item {
                    TopCard()
                }
                item {
                    TitleInfo()
                }
                item {
                    Spacer(modifier = Modifier.height(6.dp))
                }
                item {
                    BodyMain()
                }
            }

        }
    }
}

@Composable
fun TopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0XFFEAFBFF))
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "Hello Admin",
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp),
            color = Color.Black,
            style = MaterialTheme.typography.h5
        )

    }
}

@Composable
fun TopCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFE27097))
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = "You Have Posted",
                color = Color.White,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "10  Ads",
                color = Color.White,
                style = MaterialTheme.typography.body1
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.Blue)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_accessibility_24),
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )

        }
    }

}


@Composable
fun TitleInfo() {
    Row() {
        Text(
            text = "Summary Of AD Coverage",
            modifier = Modifier
                .padding(start = 25.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun BodyMain() {
    Column(Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Card(
                Modifier
                    .height(150.dp)
                    .width(150.dp), elevation = 10.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(Color(0xFFBEF3BA))
                        .padding(start = 35.dp)

                ) {
                    Text(text = "You Have :")
                    Spacer(modifier = Modifier.height(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_visibility_24),
                        modifier = Modifier.padding(start = 30.dp),
                        contentDescription = "Image1"
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "41 Views")
                }

            }
            Card(
                Modifier
                    .height(150.dp)
                    .width(150.dp), elevation = 10.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(Color(0xFF88E5F7))
                        .padding(start = 35.dp)

                ) {
                    Text(text = "You Have :")
                    Spacer(modifier = Modifier.height(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.hearts),
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .size(20.dp),
                        contentDescription = "Image1"
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "81 Likes")
                }

            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Card(
                Modifier
                    .height(150.dp)
                    .width(150.dp), elevation = 10.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(Color(0xFFBEF3BA))
                        .padding(start = 35.dp)

                ) {
                    Text(text = "You Have :")
                    Spacer(modifier = Modifier.height(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_share_24),
                        modifier = Modifier.padding(start = 30.dp),
                        contentDescription = "Image1"
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "18 Shares")
                }

            }
            Card(
                Modifier
                    .height(150.dp)
                    .width(150.dp), elevation = 10.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(Color(0xFF88E5F7))
                        .padding(start = 35.dp)

                ) {
                    Text(text = "You Have :")
                    Spacer(modifier = Modifier.height(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.thumbsdown),
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .size(20.dp),
                        contentDescription = "Image1"
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "0 Dislikes")
                }

            }
        }

        Card(
            Modifier
                .height(150.dp)
                .padding(start = 30.dp, top = 15.dp)
                .width(150.dp),
            elevation = 10.dp
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(Color(0xFF88E5F7))
                    .padding(start = 35.dp)

            ) {
                Text(text = "People Reached :")
                Spacer(modifier = Modifier.height(2.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_groups_24),
                    modifier = Modifier.padding(start = 30.dp),
                    contentDescription = "Image1"
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "98 People")
            }

        }
    }
}



