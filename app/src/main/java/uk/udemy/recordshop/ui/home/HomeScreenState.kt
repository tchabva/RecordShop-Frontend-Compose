package uk.udemy.recordshop.ui.home

import uk.udemy.recordshop.data.model.Album

//open class HomeScreenState{
//
//    data object Loading : HomeScreenState()
//
//    data class Loaded(
//        val list: List<Album> = emptyList(),
//    ) : HomeScreenState()
//
//    data class Error(
//        val error: String? = null,
//        var responseCode: Int? = null
//    ) : HomeScreenState()
//}

data class HomeScreenState(
    val isLoading: Boolean = true,
    val data: List<Album> = emptyList(),
    val error: String? = null
)