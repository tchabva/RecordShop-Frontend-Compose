package uk.udemy.recordshop.ui.viewAlbum

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
class ViewAlbumViewModel @Inject constructor(
    private val repository: RecordsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State> = _state

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: SharedFlow<Event> = _events

    // Retrieves the Album from the Backend using the Album Id
    suspend fun getAlbumById(albumId: Long) {
        when (val networkResponse = repository.getAlbumById(albumId)) {
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

    fun deleteAlbum(albumId: Long) {
        _state.value = (state.value as State.Loaded).copy(
            isLoading = true
        )

        viewModelScope.launch {
            when (val networkResponse = repository.deleteAlbumById(albumId)) {
                is NetworkResponse.Exception -> {
                    _state.value = (state.value as State.Loaded).copy(
                        isLoading = false
                    )

                    emitEvent(
                        Event.DeleteAlbumFailed(
                            responseCode = null,
                            error = networkResponse.exception.message ?: "Unknown Error"
                        )
                    )
                }

                is NetworkResponse.Failed -> {
                    _state.value = (state.value as State.Loaded).copy(
                        isLoading = false
                    )

                    emitEvent(
                        Event.DeleteAlbumFailed(
                            responseCode = networkResponse.code!!,
                            error = networkResponse.message ?: "Unknown Error"
                        )
                    )
                }

                is NetworkResponse.Success -> {
                    emitEvent(Event.DeleteAlbumSuccessful)
                    Log.i(TAG, "Deleted Album of ID: $albumId")
                }
            }
        }
    }

    fun showDeleteAlbumDialog(){
        _state.value = (state.value as State.Loaded).copy(
            showDeleteAlbumDialog = true
        )
    }

    fun dismissDeleteAlbumDialog(){
        _state.value = (state.value as State.Loaded).copy(
            showDeleteAlbumDialog = false
        )
    }

    private suspend fun emitEvent(event: Event) {
        _events.emit(event)
    }

    sealed interface State {
        data object Loading : State

        data class Loaded(
            val data: Album,
            var isLoading: Boolean = false,
            var showDeleteAlbumDialog: Boolean = false
        ) : State

        data class Error(val responseCode: Int, val error: String?) : State

        data class NetworkError(val error: String?) : State
    }

    sealed interface Event {
        data class EditAlbumFabClicked(val albumId: Long) : Event
        data object DeleteAlbumSuccessful : Event
        data class DeleteAlbumFailed(val responseCode: Int?, val error: String) : Event
    }

    companion object {
        private const val TAG = "ViewAlbumViewModel"
    }
}