package com.sekthdroid.mastergo.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.onboarding.Green

@Preview(showBackground = true, widthDp = 200)
@Composable
fun ButtonsCatalog() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PrimaryButton(text = "Click me", modifier = Modifier.fillMaxWidth())
        SecondaryButton(text = "Click me", onClick = {}, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Green),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        modifier = Modifier
            .height(60.dp)
            .then(modifier)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        border = BorderStroke(width = 1.dp, color = Color(0xFFE2E2E0)),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        modifier = Modifier
            .height(60.dp)
            .then(modifier)
    ) {
        Text(
            text = text,
            color = Color(0xFF838391),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun RoundedButton(onAddButtonClicked: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = onAddButtonClicked,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = Green),
            modifier = Modifier
                .sizeIn(
                    minWidth = 48.dp,
                    minHeight = 48.dp,
                    maxWidth = 82.dp,
                    maxHeight = 82.dp
                )
                .aspectRatio(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}
