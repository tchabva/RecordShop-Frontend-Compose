package uk.udemy.recordshop.ui.viewAlbum

import uk.udemy.recordshop.data.model.Album

sealed interface ViewAlbumScreenState {
    data object Loading : ViewAlbumScreenState

    data class Loaded(val data: Album) : ViewAlbumScreenState

    data class Error(val responseCode: Int, val error: String?) : ViewAlbumScreenState

    data class NetworkError(val error: String?) : ViewAlbumScreenState

    data object AlbumDeleted : ViewAlbumScreenState

}