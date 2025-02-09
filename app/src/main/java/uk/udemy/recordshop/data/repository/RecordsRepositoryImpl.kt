package uk.udemy.recordshop.data.repository

import android.util.Log
import uk.udemy.recordshop.data.remote.RecordsApi
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.model.Album
import javax.inject.Inject

class RecordsRepositoryImpl @Inject constructor(
    private val api: RecordsApi,
): RecordsRepository {

    override suspend fun getAllAlbums(): NetworkResponse<List<Album>> {
        try {
            val response = api.getAllAlbums()
            val responseCode = response.code()

            return if(responseCode == 200){
                Log.i(TAG, "Successful Retrieval of Albums: ${response.body()!!.size} Albums")
                NetworkResponse.Success(response.body()!!)
            }else{
                Log.e(TAG, "Failed Retrieval of Albums: Code = $responseCode")
                NetworkResponse.Failed(
                    response.message() ?: "",
                    code = responseCode,
                )
            }
        }catch (e : Throwable){
            Log.wtf(TAG, "Network Error", e)
            return NetworkResponse.Exception(e)
        }
    }

    override suspend fun getAlbumById(albumId: Long): NetworkResponse<Album> {
        try {
            val response = api.getAlbumById(albumId)
            val responseCode = response.code()

            return if(responseCode == 200){
                Log.i(TAG, "Successful Album Retrieval By ID: ${response.body()}")
                NetworkResponse.Success(response.body()!!)
            }else{
                Log.e(TAG, "Failed Album Retrieval By Id: Code = $responseCode")
                NetworkResponse.Failed(
                    response.message() ?: "",
                    code = responseCode,
                )
            }

        }catch (e : Throwable){
            Log.wtf(TAG, "Network Error", e)
            return NetworkResponse.Exception(e)
        }
    }

    companion object {
        private const val TAG = "RecordsRepoImpl"
    }
}