package uk.udemy.recordshop.ui.addOrEditAlbum

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
import uk.udemy.recordshop.data.repository.ItunesRepository
import uk.udemy.recordshop.data.repository.RecordsRepository
import javax.inject.Inject

@HiltViewModel
class AddOrEditAlbumViewModel @Inject constructor(
    private val repository: RecordsRepository,
    private val itunesRepository: ItunesRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.AddAlbum())
    val state: StateFlow<State> = _state

    private val _events: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: SharedFlow<Event> = _events

    init {
        getItunesAlbum("TC-EP Daily")
    }

    private fun getItunesAlbum(searchQuery: String) {
        viewModelScope.launch {
            when (val networkResponse = itunesRepository.getAlbumArtwork(searchQuery)) {
                is NetworkResponse.Exception -> {
                    TODO()
                }
                is NetworkResponse.Failed -> {
                    TODO()
                }
                is NetworkResponse.Success -> {
                    Log.i(TAG, networkResponse.data.toString())
                }
            }

        }
    }

    sealed interface State {
        data object Loading : State

        data class AddAlbum(
            var title: String? = null,
            var artist: String? = null,
            var genre: String? = null,
            var releaseDate: String? = null,
            var artworkUrl: String? = null,
            var price: Double? = null,
            var stock: Int? = null

        ) : State

        data class EditAlbum(val data: Album) : State

        data class Error(val responseCode: Int, val error: String?) : State

        data class NetworkError(val error: String?) : State
    }

    sealed interface Event {
        data object AlbumAdded : Event
        data object AlbumNotAdded : Event
    }

    companion object {
        private const val TAG = "AddOrEditAlbumViewModel"
    }
}