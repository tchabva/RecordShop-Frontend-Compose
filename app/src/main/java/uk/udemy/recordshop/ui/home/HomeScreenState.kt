package uk.udemy.recordshop.ui.home

import uk.udemy.recordshop.data.model.Album

data class HomeScreenState(
    val isLoading: Boolean = true,
    val data: List<Album> = emptyList(),
    val error: String? = null
)