package uk.udemy.recordshop.data

import retrofit2.http.GET
import uk.udemy.recordshop.model.Album

interface RecordShopApiService {

    @GET("albums")
    suspend fun getAllInStockAlbums(): List<Album>

}