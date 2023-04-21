package ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import di.InjectionHelper
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.BottomNavigationItem.Home
import ui.BottomNavigationItem.Location
import ui.BottomNavigationItem.Search
import ui.BottomNavigationItem.Settings
import unselectedNavigationItemColor

@Composable
fun MainScreen(screenHeight: Dp) {

    var currentRoute by remember { mutableStateOf(Home.route) }
    var forceRemote by remember { mutableStateOf(false) }
    var latitude = 64.63333
    var longitude = 47.866669

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(currentRoute) {
                currentRoute = it
            }
        }
    ) {
        when (currentRoute) {
            Home.route -> HomeScreen(
                screenHeight,
                HomeViewModel(
                    InjectionHelper.weatherRepository
                ),
                scaffoldState,
                latitude,
                longitude,
                forceRemote
            )

            Search.route -> SearchScreen(
                SearchViewModel(
                    InjectionHelper.weatherRepository
                )
            ) {
                latitude = it.first
                longitude = it.second
                forceRemote = true
                currentRoute = "home"
            }

            else -> {

            }
        }
    }

}

sealed class BottomNavigationItem(val label: String, val iconPath: String, val route: String) {

    object Home : BottomNavigationItem("Home", "ic_home.xml", "home")
    object Search : BottomNavigationItem("Search", "ic_search.xml", "search")
    object Location : BottomNavigationItem("Location", "ic_compass.xml", "location")
    object Settings : BottomNavigationItem("Settings", "ic_settings.xml", "settings")

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onDestinationChanged: (String) -> Unit
) {

    BottomNavigation(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            )
    ) {
        listOf(Home, Search, Location, Settings).forEach { destination ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(destination.iconPath),
                        contentDescription = destination.label
                    )
                },
                label = null,
                selectedContentColor = Color.White,
                unselectedContentColor = unselectedNavigationItemColor,
                alwaysShowLabel = false,
                selected = currentRoute == destination.route,
                onClick = {
                    onDestinationChanged(destination.route)
                }
            )
        }
    }
}