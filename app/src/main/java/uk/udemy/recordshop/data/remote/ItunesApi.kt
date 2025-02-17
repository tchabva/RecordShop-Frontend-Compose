package uk.udemy.recordshop.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uk.udemy.recordshop.data.model.ItunesResponse

interface ItunesApi {

    @GET("search?entity=album&country=gb&limit=1")
    suspend fun getAlbumArtwork(@Query("term") searchQuery: String): Response<ItunesResponse>
}