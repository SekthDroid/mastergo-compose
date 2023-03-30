package com.sekthdroid.mastergo.categories.data

import com.sekthdroid.mastergo.R
import com.sekthdroid.mastergo.categories.domain.Category
import com.sekthdroid.mastergo.categories.domain.CategoryWork
import java.lang.IllegalArgumentException
import java.util.UUID

object CategoriesProvider {
    private val categories = listOf(
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

    private val subCategories = mapOf(
        "furniture-works" to
                listOf(
                    CategoryWork(id = randomId(), name = "Refinishing a dining table"),
                    CategoryWork(id = randomId(), name = "Assembling a bed frame"),
                    CategoryWork(id = randomId(), name = "Building custom shelves"),
                    CategoryWork(id = randomId(), name = "Upholstering a sofa"),
                    CategoryWork(id = randomId(), name = "Repairing a wooden rocking chair"),
                    CategoryWork(id = randomId(), name = "Restoring antique furniture"),
                    CategoryWork(id = randomId(), name = "Building a wooden coffee table"),
                    CategoryWork(id = randomId(), name = "Building a bookshelf"),
                    CategoryWork(id = randomId(), name = "Installing new hardware on furniture"),
                    CategoryWork(id = randomId(), name = "Refinishing a wooden dresser."),
                ),
        "cleaning-services" to
                listOf(
                    CategoryWork(id = randomId(), "Deep cleaning a kitchen"),
                    CategoryWork(id = randomId(), "Sanitizing bathrooms"),
                    CategoryWork(id = randomId(), "Dusting and wiping surfaces"),
                    CategoryWork(id = randomId(), "Vacuuming and steam cleaning carpets"),
                    CategoryWork(id = randomId(), "Mopping and polishing floors"),
                    CategoryWork(id = randomId(), "Cleaning windows and mirrors"),
                    CategoryWork(id = randomId(), "Pressure washing exteriors"),
                    CategoryWork(id = randomId(), "Removing stains and odors"),
                    CategoryWork(id = randomId(), "Organizing closets and cabinets"),
                    CategoryWork(id = randomId(), "Cleaning air ducts.")
                ),
        "equipment-repair" to
                listOf(
                    CategoryWork(id = randomId(), "Fixing a dishwasher or garbage disposal"),
                    CategoryWork(id = randomId(), "Repairing HVAC systems"),
                    CategoryWork(id = randomId(), "Fixing electric water heaters"),
                    CategoryWork(id = randomId(), "Repairing refrigerators"),
                    CategoryWork(id = randomId(), "Fixing plumbing issues"),
                    CategoryWork(id = randomId(), "Repairing air conditioning units"),
                    CategoryWork(id = randomId(), "Fixing washing machines and dryers"),
                    CategoryWork(id = randomId(), "Repairing furnaces"),
                    CategoryWork(id = randomId(), "Fixing garage door openers"),
                    CategoryWork(id = randomId(), "Troubleshooting electrical issues."),
                ),
        "courier-services" to
                listOf(
                    CategoryWork(id = randomId(), "Delivering packages locally or internationally"),
                    CategoryWork(id = randomId(), "Providing same-day delivery"),
                    CategoryWork(id = randomId(), "Delivering food and groceries"),
                    CategoryWork(id = randomId(), "Transporting sensitive documents and items"),
                    CategoryWork(id = randomId(), "Providing rush or emergency delivery"),
                    CategoryWork(id = randomId(), "Delivering medical supplies"),
                    CategoryWork(id = randomId(), "Offering warehousing and distribution"),
                    CategoryWork(id = randomId(), "Providing logistics solutions"),
                    CategoryWork(id = randomId(), "Offering scheduled delivery"),
                    CategoryWork(id = randomId(), "Providing pick-up and drop-off services."),
                ),
        "interior-designs" to
                listOf(
                    CategoryWork(id = randomId(), "Creating design plans for new spaces"),
                    CategoryWork(id = randomId(), "Selecting colors, fabrics, and furniture"),
                    CategoryWork(id = randomId(), "Choosing lighting and placement"),
                    CategoryWork(id = randomId(), "Designing window treatments"),
                    CategoryWork(id = randomId(), "Providing visualizations of designs"),
                    CategoryWork(id = randomId(), "Selecting artwork and accessories"),
                    CategoryWork(id = randomId(), "Planning layouts and space"),
                    CategoryWork(id = randomId(), "Creating design concept presentations"),
                    CategoryWork(id = randomId(), "Consulting with clients on preferences"),
                    CategoryWork(id = randomId(), "Recommending materials and finishes."),
                )
    )

    fun getItems(): List<Category> {
        return categories
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
        return subCategories[parentCategory] ?: throw IllegalArgumentException()
    }
}

private fun randomId() = UUID.randomUUID().toString()