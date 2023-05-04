package com.example.adcampaing.feature_auth.presentation.sharedScreens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.adcampaing.R
import com.example.adcampaing.feature_auth.presentation.viewModel.AuthViewModel
import com.example.adcampaing.navigations.Screens
import com.example.adcampaing.core.utils.Resource

@Composable
fun Register(
    viewModel: AuthViewModel,
    navController: NavController,
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateTopPadding())
        ) {
            item {
                RegItem(viewModel, navController)
            }
        }

    }
}

@Composable
fun RegItem(
    viewModel: AuthViewModel,
    navController: NavController,
) {

    val signUpFlow = viewModel.signUpFlow.collectAsState()
    Column(
        modifier = Modifier
            .background(Color(0XFFEAFBFF))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .padding(horizontal = 12.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Text(
                text = "Welcome Onboard",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
        }

        Image(
            painter = painterResource(
                id = R.drawable.re
            ),
            contentDescription = "Logo",
            modifier = Modifier
                .size(180.dp)
                .padding(vertical = 12.dp)
        )

        Text(
            text = "Let's Help you set Up your Account",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif
        )


        var inputEmail by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var passwordInput by remember { mutableStateOf("") }
        var confirmPasswordInput by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        val isEmailValid by remember {
            derivedStateOf {
                Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()
            }
        }
        val isNameValid by remember {
            derivedStateOf {
                name.length > 1
            }
        }
        val isPasswordValid by remember {
            derivedStateOf {
                passwordInput.length > 7
            }
        }

        val color = Color.Black.copy(alpha = 0.78F)

        OutlinedTextField(
            value = name,
            onValueChange = { newValue -> name = newValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            label = {
                Text(text = "Name")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "UserName")
            },
            singleLine = true,
            isError = !isNameValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = color,
                cursorColor = color,
                leadingIconColor = color,
                trailingIconColor = color,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = color.copy(alpha = 0.5F),
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = color
            )
        )

        OutlinedTextField(
            value = inputEmail,
            onValueChange = { newValue ->
                inputEmail = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            label = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "email_icon")
            },
            singleLine = true,
            isError = !isEmailValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = color,
                cursorColor = color,
                leadingIconColor = color,
                trailingIconColor = color,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = color.copy(alpha = 0.5F),
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = color
            )
        )
        OutlinedTextField(
            value = passwordInput,
            onValueChange = { newValue ->
                passwordInput = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            label = {
                Text(text = "PassWord")
            },
            isError = !isPasswordValid,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Icon")
            },
            trailingIcon = {
                val image = if (isPasswordVisible)
                    R.drawable.ic_baseline_visibility_off_24 else R.drawable.ic_baseline_visibility_24
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(painter = painterResource(id = image), contentDescription = "Toggle Icon")
                }
            },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = color,
                cursorColor = color,
                leadingIconColor = color,
                trailingIconColor = color,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = color.copy(alpha = 0.5F),
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = color
            )
        )
        OutlinedTextField(
            value = confirmPasswordInput,
            onValueChange = { newValue ->
                confirmPasswordInput = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            label = {
                Text(text = "Confirm Password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Icon")
            },
            isError = !isPasswordValid,
            trailingIcon = {
                val image = if (isPasswordVisible)
                    R.drawable.ic_baseline_visibility_off_24 else R.drawable.ic_baseline_visibility_24
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(painter = painterResource(id = image), contentDescription = "Toggle Icon")
                }
            },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = color,
                cursorColor = color,
                leadingIconColor = color,
                trailingIconColor = color,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = color.copy(alpha = 0.5F),
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = color
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                viewModel.signup(inputEmail, passwordInput, name)
//                startForResult.launch(googleSignInClient?.signInIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text(text = "SignUp ", modifier = Modifier.padding(6.dp))
        }
        signUpFlow.value.let {
            when (it) {
                is Resource.Error -> {
                    val context = LocalContext.current
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(14.dp)
                            .border(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.inverseSurface,
                                width = 4.dp
                            )
                    )
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(Screens.LogIn.route) {
                            popUpTo(Screens.LogIn.route) {
                                inclusive = true
                            }
                        }
                    }
                }

                else -> {}
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 30.dp, end = 30.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
//                startForResult.launch(googleSignInClient?.signInIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.gmail),
                contentDescription = ""
            )
            Text(text = "Sign Up with Google", modifier = Modifier.padding(6.dp))
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { navController.navigate(Screens.LogIn.route) }) {
                Text(text = "Already Have an Account? SignIn")
            }
        }

    }
}