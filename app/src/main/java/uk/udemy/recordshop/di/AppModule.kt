package uk.udemy.recordshop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.udemy.recordshop.data.remote.RecordsApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL_RECORDS = "http://192.168.50.167:8080/api/v1/"
    private val interceptorAlbums = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val clientAlbums = OkHttpClient.Builder().addInterceptor(interceptorAlbums).build()


    @Provides
    @Singleton
    fun provideRecordsApi(): RecordsApiService {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL_RECORDS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientAlbums)
            .build()
            .create(RecordsApiService::class.java)
    }

//    @Provides
//    @Singleton
//    fun providesRecordsRepository(api: RecordsApiService, app: Application): RecordsRepository{
//        return RecordsRepositoryImpl(api, app)
//    }
}