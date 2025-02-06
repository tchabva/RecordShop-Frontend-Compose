package uk.udemy.recordshop.data.repository

import android.app.Application
import uk.udemy.recordshop.R
import uk.udemy.recordshop.data.remote.RecordsApi
import uk.udemy.recordshop.data.remote.Result
import uk.udemy.recordshop.model.Album
import javax.inject.Inject

class RecordsRepositoryImpl @Inject constructor(
    private val api: RecordsApi,
    private val appContext: Application
): RecordsRepository {

    init {
        val appName = appContext.getString(R.string.app_name)
        println("Hello from the repository. The app name is $appName")
    }

    override suspend fun getAllAlbums(): Result<List<Album>> {
        try {
            val response = api.getAllAlbums()
            val responseCode = response.code()

            return if(responseCode == 200){
                Result.Success<List<Album>>(response.body()!!)
            }else{
                Result.Failed(
                    response.message() ?: "",
                    code = responseCode,
                )
            }

        }catch (e : Throwable){
            return Result.Exception(e)
        }
    }

//    override suspend fun <List<Album>, ServerErrorResponse>getAllAlbums(): NetworkResponse<List<Album>, ServerErrorResponse> {
//        return try {
//
//            val response = api.getAllAlbums()
//            val responseCode = response.code()
//
//            if (responseCode == 200){
//                NetworkResponse.Success<List<Album>>(response.body()!!)
//            }else{
//                NetworkResponse.Failure<ServerErrorResponse>(
//                    message = response.message(),
//                    code = responseCode
//                )
//            }
//        }catch (e: Throwable){
//            NetworkResponse.Exception(e)
//        }
//    }
}