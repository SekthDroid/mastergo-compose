package com.sekthdroid.mastergo

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sekthdroid.mastergo.categories.CategoriesScreen
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.notifications.NotificationsScreen
import com.sekthdroid.mastergo.settings.SettingsScreen
import com.sekthdroid.mastergo.theme.MastergoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContent {
            MastergoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // OnboardingScreen()
                    // SigningInScreen()
                    // SigningUpScreen()
                    // CategoriesScreen()

                    val controller = rememberNavController()
                    val state = rememberSwipeMenuState(items = MenuOption.values().toList())
                    val onMenuClick: () -> Unit = remember {
                        { state.toogleState() }
                    }
                    SwipeMenu(
                        swipeMenuState = state,
                        onMenuItemClick = {
                            state.selected.value = it
                            state.toogleState()
                            when (it) {
                                MenuOption.Profile -> controller.navigate("profile")
                                MenuOption.Home -> controller.navigate("categories")
                                MenuOption.Messages -> controller.navigate("messages")
                                else -> {

                                }
                            }
                        }
                    ) {
                        NavHost(navController = controller, startDestination = "settings") {
                            composable("categories") {
                                CategoriesScreen(
                                    onMenuClick = onMenuClick
                                )
                            }
                            composable("profile") {
                                ProfileScreen(
                                    onBackClicked = {
                                        if (state.isExpanded) {
                                            state.toogleState()
                                        }
                                    },
                                    onMenuClick = onMenuClick
                                )
                            }
                            composable("messages") {
                                NotificationsScreen(
                                    onBackClicked = {
                                        if (state.isExpanded) {
                                            state.toogleState()
                                        }
                                    },
                                    onMenuClicked = onMenuClick
                                )
                            }
                            composable("settings") {
                                SettingsScreen(
                                    onBackClicked = {
                                        if (state.isExpanded) {
                                            state.toogleState()
                                        }
                                    },
                                    onMenuClicked = onMenuClick,
                                    onPaymentClicked = {

                                    },
                                    onWriteClicked = {

                                    },
                                    onRateClicked = {

                                    },
                                    onAbout = {

                                    },
                                    onLogout = {
                                        
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Stable
class SwipeMenuState(
    val items: List<MenuOption>,
    val selected: MutableState<MenuOption>,
    val menuState: MutableState<MenuState>
) {
    fun toogleState() {
        this.menuState.value = if (menuState.value == MenuState.Expanded) {
            MenuState.Collapsed
        } else {
            MenuState.Expanded
        }
    }

    fun selectOption(option: MenuOption) {
        selected.value = option
    }

    val isExpanded: Boolean = menuState.value == MenuState.Expanded
}

@Composable
fun rememberSwipeMenuState(items: List<MenuOption>): SwipeMenuState = remember {
    SwipeMenuState(
        items = items, selected = mutableStateOf(items.first()), menuState = mutableStateOf(
            MenuState.Collapsed
        )
    )
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MastergoTheme {
        Greeting("Android")
    }
}