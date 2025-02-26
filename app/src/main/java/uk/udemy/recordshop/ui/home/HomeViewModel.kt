package uk.udemy.recordshop.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.udemy.recordshop.data.model.Album
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.repository.RecordsRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecordsRepository
) : ViewModel() {

//    private val _homeScreenState = mutableStateOf(HomeScreenState())
//    val homeScreenState: State<HomeScreenState> = _homeScreenState

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    init {
        getAlbums()
    }

    fun getAlbums() {
        viewModelScope.launch {
//            _homeScreenState.value = HomeScreenState()
            _state.value = State.Loading
            when (val networkResponse = repository.getAllAlbums()) {

                is NetworkResponse.Exception -> {
//                    _homeScreenState.value = _homeScreenState.value.copy(
//                        isLoading = false,
//                        error = networkResponse.exception.message
//                    )
                    _state.value = State.NetworkError(
                        errorMessage = networkResponse.exception.message ?: "Unknown Exception"
                    )
                }

                is NetworkResponse.Failed -> {
//                    _homeScreenState.value = _homeScreenState.value.copy(
//                        isLoading = false,
//                        error = networkResponse.message
//                    )
                    _state.value = State.Error(
                        responseCode = networkResponse.code,
                        errorMessage = networkResponse.message ?: "Unknown Error"
                    )
                }

                is NetworkResponse.Success -> {
//                    _homeScreenState.value = _homeScreenState.value.copy(
//                        isLoading = false,
//                        data = networkResponse.data
//                    )
                    _state.value = State.Loaded(
                        data = networkResponse.data,
                    )
                }
            }
        }
    }

    fun navigateToAlbumDetail(albumId: Long) {
        Log.i(TAG, "Clicked on Album with the Id $albumId")
    }

    sealed interface State {
        data object Loading : State

        data class Loaded(
            val data: List<Album> = emptyList(),
            val isLoading: Boolean = false
        ) : State

        data class Error(val responseCode: Int?, val errorMessage: String) : State

        data class NetworkError(val errorMessage: String) : State
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}