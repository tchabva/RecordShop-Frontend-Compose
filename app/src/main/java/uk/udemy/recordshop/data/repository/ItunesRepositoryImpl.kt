package uk.udemy.recordshop.data.repository

import android.util.Log
import uk.udemy.recordshop.data.model.ItunesAlbum
import uk.udemy.recordshop.data.remote.ItunesApi
import uk.udemy.recordshop.data.remote.NetworkResponse
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
    private val api: ItunesApi
) : ItunesRepository {

    override suspend fun getAlbumArtwork(searchQuery: String): NetworkResponse<ItunesAlbum> {
        try {
            val response = api.getAlbumArtwork(searchQuery)
            val responseCode = response.code()

            if (responseCode == 200) {
                if (response.body()!!.results.isNotEmpty()) {
                    Log.i(TAG, "Result: ${response.body()}")
                    return NetworkResponse.Success(
                        data = response.body()!!.results.first()
                    )

                } else {
                    Log.i(TAG, "No Search Results")
                    return NetworkResponse.Failed(
                        message = "No Search Results",
                        code = responseCode
                    )
                }
            } else {
                Log.e(TAG, "Itunes API Failed")
                return NetworkResponse.Failed(
                    message = response.message() ?: "Something went wrong",
                    code = responseCode
                )
            }

        } catch (e: Throwable) {
            Log.wtf(TAG, "Network Error", e)
            return NetworkResponse.Exception(e)
        }
    }

    companion object {
        private const val TAG = "ItunesRepoImpl"
    }
}