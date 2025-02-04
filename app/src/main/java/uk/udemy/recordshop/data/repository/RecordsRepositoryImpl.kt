package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.data.remote.RecordsApiService
import uk.udemy.recordshop.domain.repository.RecordsRepository

class RecordsRepositoryImpl(
    private val api: RecordsApiService
): RecordsRepository {

    override suspend fun getAllAlbums() {
        TODO("Not yet implemented")
    }
}