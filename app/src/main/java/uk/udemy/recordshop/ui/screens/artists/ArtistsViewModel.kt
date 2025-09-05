package uk.udemy.recordshop.ui.screens.artists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
            // Checks the state prior for proceeding for the PullToRefreshIndicator
            val currentState = _state.value
            _state.value = when (currentState) {
                is State.Loaded -> currentState.copy(isLoading = true)
                else -> State.Loading
            }

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

    fun onArtistItemClicked(artistId: Long){
        viewModelScope.launch {
            emitEvent(
                Event.ArtistItemClicked(artistId)
            )
        }
        Log.i(TAG, "Clicked on Artist with the Id $artistId")
    }

    fun onTryAgainButtonClicked(){
        _state.value = State.Loading
        viewModelScope.launch {
            delay(1000) // To allow time for Progress Indicator to display
            Log.i(TAG, "Try Again Button Clicked")
            getArtists()
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
        data class ArtistItemClicked(val artistId: Long) : Event
    }

    companion object {
        private const val TAG = "ArtistsViewModel"
    }
}