package dev.haroldjose.familysharedlist.domainLayer.extensions

import dev.haroldjose.familysharedlist.domainLayer.models.FamilyListModel
import dev.haroldjose.familysharedlist.domainLayer.models.ProductModel
import kotlinx.datetime.LocalDateTime

class Samples private constructor() {
    class FamilyList private constructor() {
        companion object {
            val nutella: FamilyListModel = nutella()
            val sample1: FamilyListModel = sample1()
            val sample2IsComplete: FamilyListModel = sample2IsComplete()
            val sample3IsPrioritized: FamilyListModel = sample3IsPrioritized()
            val list1: List<FamilyListModel> = list1()
        }
    }
}

private fun nutella(): FamilyListModel = FamilyListModel(
    name = "Nutella",
    isCompleted = true,
    isCompletedDate = LocalDateTime(2024, 4, 14, 0, 0, 0, 0),
    isPrioritized = true,
    quantity = 2,
    price = 1245.2395,
    product = ProductModel(
        code = "3017620422003",
        imageFrontSmallUrl = "https://images.openfoodfacts.net/images/products/301/762/042/2003/front_en.633.200.jpg",
        imageFrontUrl = "https://images.openfoodfacts.net/images/products/301/762/042/2003/front_en.633.400.jpg",
        imageUrl = "https://images.openfoodfacts.net/images/products/301/762/042/2003/front_en.633.400.jpg",
        productName = "Nutella"
    )
)

private fun sample1(): FamilyListModel = FamilyListModel(
    name = "sample1",
    isCompleted = false,
    isCompletedDate = LocalDateTime(2024, 4, 14, 0, 0, 0, 0),
    isPrioritized = false,
    quantity = 1,
    price = 0.0,
    product = null
)

private fun sample2IsComplete() = FamilyListModel(
    name = "sample2",
    isCompleted = true,
    isCompletedDate = LocalDateTime(2024, 4, 13, 0, 0, 0, 0),
    isPrioritized = false,
    quantity = 2,
    price = 0.0,
    product = null
)

private fun sample3IsPrioritized() = FamilyListModel(
    name = "sample3",
    isCompleted = true,
    isCompletedDate = LocalDateTime(2024, 4, 13, 0, 0, 0, 0),
    isPrioritized = true,
    quantity = 3,
    price = 0.0,
    product = null
)

private fun list1(): List<FamilyListModel> = arrayListOf(
    Samples.FamilyList.nutella,
    Samples.FamilyList.sample1,
    Samples.FamilyList.sample2IsComplete,
    Samples.FamilyList.sample3IsPrioritized,
)