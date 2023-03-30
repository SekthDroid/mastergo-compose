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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.categories.data.CategoriesProvider
import com.sekthdroid.mastergo.categories.domain.Category
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.common.PrimaryButton
import com.sekthdroid.mastergo.common.SearchInput
import com.sekthdroid.mastergo.common.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onMenuClick: () -> Unit,
    onBackClicked: () -> Unit,
    onCategoryClicked: (Category) -> Unit
) {
    Scaffold(
        topBar = {
            AppToolbar(
                title = "Categories",
                onBackClicked = onBackClicked,
                onMenuClick = onMenuClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val (search, setSearch) = remember { mutableStateOf("") }
            val categories = remember(search) {
                if (search.isEmpty()) {
                    CategoriesProvider.getItems()
                } else {
                    CategoriesProvider.findCategories(search)
                }
            }

            SearchInput(
                value = search,
                onValueChanged = setSearch,
                label = "Search by category"
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) {
                    CategoryItem(
                        icon = it.icon,
                        title = it.name,
                        onClick = { onCategoryClicked(it) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SecondaryButton(
                    text = "Back",
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f)
                )

                PrimaryButton(
                    text = "Next",
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CategoryItem(icon: Int, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .border(2.dp, color = Color(0xFFF7F7F7))
            .clickable { onClick() }
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .background(color = Color(0xFFF7F7F7))
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = ""
            )
        }

        Text(
            text = title,
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