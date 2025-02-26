package uk.udemy.recordshop.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: SharedFlow<Event> = _events

    init {
        getAlbums()
    }

    fun getAlbums() {
        viewModelScope.launch {
            _state.value = State.Loading
            when (val networkResponse = repository.getAllAlbums()) {

                is NetworkResponse.Exception -> {
                    _state.value = State.NetworkError(
                        errorMessage = networkResponse.exception.message ?: "Unknown Exception"
                    )
                }

                is NetworkResponse.Failed -> {
                    _state.value = State.Error(
                        responseCode = networkResponse.code,
                        errorMessage = networkResponse.message ?: "Unknown Error"
                    )
                }

                is NetworkResponse.Success -> {
                    _state.value = State.Loaded(
                        data = networkResponse.data,
                    )
                }
            }
        }
    }

    private suspend fun emitEvent(event: Event) {
        _events.emit(event)
    }

    fun addAlbum(){
        viewModelScope.launch {
            emitEvent(
                Event.AddAlbumClicked
            )
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

    sealed interface Event{
        data object AddAlbumClicked : Event
        data class AlbumItemClicked(val albumId: Long) : Event
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}