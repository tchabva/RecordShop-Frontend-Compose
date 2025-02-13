package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.data.model.ItunesResponse
import uk.udemy.recordshop.data.remote.ItunesApi
import uk.udemy.recordshop.data.remote.NetworkResponse
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
    private val api: ItunesApi
) : ItunesRepository {

    override suspend fun getAlbumArtwork(searchQuery: String): NetworkResponse<ItunesResponse> {
        TODO("Not yet implemented")
    }
}