package uk.udemy.recordshop.data.repository

import android.app.Application
import uk.udemy.recordshop.R
import uk.udemy.recordshop.data.remote.RecordsApi
import uk.udemy.recordshop.domain.repository.RecordsRepository
import javax.inject.Inject

class RecordsRepositoryImpl @Inject constructor(
    private val api: RecordsApi,
    private val appContext: Application
): RecordsRepository {

    init {
        val appName = appContext.getString(R.string.app_name)
        println("Hello from the repository. The app name is $appName")
    }

    override suspend fun getAllAlbums() {
        TODO("Not yet implemented")
    }
}