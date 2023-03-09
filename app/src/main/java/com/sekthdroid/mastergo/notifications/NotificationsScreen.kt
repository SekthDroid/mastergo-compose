package com.sekthdroid.mastergo.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.common.PrimaryButton
import java.util.UUID

data class Author(
    val name: String,
    val companyName: String,
    val avatarUrl: String
)

data class Notification(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val author: Author
)

val notifications = listOf(
    Notification(
        text = """
            Sorry, all the artists in the Interior Design category are busy right now. 
            If your task is still relevant - go to the task details page and click "Extend task”.
        """.trimIndent(),
        author = Author(
            name = "Joel Rowe",
            companyName = "Bitrow Company",
            avatarUrl = "https://randomuser.me/api/portraits/men/18.jpg"
        )
    ),
    Notification(
        text = """
            We have found a contractor for your task "Cleaning services”. Please see the details.
        """.trimIndent(),
        author = Author(
            name = "Cole Payne",
            companyName = "Corporation Kraton",
            avatarUrl = "https://randomuser.me/api/portraits/men/21.jpg"
        )
    ),
    Notification(
        text = """
            David Coleman is ready to complete your assignment and get started soon! 
            View David's profile and carefully review the order details. Then confirm the order.
        """.trimIndent(),
        author = Author(
            name = "Trynke Raes",
            companyName = "Grand Service Company",
            avatarUrl = "https://randomuser.me/api/portraits/women/96.jpg"
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBackClicked: () -> Unit, onMenuClicked: () -> Unit) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Notifications",
                onBackClicked = onBackClicked,
                onMenuClick = onMenuClicked
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(30.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(60.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(notifications) { each ->
                    NotificationItem(notification = each)
                }
            }
            PrimaryButton(
                text = "View all",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    println("Clicked on View All")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationPreview() {
    NotificationItem(notification = notifications.first())
}

@Composable
fun NotificationItem(notification: Notification) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = notification.author.avatarUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(
                    text = notification.author.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color(0xFF525464)
                )
                Text(
                    text = notification.author.companyName,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF838391)
                )
            }
        }

        Text(
            text = notification.text,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF616173)
        )
    }
}