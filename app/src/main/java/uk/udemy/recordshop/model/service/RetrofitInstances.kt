package uk.udemy.recordshop.model.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_ALBUMS = "http://192.168.50.167:8080/api/v1/"
private const val BASE_URL_ITUNES = "https://itunes.apple.com/"


private val interceptorAlbums = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val clientAlbums = OkHttpClient.Builder().addInterceptor(interceptorAlbums).build()

private val retrofitAlbums = Retrofit
    .Builder()
    .baseUrl(BASE_URL_ALBUMS)
    .addConverterFactory(GsonConverterFactory.create())
    .client(clientAlbums)
    .build();

val albumService = retrofitAlbums.create(AlbumApiService::class.java)