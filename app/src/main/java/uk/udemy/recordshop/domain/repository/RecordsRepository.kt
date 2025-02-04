package uk.udemy.recordshop.domain.repository

interface RecordsRepository {
    suspend fun getAllAlbums()
}