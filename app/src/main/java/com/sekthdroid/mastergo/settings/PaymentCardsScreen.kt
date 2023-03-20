package com.sekthdroid.mastergo.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.common.PrimaryButton

enum class CardBrand {
    Visa,
    Mastercard
}

fun CardBrand.toBrush(): Brush {
    return when (this) {
        CardBrand.Visa -> {
            Brush.horizontalGradient(
                listOf(
                    Color(0xFF02DA80), Color(0xFF0284D8)
                )
            )
        }

        CardBrand.Mastercard -> {
            Brush.horizontalGradient(
                listOf(
                    Color(0xFFF6A11A), Color(0xFFF23B14)
                )
            )
        }
    }
}

data class CardInfo(
    val brand: CardBrand,
    val number: String,
    val expiration: String,
    val balance: String
)

private fun getCards(): List<CardInfo> {
    return listOf(
        CardInfo(
            brand = CardBrand.Visa,
            number = "**** **** **** 3872",
            expiration = "17/2020",
            balance = "$ 25,388"
        ),
        CardInfo(
            brand = CardBrand.Visa,
            number = "**** **** **** 2873",
            expiration = "07/2022",
            balance = "$ 34,880"
        ),
        CardInfo(
            brand = CardBrand.Mastercard,
            number = "**** **** **** 3212",
            expiration = "10/2024",
            balance = "$ 9,568"
        ),
        CardInfo(
            brand = CardBrand.Visa,
            number = "**** **** **** 3412",
            expiration = "12/2024",
            balance = "$ 41,563"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCardsScreen() {
    Scaffold(
        topBar = {
            AppToolbar("Payment cards")
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            val cards = remember { getCards() }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(cards) { each ->
                    CardPaymentItem(
                        cardNumber = each.number,
                        cardExpiration = each.expiration,
                        cardBalance = each.balance,
                        cardBrand = each.brand,
                        onClicked = { println() }
                    )
                }
            }
            PrimaryButton(
                text = "Add new card",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPaymentItemPreview() {
    CardPaymentItem(
        "**** **** **** 1234",
        cardExpiration = "17/2020",
        cardBalance = "$ 25,123",
        cardBrand = CardBrand.Visa,
        onClicked = {}
    )
}

@Composable
fun CardPaymentItem(
    cardNumber: String,
    cardExpiration: String,
    cardBalance: String,
    cardBrand: CardBrand,
    onClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClicked() }
            .border(1.dp, color = Color(0xFFE2E2E0))
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CreditCard(
                cardNumber = cardNumber.split(" ").takeLast(2).joinToString(" "),
                expiration = cardExpiration,
                balance = cardBalance,
                modifier = Modifier.size(width = 65.dp, height = 42.dp),
                numberTextStyle = TextStyle(
                    fontWeight = FontWeight(500),
                    fontSize = 4.sp,
                    color = Color.White
                ),
                expirationTextStyle = TextStyle(
                    fontWeight = FontWeight(500),
                    fontSize = 4.sp,
                    color = Color.White
                ),
                balanceTextStyle = TextStyle(
                    fontWeight = FontWeight(500),
                    fontSize = 8.sp,
                    color = Color.White
                ),
                contentPadding = 4.dp,
                backgroundColors = cardBrand.toBrush(),
                radius = 2.dp
            )

            Column(
                modifier = Modifier.weight(3f)
            ) {
                Text(text = cardNumber, fontSize = 16.sp, color = Color(0xFF525464))
                Text(text = cardExpiration, fontSize = 14.sp, color = Color(0xFF838391))
            }

            Image(
                painter = painterResource(id = R.drawable.visa_logo),
                contentDescription = "visa",
                alignment = Alignment.CenterEnd,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

val DefaultCardTextStyle = TextStyle(
    fontWeight = FontWeight(500), fontSize = 16.sp, color = Color.White
)

val DefaultBalanceTextStyle = DefaultCardTextStyle.copy(
    fontSize = 24.sp
)

@Composable
fun CreditCard(
    cardNumber: String,
    expiration: String,
    balance: String,
    modifier: Modifier = Modifier,
    numberTextStyle: TextStyle = DefaultCardTextStyle,
    expirationTextStyle: TextStyle = DefaultCardTextStyle,
    balanceTextStyle: TextStyle = DefaultBalanceTextStyle,
    radius: Dp = 8.dp,
    contentPadding: Dp = 20.dp,
    backgroundColors: Brush = Brush.horizontalGradient(
        listOf(
            Color(0xFF02DA80), Color(0xFF0284D8)
        )
    )
) {
    Box(
        modifier = modifier
            .aspectRatio(1.54f)
            .clip(RoundedCornerShape(radius))
            .background(backgroundColors)
            .padding(contentPadding)
    ) {
        Text(
            text = cardNumber,
            style = numberTextStyle,
            modifier = Modifier.align(Alignment.TopStart)
        )
        Text(
            text = expiration,
            style = expirationTextStyle,
            modifier = Modifier.align(Alignment.TopEnd)
        )
        Text(
            text = balance,
            style = balanceTextStyle,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}