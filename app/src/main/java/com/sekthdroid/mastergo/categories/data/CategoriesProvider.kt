package com.sekthdroid.mastergo.categories.data

import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.categories.domain.Category
import com.sekthdroid.mastergo.categories.domain.CategoryWork

object CategoriesProvider {
    fun getItems(): List<Category> {
        return listOf(
            Category(
                id = "furniture-works",
                icon = R.drawable.ic_furniture,
                name = "Furniture Works"
            ),
            Category(
                id = "cleaning-services",
                icon = R.drawable.ic_cleaning,
                name = "Cleaning services"
            ),
            Category(
                id = "equipment-repair",
                icon = R.drawable.ic_equipment,
                name = "Equipment repair"
            ),
            Category(
                id = "courier-services",
                icon = R.drawable.ic_courier,
                name = "Courier services"
            ),
            Category(
                id = "interior-designs",
                icon = R.drawable.ic_interior,
                name = "Interior designs"
            )
        )
    }

    fun getCategoryById(categoryId: String): Category {
        return getItems().find { it.id == categoryId }!!
    }

    fun findCategories(searchCriteria: String): List<Category> {
        return if (searchCriteria.isEmpty()) {
            getItems()
        } else {
            val regex = Regex(searchCriteria, RegexOption.IGNORE_CASE)
            getItems().filter { regex.containsMatchIn(it.name) }
        }
    }

    fun getCategoryItems(parentCategory: String): List<CategoryWork> {
        return when (parentCategory) {
            "furniture-works" -> {
                listOf(
                    CategoryWork(
                        "assembly",
                        "Assembly"
                    ),
                    CategoryWork(
                        "varnish",
                        "Varnish"
                    ),
                    CategoryWork(
                        "transport",
                        "Transport"
                    )
                )
            }

            "cleaning-services" -> {
                listOf(
                    CategoryWork(
                        "laundry",
                        "Laundry"
                    ),
                    CategoryWork(
                        "ironing",
                        "Ironing"
                    ),
                    CategoryWork(
                        "house",
                        "House"
                    )
                )
            }

            "equipment-repair" -> {
                listOf(
                    CategoryWork(
                        "pc",
                        "Computers"
                    ),
                    CategoryWork(
                        "tv",
                        "Television"
                    ),
                    CategoryWork(
                        "electronics",
                        "Smartphone/tablets"
                    )
                )
            }

            "courier-services" -> {
                listOf(
                    CategoryWork(
                        "messaging",
                        "Postal Service"
                    ),
                    CategoryWork(
                        "packaging",
                        "Packaging"
                    ),
                    CategoryWork(
                        "international-courier",
                        "International courier"
                    ),
                    CategoryWork(
                        "local-courier",
                        "Local courier"
                    )
                )
            }

            "interior-designs" -> {
                listOf(
                    CategoryWork(
                        "wall-painting",
                        "Wall painting"
                    ),
                    CategoryWork(
                        "decoration",
                        "Decoration"
                    ),
                    CategoryWork(
                        "lightning",
                        "Lightning"
                    ),
                    CategoryWork(
                        "materials",
                        "Materials"
                    )
                )
            }

            else -> {
                emptyList()
            }
        }
    }
}