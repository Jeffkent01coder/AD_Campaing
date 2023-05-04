package com.example.adcampaing.feature_user.presentation.adminScreens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.adcampaing.core.shared_composables.ChooseProfilePicFromGallery
import com.example.adcampaing.feature_post.domain.model.Post
import com.example.adcampaing.feature_post.presentation.PostViewModel
import com.example.adcampaing.navigations.BottomNavAdminMenu
import com.example.adcampaing.navigations.Screens
import com.example.adcampaing.ui.theme.ADCampaingTheme

@Composable
fun AdPost(
    navController: NavController,
    postViewModel: PostViewModel
) {
    ADCampaingTheme {
        Scaffold(
            bottomBar = { BottomNavAdminMenu(navController = navController) },
            topBar = { ADTopBar(navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .background(Color(0XFFEAFBFF))
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                BodyAd(navController, postViewModel)
            }

        }
    }
}

@Composable
fun ADTopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .padding(horizontal = 12.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = { navController.popBackStack() }) {

        }
        Icon(
            modifier = Modifier.clickable {
                navController.navigateUp()
            },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back arrow",
            tint = Color.Black
        )

        Text(
            text = "Create An AD",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace
        )

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BodyAd(
    navController: NavController,
    postViewModel: PostViewModel
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val addTypeListItems = arrayOf("Video", "Text", "Pictorial")

    val addCategoryListItems = arrayOf("Kids", "Teens", "Youths", "Adults")
    val targetAudienceListItems = arrayOf("Political", "Informative", "Awareness")
    val contextForToast = LocalContext.current.applicationContext

    var adtypExpanded by remember {
        mutableStateOf(false)
    }
    var adCatExpanded by remember {
        mutableStateOf(false)
    }
    var targetExpanded by remember {
        mutableStateOf(false)
    }

    var postImage by remember {
        mutableStateOf("")
    }

    var adTypeSelectedItem by remember {
        mutableStateOf(addTypeListItems[0])
    }
    var adCategorySelectedItem by remember {
        mutableStateOf(addTypeListItems[0])
    }
    var targetAudienceSelectedItem by remember {
        mutableStateOf(addTypeListItems[0])
    }
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Title : ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    value = title,
                    onValueChange = { newText ->
                        title = newText
                    },
                    singleLine = true,
                    placeholder = { Text(text = "Ad title") }
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Ad Type : ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                ExposedDropdownMenuBox(
                    expanded = adtypExpanded,
                    onExpandedChange = {
                        adtypExpanded = !adtypExpanded
                    }
                ) {
                    TextField(
                        value = adTypeSelectedItem,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = "Ad type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = adtypExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = adtypExpanded,
                        onDismissRequest = { adtypExpanded = false }
                    ) {
                        addTypeListItems.forEach { selectedOption ->
                            DropdownMenuItem(onClick = {
                                adTypeSelectedItem = selectedOption
                                Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT)
                                    .show()
                                adtypExpanded = false
                            }) {
                                Text(text = selectedOption)
                            }
                        }
                    }
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Ad Category : ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                ExposedDropdownMenuBox(
                    expanded = adCatExpanded,
                    onExpandedChange = {
                        adCatExpanded = !adCatExpanded
                    }
                ) {
                    TextField(
                        value = adCategorySelectedItem,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = "Category") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = adCatExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = adCatExpanded,
                        onDismissRequest = { adCatExpanded = false }
                    ) {
                        addCategoryListItems.forEach { selectedOption ->
                            DropdownMenuItem(onClick = {
                                adCategorySelectedItem = selectedOption
                                Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT)
                                    .show()
                                adCatExpanded = false
                            }) {
                                Text(text = selectedOption)
                            }
                        }
                    }
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Target Audience : ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                ExposedDropdownMenuBox(
                    expanded = targetExpanded,
                    onExpandedChange = {
                        targetExpanded = !targetExpanded
                    }
                ) {
                    TextField(
                        value = targetAudienceSelectedItem,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text = "Audience") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = targetExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = targetExpanded,
                        onDismissRequest = { targetExpanded = false }
                    ) {
                        targetAudienceListItems.forEach { selectedOption ->
                            DropdownMenuItem(onClick = {
                                targetAudienceSelectedItem = selectedOption
                                Toast.makeText(contextForToast, selectedOption, Toast.LENGTH_SHORT)
                                    .show()
                                targetExpanded = false
                            }) {
                                Text(text = selectedOption)
                            }
                        }
                    }
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Description : ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    value = description,
                    onValueChange = { newText ->
                        description = newText
                    },
                    placeholder = { Text(text = "Description") }
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Image : ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFFFFFFFF)
                )
//                UploadImage(navController)
                ChooseProfilePicFromGallery(postImage) {
                    if (it != null) {
                        postViewModel.uploadPictureToFirebase(it)
                    }
                }
            }
        }
        item {
            Button(
                onClick = {
                    if (description != "") {
                        postViewModel.createPostToFirebase(
                            Post(postDescription = description)
                        )
                    }
                    navController.popBackStack()
                    navController.navigate(Screens.DashBoard.route)
                    Toast.makeText(contextForToast, "post sent", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "Post Ad")
            }
        }

    }

}