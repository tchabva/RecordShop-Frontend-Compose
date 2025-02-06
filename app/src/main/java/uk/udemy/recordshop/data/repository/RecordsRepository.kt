package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.R
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.remote.Result
import uk.udemy.recordshop.model.Album

interface RecordsRepository {
    suspend fun getAllAlbums(): Result<List<Album>>
}