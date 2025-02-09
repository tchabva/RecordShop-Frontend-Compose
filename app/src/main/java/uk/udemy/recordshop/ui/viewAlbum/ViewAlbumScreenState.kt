package uk.udemy.recordshop.ui.viewAlbum

import uk.udemy.recordshop.data.model.Album

data class ViewAlbumScreenState(
    val isLoading: Boolean = true,
    val data:Album? = null,
    val error: String? = null
)
