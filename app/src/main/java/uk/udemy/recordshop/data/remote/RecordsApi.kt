package uk.udemy.recordshop.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import uk.udemy.recordshop.data.model.Album

interface RecordsApi {

    @GET("albums")
    suspend fun getAllAlbums(): Response<List<Album>>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") albumId: Long): Response<Album>

    @DELETE("albums/{id}")
    suspend fun deleteAlbumById(@Path("id") albumId: Long): Response<Void>

    @POST("albums/add")
    suspend fun addAlbum(@Body album: Album): Response<Album>

    @PUT("albums/{id}")
    suspend fun updateAlbum(@Path("id") albumId: Long, @Body updatedAlbum: Album): Response<Album>
}