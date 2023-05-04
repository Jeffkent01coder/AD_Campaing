package com.example.adcampaing.feature_user.presentation.userScreens

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.adcampaing.R
import com.example.adcampaing.core.shared_composables.DummyData
import com.example.adcampaing.feature_post.presentation.PostViewModel
import com.example.adcampaing.navigations.BottomNavUserMenu

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserHome(
    navController: NavController,
    postViewModel: PostViewModel
) {
    Scaffold(
        bottomBar = {
            BottomNavUserMenu(navController)
        },
        topBar = { TopNav() }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(Color(0XFFEAFBFF))
                .padding(it)
                .fillMaxHeight()
        ) {

            stickyHeader {
                Box(
                    modifier = Modifier
                        .background(Color(0XFFEAFBFF))
                        .fillMaxWidth()
                ) {
                    Search()
                }
            }
            items(DummyData.dataList) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    DummyData.dataList.forEach { data ->
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clickable { },
                            elevation = 10.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(4.dp)
                            ) {

                                Image(
                                    painter = painterResource(id = data.image),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        var number by remember { mutableStateOf(0) }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { number++ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.hearts),
                                    contentDescription = "Like",
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(text = "$number")


                            IconButton(onClick = { number++ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_visibility_24),
                                    contentDescription = "view",
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(text = "$number")


                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Hello there\n Here is how to grow your business with Google Ads\n https://ads.google.com/home/."
                                )
                                type = "text/plain"
                            }

                            val shareIntent = Intent.createChooser(sendIntent, null)

                            val context = LocalContext.current
                            IconButton(onClick = {
                                startActivity(context, shareIntent, null)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_share_24),
                                    contentDescription = "share",
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                        }
                    }

                }

            }

        }

    }
}

@Composable
fun TopNav() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0XFFEAFBFF))
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = "Hello Ndeti",
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp),
            color = Color.Black,
            style = MaterialTheme.typography.h5
        )

    }
}


@Composable
fun Search(
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.padding(start = 30.dp, end = 30.dp)

    ) {
        TextField(
            value = text,
            placeholder = {
                Text(text = "Search")
            },
            onValueChange = {
                text = it
                onSearch(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(100))
                .fillMaxWidth()
                .background(color = Color(0xFFC8C9CC))

        )
    }
}

@Composable
fun Body(
) {

}

//@Composable
//private fun AdImage(ad: AdData){
//    Image(painter = painterResource(id = ad.image), contentDescription = null)
//    
//}

