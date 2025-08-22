package uk.udemy.recordshop.ui.screens.artist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.udemy.recordshop.data.model.ArtistAndAlbums
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.repository.ArtistsRepository
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val repository: ArtistsRepository
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: SharedFlow<Event> = _events

    // Retrieves the Artist and their Albums from the Backend using the Artist Id
    suspend fun getArtistWithAlbums(artistId: Long) {
        when (val networkResponse = repository.getArtistWithAlbums(artistId)) {
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

    private suspend fun emitEvent(event: Event) {
        _events.emit(event)
    }

    sealed interface State {
        data object Loading : State

        data class Loaded(
            val data: ArtistAndAlbums,
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
        private const val TAG = "ArtistViewModel"
    }
}