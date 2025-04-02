package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.data.model.ArtistAndAlbums
import uk.udemy.recordshop.data.model.ArtistDTO
import uk.udemy.recordshop.data.remote.NetworkResponse

interface ArtistsRepository {
    suspend fun getAllArtists(): NetworkResponse<List<ArtistDTO>>
    suspend fun getArtistWithAlbums(artistId: Long): NetworkResponse<ArtistAndAlbums>
}