package uk.udemy.recordshop.ui.screens.genres

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
import uk.udemy.recordshop.data.model.GenreDTO
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.repository.GenresRepository
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val repository: GenresRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: SharedFlow<Event> = _events

    private suspend fun emitEvent(event: Event) {
        _events.emit(event)
    }

    init {
        getGenres()
    }

    fun getGenres() {
        viewModelScope.launch {
            // Checks the state prior to proceeding with the PullToRefreshIndicator
            val currentState = _state.value
            _state.value = when (currentState) {
                is State.Loaded -> currentState.copy(isLoading = true)
                else -> State.Loading
            }

            when (val networkResponse = repository.getAllGenres()) {
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
                        data = networkResponse.data
                    )
                }
            }
        }
    }

    fun onGenreItemClicked(genreId: Long) {
        viewModelScope.launch {
            emitEvent(
                Event.GenreItemClicked(genreId)
            )
        }
        Log.i(TAG, "Clicked on Genre with the ID: $genreId")
    }

    fun onTryAgainButtonClicked() {
        _state.value = State.Loading
        viewModelScope.launch {
            delay(400) // To allow time for Progress Indicator to display
            Log.i(TAG, "Try Again Button Clicked")
            getGenres()
        }
    }

    sealed interface State {
        data object Loading : State

        data class Loaded(
            val data: List<GenreDTO> = emptyList(),
            val isLoading: Boolean = false
        ) : State

        data class Error(val responseCode: Int?, val errorMessage: String) : State

        data class NetworkError(val errorMessage: String) : State
    }

    sealed interface Event {
        data class GenreItemClicked(val genreId: Long) : Event
    }

    companion object {
        private const val TAG = "GenresViewModel"
    }
}