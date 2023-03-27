package com.sekthdroid.mastergo.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.common.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClicked: () -> Unit,
    onMenuClicked: () -> Unit,
    onPaymentClicked: () -> Unit,
    onWriteClicked: () -> Unit,
    onRateClicked: () -> Unit,
    onAbout: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Settings",
                onBackClicked = onBackClicked,
                onMenuClick = onMenuClicked
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .padding(30.dp)
        ) {
            SettingsItem(
                title = "Payment Cards",
                onClick = onPaymentClicked
            )
            SettingsItem(
                title = "Write to us",
                onClick = onWriteClicked
            )
            SettingsItem(
                title = "Rate us on app store",
                onClick = onRateClicked
            )
            SettingsItem(
                title = "About us",
                onClick = onAbout
            )

            ClickableText(
                text = buildAnnotatedString {
                    pushStringAnnotation("logout", "logout")
                    withStyle(SpanStyle(color = Color(0xFFFFB19D))) {
                        append("Log out")
                    }
                    pop()
                },
                onClick = { onLogout() },
                modifier = Modifier.align(Alignment.Start),
                style = TextStyle(textDecoration = TextDecoration.Underline, fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun SettingsItem(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(0xFFF7F7F7))
            .clickable { onClick() }
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}