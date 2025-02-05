package uk.udemy.recordshop.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.udemy.recordshop.data.repository.RecordsRepositoryImpl
import uk.udemy.recordshop.domain.repository.RecordsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecordsRepository(
        recordsRepositoryImpl: RecordsRepositoryImpl
    ): RecordsRepository
}