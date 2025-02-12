package uk.udemy.recordshop.ui.addOrEditAlbum

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import uk.udemy.recordshop.data.model.Album
import uk.udemy.recordshop.data.repository.RecordsRepository
import javax.inject.Inject

@HiltViewModel
class AddOrEditAlbumViewModel @Inject constructor(
    private val repository: RecordsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.AddAlbum)
    val state: StateFlow<State> = _state

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: SharedFlow<Event> = _events

    sealed interface State {
        data object Loading : State

        data object AddAlbum: State

        data class EditAlbum(val data: Album) : State

        data class Error(val responseCode: Int, val error: String?) : State

        data class NetworkError(val error: String?) : State
    }

    sealed interface Event {
        data object AlbumAdded : Event
        data object AlbumNotAdded : Event
    }
}