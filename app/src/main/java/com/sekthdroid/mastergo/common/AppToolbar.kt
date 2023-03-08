package com.sekthdroid.mastergo.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R

@Preview
@Composable
fun AppToolbarPreview() {
    AppToolbar(title = "Sign in")
}

@Composable
fun AppToolbar(title: String = "", onBackClicked: () -> Unit = {}, onMenuClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onBackClicked,
            modifier = Modifier.align(alignment = Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Back"
            )
        }

        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = onMenuClick,
            modifier = Modifier.align(alignment = Alignment.CenterEnd)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu"
            )
        }
    }

}