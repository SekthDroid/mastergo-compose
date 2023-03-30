package com.sekthdroid.mastergo.categories

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sekthdroid.mastergo.categories.data.CategoriesProvider
import com.sekthdroid.mastergo.common.AppToolbar
import com.sekthdroid.mastergo.common.PrimaryButton
import com.sekthdroid.mastergo.common.SearchInput
import com.sekthdroid.mastergo.common.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesItemsScreen(
    categoryId: String,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val parentCategory = remember(categoryId) {
        CategoriesProvider.getCategoryById(categoryId)
    }
    val (search, setSearch) = remember {
        mutableStateOf("")
    }
    val selectedItems = remember {
        mutableStateListOf<String>()
    }
    val items = remember(categoryId, search) {
        if (search.isBlank()) {
            CategoriesProvider.getCategoryItems(categoryId)
        } else {
            val regex = Regex(search, RegexOption.IGNORE_CASE)
            CategoriesProvider.getCategoryItems(categoryId)
                .filter { regex.containsMatchIn(it.name) }
        }
    }

    Scaffold(
        topBar = {
            AppToolbar(
                title = parentCategory.name,
                onBackClicked = onBackClick,
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
            SearchInput(value = search, onValueChanged = setSearch, label = "Search...")

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items, key = { it.id }) {
                    CategoryWorkItem(
                        title = it.name,
                        isSelected = it.id in selectedItems,
                        onClick = {
                            println("Clicked on $it")
                            if (it.id in selectedItems) {
                                println("Removing $it")
                                selectedItems.remove(it.id)
                            } else {
                                println("Adding $it")
                                selectedItems.add(it.id)
                            }
                            println("Items $selectedItems")
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SecondaryButton(
                    text = "Skip",
                    onClick = onBackClick,
                    modifier = Modifier.weight(1f)
                )
                PrimaryButton(
                    text = "Done",
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryWorkItemPreview() {
    CategoryWorkItem(title = "Sample", isSelected = false) {

    }
}

@Composable
fun CategoryWorkItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (isSelected) {
            Color(0xFFFFB19D)
        } else {
            Color(0xFFF7F7F7)
        }
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) {
            Color.White
        } else {
            Color.Black
        }
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) {
            Color(0xFF525464)
        } else {
            Color(0xFF838391)
        }
    )

    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .border(width = 2.dp, color = color)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(horizontal = 20.dp)
        )

        Box(
            modifier = Modifier
                .height(60.dp)
                .aspectRatio(1f)
                .background(color = color),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = if (isSelected) Icons.Filled.Done else Icons.Filled.Add,
                contentDescription = if (isSelected) "Remove" else "Add",
                colorFilter = ColorFilter.tint(iconColor)
            )
        }
    }
}