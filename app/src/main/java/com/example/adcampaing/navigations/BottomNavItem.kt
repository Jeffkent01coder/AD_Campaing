package com.example.adcampaing.navigations

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.adcampaing.R

enum class BottomNavAdminItem(val icon: Int, val route: String, val title:String){
    DASHBOARD(R.drawable.ic_baseline_dashboard_24,Screens.DashBoard.route,title = "Dashboard"),
    ADDPOST(R.drawable.ic_baseline_add_24,Screens.AddPost.route,title = "Post"),
    ADMINPROFILE( R.drawable.ic_baseline_person_24,Screens.AdminProfile.route,title = "AdminProfile")
}
enum class BottomNavUserItem(val icon: Int, val route: String, val title:String){
    HOME(R.drawable.ic_baseline_home_24,Screens.UserHome.route, title = "Home"),
    USERPROFILE( R.drawable.ic_baseline_person_24,Screens.UserProfile.route,title = "UserProfile"),
}


@Composable
fun BottomNavAdminMenu(
    navController: NavController,
) {
    androidx.compose.material.BottomNavigation(
        backgroundColor = Color(color = 0xFF5B7385),
        contentColor = Color.Black,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        for (item in BottomNavAdminItem.values()){
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = null) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Black,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun BottomNavUserMenu(
    navController: NavController,
) {
    androidx.compose.material.BottomNavigation(
        backgroundColor = Color(color = 0xFF5B7385),
        contentColor = Color.Black,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        for (item in BottomNavUserItem.values()){
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = null) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


