package com.sekthdroid.mastergo.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.sekthdroid.mastergo.R
import kotlinx.coroutines.launch

val Green = Color(0xFF20C3AF)
val Grey = Color(0xFF838391)

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true, widthDp = 375, heightDp = 812)
@Composable
fun OnboardingScreen(onAddButtonClicked: () -> Unit = {}) {
    val items = remember { OnboardingPages }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val isLastPage = remember(pagerState.currentPage) {
        pagerState.currentPage == pagerState.pageCount - 1
    }
    val bottomButtonsAlphaState by animateFloatAsState(
        targetValue = if (isLastPage) 0f else 1f
    )

    Column(
        modifier = Modifier.padding(vertical = 24.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        HorizontalPager(
            count = items.size,
            state = pagerState,
            modifier = Modifier.weight(4f)
        ) {
            OnboardingPage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp), pageItem = items[it]
            )
        }

        Crossfade(
            targetState = isLastPage,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (it) {
                RoundedButton(onAddButtonClicked)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        pageCount = items.size,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .alpha(bottomButtonsAlphaState)
                            .padding(32.dp)
                    )

                    BasicButton(
                        text = "Next",
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun BasicButton(text: String, onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Green),
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

@Composable
fun OnboardingPage(modifier: Modifier = Modifier, pageItem: OnboardingItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = pageItem.title),
            fontWeight = FontWeight.Medium,
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )
        Image(
            painter = painterResource(id = pageItem.image),
            contentDescription = "",
        )
        Text(
            text = stringResource(id = pageItem.description),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Grey
        )
    }
}

data class OnboardingItem(
    val title: Int, val image: Int, val description: Int
)

val OnboardingPages = listOf(
    OnboardingItem(
        R.string.onboarding_title_1, R.drawable.img_onboarding_1, R.string.onboarding_description_1
    ),
    OnboardingItem(
        R.string.onboarding_title_2, R.drawable.img_onboarding_2, R.string.onboarding_description_2
    ),
    OnboardingItem(
        R.string.onboarding_title_3, R.drawable.img_onboarding_3, R.string.onboarding_description_3
    ),
    OnboardingItem(
        R.string.onboarding_title_4, R.drawable.img_onboarding_4, R.string.onboarding_description_4
    ),
)