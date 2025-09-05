package uk.udemy.recordshop.data.repository

import android.util.Log
import uk.udemy.recordshop.data.model.ArtistAndAlbums
import uk.udemy.recordshop.data.model.ArtistDTO
import uk.udemy.recordshop.data.remote.ArtistsApi
import uk.udemy.recordshop.data.remote.NetworkResponse
import javax.inject.Inject

class ArtistsRepositoryImpl @Inject constructor(
    private val api: ArtistsApi,
) : ArtistsRepository {

    override suspend fun getAllArtists(): NetworkResponse<List<ArtistDTO>> {
        try {
            val response = api.getAllArtists()
            val responseCode = response.code()

            return if (responseCode == 200) {
                Log.i(TAG, "Successful Retrieval of Artists: ${response.body()!!.size} Artists")
                NetworkResponse.Success(response.body()!!)
            } else {
                Log.e(TAG, "Failed Retrieval of Artists: Code = $responseCode")
                NetworkResponse.Failed(
                    message = response.message() ?: "",
                    code = responseCode
                )
            }
        } catch (e: Throwable) {
            Log.wtf(TAG, "Network Error", e)
            return NetworkResponse.Exception(e)
        }
    }

    override suspend fun getArtistWithAlbums(artistId: Long): NetworkResponse<ArtistAndAlbums> {
        try {
            val response = api.getArtistWithAlbumsById(artistId)
            val responseCode = response.code()

            return if (responseCode == 200) {
                Log.i(TAG, "Successful Artist With Albums Retrieval By ID: ${response.body()}")
                NetworkResponse.Success(response.body()!!)
            } else {
                Log.e(TAG, "Failed Artist With Albums Retrieval By Id: Code = $responseCode")
                NetworkResponse.Failed(
                    response.message() ?: "",
                    code = responseCode,
                )
            }
        } catch (e: Throwable) {
            Log.wtf(TAG, "Network Error", e)
            return NetworkResponse.Exception(e)
        }
    }

    companion object {
        private const val TAG = "ArtistsRepoImpl"
    }
}