@file:OptIn(ExperimentalAnimationApi::class)

package com.sekthdroid.mastergo

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.sekthdroid.mastergo.categories.CategoriesItemsScreen
import com.sekthdroid.mastergo.categories.CategoriesScreen
import com.sekthdroid.mastergo.notifications.NotificationsScreen
import com.sekthdroid.mastergo.payments.CardScreen
import com.sekthdroid.mastergo.payments.PaymentCardsScreen
import com.sekthdroid.mastergo.settings.SettingsScreen
import com.sekthdroid.mastergo.theme.MastergoTheme

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContent {
            MastergoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val controller = rememberAnimatedNavController()
                    val state = rememberSwipeMenuState(items = MenuOption.values().toList())
                    val onMenuClick: () -> Unit = remember {
                        {
                            state.toogleState()
                        }
                    }
                    SwipeMenu(
                        swipeMenuState = state,
                        onMenuItemClick = {
                            state.selected.value = it
                            state.toogleState()
                            when (it) {
                                MenuOption.Profile -> controller.navigateOriginScreen("profile")
                                MenuOption.Home -> controller.navigateOriginScreen("categories")
                                MenuOption.Messages -> controller.navigateOriginScreen("messages")
                                MenuOption.Settings -> controller.navigateOriginScreen("settings")
                            }
                        }
                    ) {
                        AnimatedNavHost(
                            navController = controller,
                            startDestination = "categories"
                        ) {
                            composable("categories") {
                                CategoriesScreen(
                                    onBackClicked = {
                                        if (state.isExpanded) {
                                            state.toogleState()
                                        }
                                    },
                                    onMenuClick = onMenuClick,
                                    onCategoryClicked = {
                                        controller.navigate("categories/${it.id}/items")
                                    }
                                )
                            }
                            childComposable("categories/{id}/items") {
                                val category = it.arguments?.getString("id").orEmpty()
                                CategoriesItemsScreen(
                                    categoryId = category,
                                    onBackClick = {
                                        if (state.isExpanded) {
                                            state.toogleState()
                                        } else {
                                            controller.popBackStack()
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
                            childComposable(route = "payments") {
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
                            childComposable(route = "card/{cardId}") { entry ->
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

fun NavController.navigateOriginScreen(route: String) {
    navigate(route) {
        popUpTo(route) { inclusive = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.childComposable(
    route: String,
    content: @Composable() AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideInHorizontally { it }
        },
        exitTransition = {
            slideOutHorizontally { -it }
        },
        popEnterTransition = {
            slideInHorizontally { -it }
        },
        popExitTransition = {
            slideOutHorizontally { it }
        },
        content = content
    )
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