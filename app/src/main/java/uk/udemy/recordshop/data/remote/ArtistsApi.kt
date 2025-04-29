package uk.udemy.recordshop.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uk.udemy.recordshop.data.model.ArtistAndAlbums
import uk.udemy.recordshop.data.model.ArtistDTO

interface ArtistsApi {

    @GET("artists")
    suspend fun getAllArtists(): Response<List<ArtistDTO>>

    @GET("artists/{id}")
    suspend fun getArtistWithAlbumsById(@Path("id") artistId: Long): Response<ArtistAndAlbums>
}