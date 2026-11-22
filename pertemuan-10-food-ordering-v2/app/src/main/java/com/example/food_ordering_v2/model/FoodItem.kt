package com.example.food_ordering_v2.model

import androidx.annotation.DrawableRes
import com.example.food_ordering_v2.R

enum class Category {
    ALL,
    FOOD,
    DRINK,
    SNACK
}

data class FoodItem(
    val id: String,
    val name: String,
    val category: Category,
    val price: Int,
    val originalPrice: Int? = null,
    val rating: Double,
    val ratingCount: String,
    val description: String,
    val deliveryTimeMinutes: String = "20-30 min",
    val distance: String = "1.5 km",
    val restaurantName: String = "Kuliner Nusantara Express",
    val spicyLevels: List<Int> = listOf(0, 1, 2, 3, 4, 5),
    val discountPercent: Int? = null,
    @DrawableRes val imageResId: Int = R.drawable.martabak_manis
)

data class CartItem(
    val foodItem: FoodItem,
    val quantity: Int,
    val spicyLevel: Int = 0,
    val notes: String = ""
) {
    val totalPrice: Int get() = foodItem.price * quantity
}

object SampleFoodData {
    val items = listOf(
        FoodItem(
            id = "ayam_geprek_sambal_bawang",
            name = "Ayam Geprek Sambal Bawang",
            category = Category.FOOD,
            price = 18000,
            originalPrice = 24000,
            rating = 4.9,
            ratingCount = "12rb+",
            description = "Ayam krispi renyah digeprek dengan ulekan cabai rawit pedas dan bawang gurih beraroma harum, disajikan dengan lalapan segar.",
            deliveryTimeMinutes = "15-25 min",
            distance = "1.8 km",
            spicyLevels = listOf(0, 1, 2, 3, 5, 8, 10),
            discountPercent = 25,
            imageResId = R.drawable.geprek
        ),
        FoodItem(
            id = "martabak_telur_spesial",
            name = "Martabak Telur Daging Sapi",
            category = Category.FOOD,
            price = 35000,
            originalPrice = 45000,
            rating = 4.8,
            ratingCount = "8rb+",
            description = "Martabak telur renyah dengan isian daging sapi cincang gurih melimpah dan daun bawang, lengkap dengan kuah cuka asam manis.",
            deliveryTimeMinutes = "20-30 min",
            distance = "2.4 km",
            spicyLevels = listOf(0, 1, 2, 3),
            discountPercent = 22,
            imageResId = R.drawable.martabak_telur
        ),
        FoodItem(
            id = "batagor_kuah_kacang",
            name = "Batagor Bandung Komplit",
            category = Category.SNACK,
            price = 16000,
            originalPrice = 20000,
            rating = 4.8,
            ratingCount = "6rb+",
            description = "Bakso tahu goreng renyah berbahan ikan tenggiri asli dengan siraman bumbu kacang kental pedas manis dan perasan jeruk limau.",
            deliveryTimeMinutes = "15-20 min",
            distance = "1.5 km",
            spicyLevels = listOf(0, 1, 2, 3),
            discountPercent = 20,
            imageResId = R.drawable.batagor
        ),
        FoodItem(
            id = "martabak_manis_cokelat_keju",
            name = "Martabak Manis Cokelat Keju",
            category = Category.SNACK,
            price = 28000,
            originalPrice = 35000,
            rating = 4.9,
            ratingCount = "15rb+",
            description = "Terang bulan bersarang tebal dan lembut dengan olesan mentega wangi serta limpahan meses cokelat dan keju cheddar parut.",
            deliveryTimeMinutes = "20-35 min",
            distance = "2.4 km",
            spicyLevels = emptyList(),
            discountPercent = 20,
            imageResId = R.drawable.martabak_manis
        ),
        FoodItem(
            id = "es_teler_spesial",
            name = "Es Teler Durian Spesial",
            category = Category.DRINK,
            price = 15000,
            originalPrice = 20000,
            rating = 4.9,
            ratingCount = "10rb+",
            description = "Minuman penutup segar dengan kombinasi alpukat mentega, kelapa muda, nangka manis, jeli, dan kuah santan kental manis harum.",
            deliveryTimeMinutes = "10-20 min",
            distance = "1.2 km",
            spicyLevels = emptyList(),
            discountPercent = 25,
            imageResId = R.drawable.es_teler
        ),
        FoodItem(
            id = "es_teler_original",
            name = "Es Teler Alpukat Kelapa",
            category = Category.DRINK,
            price = 12000,
            rating = 4.8,
            ratingCount = "9rb+",
            description = "Es teler klasik dengan potongan alpukat segar, serutan kelapa muda gurih, dan sirup istimewa pelepas dahaga.",
            deliveryTimeMinutes = "10-15 min",
            distance = "1.2 km",
            spicyLevels = emptyList(),
            imageResId = R.drawable.es_teler
        ),
        FoodItem(
            id = "batagor_kering_crispy",
            name = "Batagor Goreng Crispy Siomay",
            category = Category.SNACK,
            price = 14000,
            rating = 4.7,
            ratingCount = "4rb+",
            description = "Pangsit goreng batagor renyah disajikan dengan cocolan sambal kacang gurih pedas manis.",
            deliveryTimeMinutes = "15-20 min",
            distance = "1.5 km",
            spicyLevels = listOf(0, 1, 2),
            imageResId = R.drawable.batagor
        ),
        FoodItem(
            id = "ayam_geprek_keju_mozarella",
            name = "Ayam Geprek Leleh Mozarella",
            category = Category.FOOD,
            price = 23000,
            rating = 4.9,
            ratingCount = "14rb+",
            description = "Ayam geprek pedas gurih dengan topping keju mozzarella torch yang meleleh lumer memanjakan lidah.",
            deliveryTimeMinutes = "20-30 min",
            distance = "1.8 km",
            spicyLevels = listOf(0, 1, 2, 3, 5),
            imageResId = R.drawable.geprek
        )
    )
}
