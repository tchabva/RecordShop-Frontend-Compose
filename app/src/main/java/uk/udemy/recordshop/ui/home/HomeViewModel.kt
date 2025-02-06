package uk.udemy.recordshop.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.udemy.recordshop.data.remote.Result
import uk.udemy.recordshop.data.repository.RecordsRepository
import uk.udemy.recordshop.model.Album
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


    private fun getAlbums(){
        viewModelScope.launch {

//            try {
//                val response = albumService.getAllAlbums()
//                _recordShopState.value = _recordShopState.value.copy(
//                    loading = false,
//                    list = response.body() ?: emptyList(),
//                    error = null
//                )
//            }catch (e: Exception){
//                _recordShopState.value = _recordShopState.value.copy(
//                    loading = false,
//                    error = "Error Loading albums ${e.message}"
//                )
//            }
            when (val networkResponse = repository.getAllAlbums()){
//                is NetworkResponse.Exception -> {
//                    _homeScreenState.value = HomeScreenState.Error()
//                }
//                is NetworkResponse.Failure -> {
//                    _homeScreenState.value = HomeScreenState.Error()
//                }
//                is NetworkResponse.Success -> {
//                    _homeScreenState.value = HomeScreenState.Loaded(networkResponse.data)
//                }

                is Result.Exception -> {
                    _homeScreenState.value = _homeScreenState.value.copy(isLoading = false)
                }
                is Result.Failed -> {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        isLoading = false,
                        error = networkResponse.message
                        )
                }
                is Result.Success -> {
                    _homeScreenState.value =  _homeScreenState.value.copy(
                        isLoading = false,
                        data = networkResponse.data
                    )
                }
            }
        }
    }
}