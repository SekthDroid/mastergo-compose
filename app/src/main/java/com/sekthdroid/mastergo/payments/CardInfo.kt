package com.sekthdroid.mastergo.payments

import java.util.UUID

data class CardInfo(
    val id: String = UUID.randomUUID().toString(),
    val brand: CardBrand,
    val number: String,
    val expiration: Expiration,
    val balance: String,
    val cvv: String
)

enum class CardBrand {
    Visa,
    Mastercard
}

data class Expiration(val month: Int, val year: Int)