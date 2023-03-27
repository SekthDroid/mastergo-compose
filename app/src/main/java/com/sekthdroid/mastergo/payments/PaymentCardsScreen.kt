package com.sekthdroid.mastergo.payments

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.common.PrimaryButton
import com.sekthdroid.mastergo.payments.data.CardsProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCardsScreen(
    onCardClicked: (String) -> Unit,
    onBackClicked: () -> Unit,
    onMenuClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Payment cards",
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
            val cards = remember { CardsProvider.getCards() }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(cards) { each ->
                    CardPaymentItem(
                        cardNumber = each.number,
                        cardExpiration = formatExpiration(each.expiration),
                        cardBalance = each.balance,
                        cardBrand = each.brand,
                        onClicked = { onCardClicked(each.id) }
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
                CreditCardNumberText(
                    value = cardNumber,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF525464)
                    ),
                    format = CreditCardNumberFormat.Long
                )
                Text(text = cardExpiration, fontSize = 14.sp, color = Color(0xFF838391))
            }

            Image(
                painter = painterResource(
                    id = if (cardBrand == CardBrand.Mastercard)
                        R.drawable.mastercard_logo
                    else
                        R.drawable.visa_logo
                ),
                contentDescription = "visa",
                alignment = Alignment.CenterEnd,
                modifier = Modifier.weight(1f)
            )
        }
    }
}