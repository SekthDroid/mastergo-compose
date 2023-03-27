package com.sekthdroid.mastergo

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sekthdroid.mastergo.categories.CategoriesScreen
import com.sekthdroid.mastergo.notifications.NotificationsScreen
import com.sekthdroid.mastergo.payments.PaymentCardsScreen
import com.sekthdroid.mastergo.payments.CardScreen
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

                    val controller = rememberNavController()
                    val state = rememberSwipeMenuState(items = MenuOption.values().toList())
                    val onMenuClick: () -> Unit = remember {
                        {
                            println("onMenuClick")
                            state.toogleState()
                            println("onMenuClick ${state.menuState.value}")
                        }
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
                                MenuOption.Settings -> controller.navigate("settings")
                            }
                        }
                    ) {
                        NavHost(navController = controller, startDestination = "categories") {
                            composable("categories") {
                                CategoriesScreen(
                                    onBackClicked = {
                                        println("onBackClicked categories ${state.menuState.value}")
                                        if (state.isExpanded) {
                                            state.toogleState()
                                        }
                                    },
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
                                        controller.navigate("payments")
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
                            composable("payments") {
                                PaymentCardsScreen(
                                    onBackClicked = {
                                        if (state.isExpanded) {
                                            state.toogleState()
                                        } else {
                                            controller.popBackStack()
                                        }
                                    },
                                    onMenuClicked = onMenuClick,
                                    onCardClicked = {
                                        controller.navigate("card/$it")
                                    }
                                )
                            }
                            composable("card/{cardId}") { entry ->
                                val cardId = entry.arguments?.getString("cardId").orEmpty()
                                CardScreen(
                                    cardId,
                                    onBackClicked = {
                                        if (state.isExpanded) {
                                            state.toogleState()
                                        } else {
                                            controller.popBackStack()
                                        }
                                    },
                                    onMenuClicked = onMenuClick,
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

    val isExpanded: Boolean
        get() = menuState.value == MenuState.Expanded
}

@Composable
fun rememberSwipeMenuState(items: List<MenuOption>): SwipeMenuState = remember {
    SwipeMenuState(
        items = items,
        selected = mutableStateOf(items.first()),
        menuState = mutableStateOf(MenuState.Collapsed)
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