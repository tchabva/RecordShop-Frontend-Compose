package uk.udemy.recordshop.ui.screens.genre

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.udemy.recordshop.data.model.GenreAndAlbums
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.repository.GenresRepository
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repository: GenresRepository
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: SharedFlow<Event> = _events

    private suspend fun emitEvent(event: Event) {
        _events.emit(event)
    }

    // Retrieves the Genre and its Albums from the Backend using the GenreId
    suspend fun getGenreWithAlbums(genreId: Long) {
        when (val networkResponse = repository.getGenreWithAlbums(genreId)) {
            is NetworkResponse.Exception -> {
                _state.value =
                    State.NetworkError(
                        error = networkResponse.exception.message ?: ""
                    )
            }

            is NetworkResponse.Failed -> {
                _state.value =
                    State.Error(
                        responseCode = networkResponse.code!!,
                        error = networkResponse.message
                    )
            }

            is NetworkResponse.Success -> {
                _state.value =
                    State.Loaded(
                        data = networkResponse.data
                    )
            }
        }
    }

    fun onAlbumItemClicked(albumId: Long) {
        viewModelScope.launch {
            emitEvent(
                Event.AlbumItemClicked(
                    albumId = albumId
                )
            )
        }
        Log.i(TAG, "Clicked on Album with the Id $albumId")
    }

    sealed interface State {
        data object Loading : State

        data class Loaded(
            val data: GenreAndAlbums,
            var isLoading: Boolean = false,
            var showDeleteAlbumDialog: Boolean = false
        ) : State

        data class Error(val responseCode: Int, val error: String?) : State

        data class NetworkError(val error: String?) : State
    }

    sealed interface Event {
        data class AlbumItemClicked(val albumId: Long) : Event
    }

    companion object {
        private const val TAG = "GenreViewModel"
    }
}