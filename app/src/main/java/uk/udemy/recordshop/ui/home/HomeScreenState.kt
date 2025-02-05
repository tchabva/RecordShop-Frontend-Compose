package uk.udemy.recordshop.ui.home

import uk.udemy.recordshop.model.Album

sealed interface HomeScreenState{

    data object Loading : HomeScreenState

    data class Loaded(
        val list: List<Album> = emptyList(),
    ) : HomeScreenState

    data class Error(
        val error: String? = null,
        var responseCode: Int? = null
    ) : HomeScreenState
}