package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.model.Album

interface RecordsRepository {
    suspend fun getAllAlbums(): NetworkResponse<List<Album>>
    suspend fun getAlbumById(albumId: Long): NetworkResponse<Album>
    suspend fun deleteAlbumById(albumId: Long): NetworkResponse<Unit>
    suspend fun addAlbum(album: Album): NetworkResponse<Album>
}