package com.sekthdroid.mastergo.payments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.common.CreditCardVisualTransformation
import com.sekthdroid.mastergo.common.DefaultInput
import com.sekthdroid.mastergo.common.PasswordInput
import com.sekthdroid.mastergo.common.PrimaryButton
import com.sekthdroid.mastergo.payments.data.CardsProvider
import com.sekthdroid.mastergo.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(cardId: String, onBackClicked: () -> Unit, onMenuClicked: () -> Unit) {
    Scaffold(
        topBar = {
            AppToolbar(
                "Payment cards",
                onBackClicked = onBackClicked,
                onMenuClick = onMenuClicked
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(30.dp)
        ) {
            val card = remember(cardId) { CardsProvider.getCard(cardId) }
            var number by remember(card) { mutableStateOf(card.number) }
            var expirationMonth by remember(card) { mutableStateOf(card.expiration.month) }
            var expirationYear by remember(card) { mutableStateOf(card.expiration.year) }
            var cvv by remember(card) { mutableStateOf(card.cvv) }

            val cardExpiration = remember(expirationMonth, expirationYear) {
                Expiration(expirationMonth, expirationYear)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(26.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                CreditCard(
                    cardNumber = number,
                    expiration = formatExpiration(cardExpiration),
                    balance = card.balance,
                    backgroundColors = card.brand.toBrush()
                )

                CardField(title = "Card Number") {
                    DefaultInput(
                        value = number,
                        onValueChanged = { value ->
                            if (value.length <= 16) {
                                number = value
                            }
                        },
                        backgroundColor = LightGray,
                        visualTransformation = CreditCardVisualTransformation()
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CardField(
                        title = "Exp. Month",
                        modifier = Modifier.weight(1f)
                    ) {
                        CreditCardPicker(
                            value = "%02d".format(expirationMonth),
                            values = CardsProvider.getMonths(),
                            onValuePicked = { value -> expirationMonth = value.toInt() },
                        )
                    }
                    CardField(
                        title = "Exp. Year",
                        modifier = Modifier.weight(1f)
                    ) {
                        CreditCardPicker(
                            value = expirationYear.toString(),
                            values = CardsProvider.getYears(),
                            onValuePicked = { value -> expirationYear = value.toInt() },
                        )
                    }
                    CardField(
                        title = "CVV",
                        modifier = Modifier.weight(1f)
                    ) {
                        PasswordInput(
                            value = cvv,
                            placeHolder = "",
                            onValueChanged = {
                                if (it.length <= 3) {
                                    cvv = it
                                }
                            }
                        )
                    }
                }
            }

            PrimaryButton(
                text = "Save card",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CreditCardPicker(value: String, values: List<String>, onValuePicked: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(LightGray)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = value,
                textAlign = TextAlign.Start,
                color = Color(0xFFB0B0C3),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f, true)
            )
            IconButton(
                onClick = { isExpanded = true },
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_dropdown),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.background(LightGray)
        ) {
            values.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        onValuePicked(value)
                        isExpanded = false
                    },
                ) {
                    Text(
                        text = value,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun CardField(title: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color(0xFF525464)
        )
        content()
    }
}
