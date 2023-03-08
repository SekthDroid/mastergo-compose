package com.sekthdroid.mastergo

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.sekthdroid.mastergo.theme.DarkGray
import com.sekthdroid.mastergo.theme.Ocean
import kotlin.math.roundToInt

enum class MenuState {
    Expanded,
    Collapsed
}

enum class MenuOption(val icon: Int, val text: String) {
    Home(R.drawable.ic_menu_home, "Home"),
    Profile(R.drawable.ic_menu_group, "Profile"),
    Settings(R.drawable.ic_menu_settings, "Settings"),
    Messages(R.drawable.ic_menu_messages, "Messages")
}

/**
 * This is probably a mess lol
 */
@Composable
fun SwipeMenu(
    swipeMenuState: SwipeMenuState,
    onMenuItemClick: (MenuOption) -> Unit,
    content: @Composable () -> Unit
) {
    var size by remember { mutableStateOf(Size.Zero) }
    var menuSize by remember { mutableStateOf(Size.Zero) }

    val isExpanded = swipeMenuState.menuState.value == MenuState.Expanded
    val transition = updateTransition(targetState = isExpanded, label = "what")
    val offset = transition.animateOffset(label = "what", transitionSpec = { tween(300) }) {
        if (it) {
            Offset(LocalDensity.current.run { 96.dp.toPx() * 2 + menuSize.width }, 0f)
        } else {
            Offset(0f, 0f)
        }
    }

    val scale by transition.animateFloat(transitionSpec = { tween(300) }, label = "scale") {
        if (it) {
            0.9f
        } else {
            1f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF525464))
            .onSizeChanged {
                size = it.toSize()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .offset(x = 96.dp)
                .onSizeChanged {
                    menuSize = it.toSize()
                },
            verticalArrangement = Arrangement.spacedBy(
                36.dp,
                alignment = Alignment.CenterVertically
            ),
        ) {
            swipeMenuState.items.forEach {
                MenuItem(
                    icon = it.icon,
                    title = it.text,
                    isSelected = swipeMenuState.selected.value == it,
                    onClick = { onMenuItemClick(it) }
                )
            }
        }
        Surface(
            modifier = Modifier
                .size(width = size.width.dp, height = size.height.dp)
                .offset { IntOffset(offset.value.x.roundToInt(), 0) }
                .scale(scale)
                .background(color = Color.White)
                .shadow(elevation = if (isExpanded) 24.dp else 0.dp)
                .pointerInput(isExpanded) {
                    if (isExpanded) {
                        awaitEachGesture {
                            awaitFirstDown()
                            swipeMenuState.toogleState()
                        }
                    }
                },
            content = content
        )
    }
}

@Composable
fun MenuItem(icon: Int, title: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    if (isSelected) Ocean else DarkGray
                )
        ) {
            Image(painter = painterResource(id = icon), contentDescription = title)
        }
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun MenuItemPreview() {
    MenuItem(
        icon = R.drawable.ic_menu_home,
        title = "Home",
        isSelected = false,
        onClick = {}
    )
}