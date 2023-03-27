package com.sekthdroid.mastergo.payments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.common.CreditCardVisualTransformation
import java.time.Month

val DefaultCardTextStyle = TextStyle(
    fontWeight = FontWeight(500),
    fontSize = 16.sp,
    color = Color.White
)

val DefaultBalanceTextStyle = DefaultCardTextStyle.copy(
    fontSize = 24.sp
)

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

fun formatCardNumber(number: String, format: CreditCardNumberFormat): String {
    return CreditCardVisualTransformation()
        .filter(AnnotatedString(number))
        .text
        .run {
            if (format == CreditCardNumberFormat.Short) {
                takeLast(9)
            } else {
                this
            }
        }
        .toString()
}

fun formatExpiration(month: Int, year: Int): String {
    return "${"%02d".format(month)}/${year.toString().takeLast(2)}"
}

fun formatExpiration(expiration: Expiration): String {
    return formatExpiration(expiration.month, expiration.year)
}

enum class CreditCardNumberFormat {
    Short,
    Long
}

@Composable
fun CreditCardNumberText(
    value: String,
    format: CreditCardNumberFormat,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        text = formatCardNumber(value, format),
        style = textStyle,
        modifier = modifier
    )
}

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
    backgroundColors: Brush = horizontalGradient(
        listOf(
            Color(0xFF02DA80),
            Color(0xFF0284D8)
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
        CreditCardNumberText(
            value = cardNumber,
            format = CreditCardNumberFormat.Short,
            textStyle = numberTextStyle,
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