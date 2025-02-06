package uk.udemy.recordshop.data.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import uk.udemy.recordshop.model.Album

interface RecordsApi {

    @GET("albums")
    suspend fun getAllAlbums(): Response<List<Album>>

}