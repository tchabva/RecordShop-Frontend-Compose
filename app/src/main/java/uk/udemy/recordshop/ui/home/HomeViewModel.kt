package uk.udemy.recordshop.ui.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.repository.RecordsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecordsRepository
) : ViewModel() {

    private val _homeScreenState = mutableStateOf(HomeScreenState())
    val homeScreenState: State<HomeScreenState> = _homeScreenState

    init {
        getAlbums()
    }


    fun getAlbums(){
        viewModelScope.launch {
            _homeScreenState.value = HomeScreenState()
            when (val networkResponse = repository.getAllAlbums()){

                is NetworkResponse.Exception -> {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        isLoading = false,
                        error = networkResponse.exception.message
                    )

                }
                is NetworkResponse.Failed -> {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        isLoading = false,
                        error = networkResponse.message
                        )
                }
                is NetworkResponse.Success -> {
                    _homeScreenState.value =  _homeScreenState.value.copy(
                        isLoading = false,
                        data = networkResponse.data
                    )
                }
            }
        }
    }

    fun navigateToAlbumDetail(albumId: Long){
        Log.i(TAG, "Clicked on Album with the Id $albumId")
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}