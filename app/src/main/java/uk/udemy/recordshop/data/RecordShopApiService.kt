package uk.udemy.recordshop.data

import retrofit2.http.GET
import uk.udemy.recordshop.model.Album

interface AlbumApiService {

    @GET("albums")
    suspend fun getAllInStockAlbums(): List<Album>

}