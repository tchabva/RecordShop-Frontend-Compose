package uk.udemy.recordshop.ui.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.udemy.recordshop.data.model.ArtistDTO
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.repository.ArtistsRepository
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val repository: ArtistsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: SharedFlow<Event> = _events

    init {
        getArtists()
    }

    fun getArtists(){
        viewModelScope.launch {
            _state.value = State.Loading
            when(val networkResponse = repository.getAllArtists()){
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

    private suspend fun emitEvent(event: Event){
        _events.emit(event)
    }

    fun onArtistItemClicked(albumId: Long){
        viewModelScope.launch {
            emitEvent(
                Event.ArtistItemClicked(albumId)
            )
        }
    }

    sealed interface State {
        data object Loading : State

        data class Loaded(
            val data: List<ArtistDTO> = emptyList(),
            val isLoading: Boolean = false
        ) : State

        data class Error(val responseCode: Int?, val errorMessage: String) : State

        data class NetworkError(val errorMessage: String) : State
    }

    sealed interface Event{
        data class ArtistItemClicked(val albumId: Long) : Event
    }

    companion object {
        private const val TAG = "ArtistsViewModel"
    }
}