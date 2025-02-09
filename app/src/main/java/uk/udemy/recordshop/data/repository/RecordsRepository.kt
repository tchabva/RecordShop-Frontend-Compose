package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.data.remote.Result
import uk.udemy.recordshop.data.model.Album

interface RecordsRepository {
    suspend fun getAllAlbums(): Result<List<Album>>
    suspend fun getAlbumById(albumId: Long): Result<Album>
}