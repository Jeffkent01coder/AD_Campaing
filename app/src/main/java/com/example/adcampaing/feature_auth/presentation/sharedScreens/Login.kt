package com.example.adcampaing.feature_auth.presentation.sharedScreens


import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.adcampaing.R
import com.example.adcampaing.feature_auth.presentation.viewModel.AuthViewModel
import com.example.adcampaing.navigations.Screens
import com.example.adcampaing.ui.theme.ADCampaingTheme
import com.example.adcampaing.core.utils.Resource

@Composable
fun Login(
    viewModel: AuthViewModel,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .background(Color(0XFFEAFBFF))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val loginFlow = viewModel.loginFlow.collectAsState()

        Row(
            modifier = Modifier
                .padding(8.dp)
                .padding(horizontal = 12.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.clickable {
//                    navController.popBackStack()
                },
                imageVector = Icons.Default.ArrowBack,
                tint = Color.Black,
                contentDescription = "Back arrow"
            )

            Text(
                text = "Welcome Back!!!",
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
                id = R.drawable.login
            ),
            contentDescription = "Logo",
            modifier = Modifier
                .size(180.dp)
                .padding(vertical = 12.dp)
        )

        Text(
            text = "Let's Help you log into your Account",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif
        )


        var inputEmail by remember { mutableStateOf("") }
        var passwordInput by remember { mutableStateOf("") }
        var isPasswordVisible by remember { mutableStateOf(false) }

        val color = Color.Black.copy(alpha = 0.78F)
        val focusManager = LocalFocusManager.current

//        val isEmailValid by remember {
//            derivedStateOf {
////                Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()
//                Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()
//            }
//        }
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val isEmailValid = emailRegex.matches(inputEmail)

        val isPasswordValid by remember {
            derivedStateOf {
                passwordInput.length > 7
            }
        }
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
            isError = !isPasswordVisible,
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
                viewModel.login(inputEmail, passwordInput)
//                startForResult.launch(googleSignInClient?.signInIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                contentColor = Color.White
            ),
            enabled = isPasswordValid && isEmailValid ,
        ) {
            Text(text = "SignIn ", modifier = Modifier.padding(6.dp))
        }
        loginFlow.value.let {
            when (it) {
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        if (viewModel.currentUser?.uid == "yv7LwL6cBzV5eFQQ67UrwLlk1SM2" || viewModel.currentUser?.uid == "ycQ2tVRHxLOttJNwdfYL1lxERss2") {
                            navController.navigate(Screens.DashBoard.route) {
                                popUpTo(Screens.DashBoard.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            navController.navigate(Screens.UserHome.route) {
                                popUpTo(Screens.UserHome.route) {
                                    inclusive = true
                                }
                            }
                        }

                    }
                }
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .border(
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colors.primary,
                                width = 2.dp
                            )
                    )
                }
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }

                else -> {

                }
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
            Text(text = "Sign In with Google", modifier = Modifier.padding(6.dp))
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Do not Have an Account?")
            TextButton(onClick = {navController.navigate(Screens.Register.route)}) {
                Text(text = "SignUp", color = Color.Blue )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginPrev() {
    ADCampaingTheme {
        val viewModel: AuthViewModel = hiltViewModel()
        val navController: NavController = rememberNavController()
        Login(viewModel, navController)
    }
}