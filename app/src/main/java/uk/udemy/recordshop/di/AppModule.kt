package uk.udemy.recordshop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.udemy.recordshop.data.remote.ArtistsApi
import uk.udemy.recordshop.data.remote.ItunesApi
import uk.udemy.recordshop.data.remote.RecordsApi
import javax.inject.Singleton

/*
Allows for creation of singletons for the APIs which can then be injected where they are required
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL_RECORDS = "http://192.168.50.167:8080/api/v1/"
    //    private const val BASE_URL_RECORDS = "http://10.0.2.2:8080/api/v1/"
    private const val BASE_URL_ITUNES = "https://itunes.apple.com/"
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideRecordsApi(): RecordsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL_RECORDS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(RecordsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesItunesApi(): ItunesApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL_ITUNES)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ItunesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesArtistsApi(): ArtistsApi{
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL_RECORDS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ArtistsApi::class.java)
    }
}