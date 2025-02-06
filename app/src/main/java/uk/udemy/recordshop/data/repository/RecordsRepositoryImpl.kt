package uk.udemy.recordshop.data.repository

import android.util.Log
import uk.udemy.recordshop.data.remote.RecordsApi
import uk.udemy.recordshop.data.remote.Result
import uk.udemy.recordshop.data.model.Album
import javax.inject.Inject

class RecordsRepositoryImpl @Inject constructor(
    private val api: RecordsApi,
): RecordsRepository {

    override suspend fun getAllAlbums(): Result<List<Album>> {
        try {
            val response = api.getAllAlbums()
            val responseCode = response.code()

            return if(responseCode == 200){
                Log.i(TAG, "Successful Album Retrieval: ${response.body()!!.size} Albums")
                Result.Success(response.body()!!)
            }else{
                Log.e(TAG, "Failed Album Retrieval: Code = $responseCode")
                Result.Failed(
                    response.message() ?: "",
                    code = responseCode,
                )
            }
        }catch (e : Throwable){
            Log.wtf(TAG, "Network Error", e)
            return Result.Exception(e)
        }
    }

    companion object {
        private const val TAG = "RecordsRepoImpl"
    }
}