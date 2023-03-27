package com.sekthdroid.mastergo.payments.data

import com.sekthdroid.mastergo.payments.CardBrand
import com.sekthdroid.mastergo.payments.CardInfo
import com.sekthdroid.mastergo.payments.Expiration

object CardsProvider {
    private val cards: List<CardInfo> = listOf(
        CardInfo(
            brand = CardBrand.Visa,
            number = "************3872",
            expiration = Expiration(7, 2020),
            balance = "$ 25,388",
            cvv = "123"
        ),
        CardInfo(
            brand = CardBrand.Visa,
            number = "************2873",
            expiration = Expiration(7, 2022),
            balance = "$ 34,880",
            cvv = "123"
        ),
        CardInfo(
            brand = CardBrand.Mastercard,
            number = "************3212",
            expiration = Expiration(10, 2024),
            balance = "$ 9,568",
            cvv = "123"
        ),
        CardInfo(
            brand = CardBrand.Visa,
            number = "************3412",
            expiration = Expiration(12, 2024),
            balance = "$ 41,563",
            cvv = "123"
        )
    )

    fun getCards(): List<CardInfo> {
        return cards
    }

    fun getCard(id: String): CardInfo {
        return cards.find { it.id == id }!!
    }

    fun getMonths() = (1..12).map { "%02d".format(it) }
    fun getYears() = (2023..2030).map { it.toString() }
}