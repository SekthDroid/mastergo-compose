package com.sekthdroid.mastergo.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.common.PrimaryButton
import com.sekthdroid.mastergo.common.SearchInput
import com.sekthdroid.mastergo.common.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CategoriesScreen() {
    Scaffold(
        topBar = {
            AppToolbar("Categories")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val (search, setSearch) = remember { mutableStateOf("") }

            SearchInput(value = search, onValueChanged = setSearch, label = "Search by category")

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listOf(1, 2, 3, 4)) {
                    CategoryItem()
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SecondaryButton(
                    text = "Back",
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(.5f)
                )

                PrimaryButton(
                    text = "Next",
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 100)
@Composable
fun CategoryItem() {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .border(1.dp, color = Color(0xFFE2E2E0))
            .padding(end = 16.dp)
            .clickable {  },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .background(color = Color(0xFFE2E2E0))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_furniture),
                contentDescription = ""
            )
        }

        Text(
            text = "Furniture Works",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF838391)
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "",
        )
    }
}