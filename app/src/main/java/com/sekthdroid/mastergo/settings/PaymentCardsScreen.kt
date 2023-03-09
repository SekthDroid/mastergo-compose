package com.sekthdroid.mastergo.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sekthdroid.mastergo.common.AppToolbar

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
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
            ) {
                
            }
        }
    }
}