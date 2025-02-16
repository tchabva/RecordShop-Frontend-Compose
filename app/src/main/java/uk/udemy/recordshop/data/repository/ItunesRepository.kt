package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.data.model.ItunesAlbum
import uk.udemy.recordshop.data.remote.NetworkResponse

interface ItunesRepository {
    suspend fun getAlbumArtwork(searchQuery: String): NetworkResponse<ItunesAlbum>
}