package uk.udemy.recordshop.data.repository

import uk.udemy.recordshop.data.model.GenreAndAlbums
import uk.udemy.recordshop.data.model.GenreDTO
import uk.udemy.recordshop.data.remote.NetworkResponse

interface GenresRepository {
    suspend fun getAllGenres(): NetworkResponse<List<GenreDTO>>
    suspend fun getGenreWithAlbums(genreId: Long): NetworkResponse<GenreAndAlbums>
}