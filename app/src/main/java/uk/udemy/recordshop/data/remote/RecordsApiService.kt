package uk.udemy.recordshop.data.remote

import retrofit2.http.GET
import uk.udemy.recordshop.model.Album

interface RecordsApiService {

    @GET("albums")
    suspend fun getAllInStockAlbums(): List<Album>

}