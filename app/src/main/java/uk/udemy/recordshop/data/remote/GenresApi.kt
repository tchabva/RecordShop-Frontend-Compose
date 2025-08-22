package uk.udemy.recordshop.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uk.udemy.recordshop.data.model.GenreAndAlbums
import uk.udemy.recordshop.data.model.GenreDTO

interface GenresApi {
    @GET("genres")
    suspend fun getAllGenres(): Response<List<GenreDTO>>

    @GET("genres/{id}")
    suspend fun getGenresWithAlbumsById(@Path("id") genreId: Long): Response<GenreAndAlbums>
}