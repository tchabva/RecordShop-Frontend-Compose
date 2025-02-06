package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.data.remote.NetworkResponse

interface RecordsRepository {
    suspend fun <Data>getAllAlbums(): NetworkResponse<Data>
}