package uk.udemy.recordshop.data.repository

import android.util.Log
import uk.udemy.recordshop.data.model.GenreAndAlbums
import uk.udemy.recordshop.data.model.GenreDTO
import uk.udemy.recordshop.data.remote.GenresApi
import uk.udemy.recordshop.data.remote.NetworkResponse
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val api: GenresApi
) : GenresRepository {
    override suspend fun getAllGenres(): NetworkResponse<List<GenreDTO>> {
        try {
            val response = api.getAllGenres()
            val responseCode = response.code()

            return if (responseCode == 200) {
                Log.i(
                    TAG,
                    "Successful retrieval of Genres: ${response.body()!!.size} Genres"
                )
                NetworkResponse.Success(data = response.body()!!)
            } else {
                Log.e(TAG, "Failed Retrieval of Genres: Code = $responseCode")
                NetworkResponse.Failed(
                    message = response.message() ?: "",
                    code = responseCode
                )
            }
        } catch (e: Throwable) {
            Log.wtf(TAG, "Network Error", e)
            return NetworkResponse.Exception(exception = e)
        }
    }

    override suspend fun getGenreWithAlbums(genreId: Long): NetworkResponse<GenreAndAlbums> {
        try {
            val response = api.getGenresWithAlbumsById(genreId)
            val responseCode = response.code()

            return if (responseCode == 200) {
                Log.i(TAG, "Successful Genres With Albums Retrieval By ID: ${response.body()}")
                NetworkResponse.Success(response.body()!!)
            }else {
                Log.e(TAG, "Failed Retrieval of Genres with Albums: Code = $responseCode")
                NetworkResponse.Failed(
                    message = response.message() ?: "",
                    code = responseCode
                )
            }
        }catch (e: Throwable) {
            Log.wtf(TAG, "Network Error", e)
            return NetworkResponse.Exception(e)
        }
    }

    companion object {
        private const val TAG = "GenresRepoImpl"
    }
}