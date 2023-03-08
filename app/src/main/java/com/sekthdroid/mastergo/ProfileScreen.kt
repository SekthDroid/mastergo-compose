package com.sekthdroid.mastergo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.common.PrimaryButton
import com.sekthdroid.mastergo.common.SecondaryButton
import com.sekthdroid.mastergo.theme.DarkGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBackClicked: () -> Unit, onMenuClick: () -> Unit) {
    Scaffold(topBar = {
        AppToolbar(onBackClicked = onBackClicked, onMenuClick = onMenuClick)
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileHeader(
                onEditClicked = { /*TODO*/ },
                aboutMeClicked = { /*TODO*/ },
                onReviewsClicked = { /*TODO*/ }
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGray)
                    .padding(horizontal = 30.dp, vertical = 40.dp)
            ) {

                ContactInfo(
                    icon = R.drawable.ic_phone,
                    title = "Phone number",
                    value = "+3746589923"
                )

                ContactInfo(
                    icon = R.drawable.ic_email,
                    title = "Email",
                    value = "conrad@gmail.com"
                )

                ContactInfo(
                    icon = R.drawable.ic_completed,
                    title = "Completed projects",
                    value = "248"
                )
            }
        }
    }
}

@Composable
fun ContactInfo(
    icon: Int,
    title: String,
    value: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = Color(0xFF979797)
                )
            )
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier
                .padding(30.dp)
                .size(24.dp)
        )

        Divider(
            color = Color(0xFF979797),
            modifier = Modifier
                .height(40.dp)
                .width(1.dp)
        )

        Spacer(modifier = Modifier.width(30.dp))

        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.alpha(.4f),
                color = Color.White
            )
            Text(
                text = value,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun UserProfileInfo(
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AsyncImage(
            model = "https://randomuser.me/api/portraits/men/79.jpg",
            contentDescription = "user",
            modifier = Modifier
                .size(114.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "Jeremias del Pozo",
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = Color(0xFF525464)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "New York \u2022 ID:1120611",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color(0xFF838391)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = buildAnnotatedString {
                pushStringAnnotation("edit", "edit")
                withStyle(SpanStyle(color = Color(0xFFFFB19D), fontSize = 16.sp)) {
                    append("Edit")
                }
                pop()
            },
            onClick = { onEditClicked() },
            style = TextStyle(textDecoration = TextDecoration.Underline),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileActions(
    modifier: Modifier = Modifier,
    onAboutClicked: () -> Unit = {},
    onReviewsClicked: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            16.dp, alignment = Alignment.CenterHorizontally
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
    ) {
        SecondaryButton(
            text = "About Me",
            onClick = onAboutClicked,
            modifier = Modifier.weight(1f)
        )
        PrimaryButton(
            text = "Reviews",
            onClick = onReviewsClicked,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit,
    aboutMeClicked: () -> Unit,
    onReviewsClicked: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        UserProfileInfo(
            onEditClicked = onEditClicked,
            modifier = Modifier.fillMaxWidth()
        )

        UserProfileActions(
            onAboutClicked = aboutMeClicked,
            onReviewsClicked = onReviewsClicked,
            modifier = Modifier.fillMaxWidth()
        )
    }
}