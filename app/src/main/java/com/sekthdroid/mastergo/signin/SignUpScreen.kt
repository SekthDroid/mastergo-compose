package com.sekthdroid.mastergo.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.common.PrimaryButton
import com.sekthdroid.mastergo.common.PasswordInput
import com.sekthdroid.mastergo.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun SigningUpScreen() {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.align(alignment = Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 18.dp, vertical = 16.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_signup_header),
                contentDescription = "",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(145.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom)
            ) {
                var usernameValue by remember { mutableStateOf("") }
                BasicTextField(
                    value = usernameValue,
                    onValueChange = {
                        usernameValue = it
                    },
                    decorationBox = { innerTextField ->
                        if (usernameValue.isEmpty()) {
                            Text(
                                text = "Username",
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFB0B0C3)
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(LightGray)
                        .padding(horizontal = 16.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                )

                var passwordValue by remember { mutableStateOf("") }
                PasswordInput(
                    value = passwordValue,
                    placeHolder = "Enter password",
                    onValueChanged = { text -> passwordValue = text }
                )

                var passwordRepeatValue by remember { mutableStateOf("") }
                PasswordInput(
                    value = passwordRepeatValue,
                    placeHolder = "Confirm password",
                    onValueChanged = { text -> passwordRepeatValue = text }
                )

                PrimaryButton(
                    text = "Sign Up",
                    onClick = {
                        // TODO: 15/2/23
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(text = "or", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SocialNetworkSignButton(
                        icon = R.drawable.ic_facebook,
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.5f)
                    )
                    SocialNetworkSignButton(
                        icon = R.drawable.ic_twitter,
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.5f)
                    )
                    SocialNetworkSignButton(
                        icon = R.drawable.ic_linkedin,
                        onClick = {

                        },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.5f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = Color(0xFF838391))) {
                            append("Already have an account? ")
                        }
                        pushStringAnnotation("signin", "signin")
                        withStyle(SpanStyle(color = Color(0xFFFFB19D))) {
                            append("Sign in")
                        }
                        pop()
                    },
                    onClick = {
                        println("Clicked on signup")
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}