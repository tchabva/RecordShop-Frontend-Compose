package uk.udemy.recordshop.model

import java.util.Locale.Category

data class RecordShopState(
    val loading: Boolean = true,
    val list: List<Album> = emptyList(),
    val error: String? = null
)
