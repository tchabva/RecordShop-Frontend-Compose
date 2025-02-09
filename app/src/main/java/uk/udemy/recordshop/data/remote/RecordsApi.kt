package uk.udemy.recordshop.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uk.udemy.recordshop.data.model.Album

interface RecordsApi {

    @GET("albums")
    suspend fun getAllAlbums(): Response<List<Album>>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") albumId: Long): Response<Album>

}