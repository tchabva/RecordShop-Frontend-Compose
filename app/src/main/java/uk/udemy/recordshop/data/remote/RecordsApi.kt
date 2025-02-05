package uk.udemy.recordshop.data.remote

import retrofit2.http.GET
import uk.udemy.recordshop.model.Album

interface RecordsApi {

    @GET("albums")
    suspend fun getAllInStockAlbums(): List<Album>

}