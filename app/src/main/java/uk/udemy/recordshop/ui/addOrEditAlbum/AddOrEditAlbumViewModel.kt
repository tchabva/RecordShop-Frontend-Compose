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

    // Method for retrieving album artwork from the iTunes URL.
    private suspend fun getItunesAlbum(artist: String, albumTitle: String): String? {
        var artworkUrl: String? = null
        when (val networkResponse =
            itunesRepository.getAlbumArtwork("${artist.trim()} ${albumTitle.trim()}")) {
            is NetworkResponse.Exception -> {
                Log.e(TAG, "${networkResponse.exception}")
                artworkUrl = null
            }

            is NetworkResponse.Failed -> {
                Log.i(TAG, "Code: ${networkResponse.code}, ${networkResponse.message}")
                artworkUrl = null
            }

            is NetworkResponse.Success -> {
                Log.i(TAG, networkResponse.data.toString())
                val itunesAlbumArtist = networkResponse.data.artistName

                artworkUrl =
                    if (itunesAlbumArtist.contains(other = artist.trim(), ignoreCase = true)) {
                        networkResponse.data.artworkUrl100
                    } else {
                        Log.i(
                            TAG,
                            "Artist names do not match:\nInput Artist: $artist \niTunes Artist: $itunesAlbumArtist"
                        )
                        null
                    }
            }
        }
        return artworkUrl
    }

    fun addAlbum() {
        viewModelScope.launch {
            val currentState = _state.value as State.AddAlbum
            if (currentState.title.isNullOrBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter an Album Title!"))
            } else if (currentState.artist.isNullOrBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter an the Artist's name!"))
            } else if (currentState.genre.isNullOrBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter a Genre for the Album"))
            } else if (currentState.releaseDate.isNullOrBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter a valid Release Date!"))
            } else if (currentState.price == null) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter a Price for the Album"))
            } else if (currentState.stock == null) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter the Stock quantity for the album"))
            } else {

                _state.value = currentState.copy(
                    isLoading = true
                )

                val artworkUrl = getItunesAlbum(
                    artist = currentState.artist!!,
                    albumTitle = currentState.title!!
                )
                Log.i(TAG, "Artwork URL: $artworkUrl")

                if (artworkUrl != null) {
                    val updatedURL = artworkUrl.replace(
                        "100x100bb.jpg",
                        "1000x1000bb.jpg"
                    )
                    val album = Album(
                        id = null,
                        title = currentState.title!!.trim(),
                        artist = currentState.artist!!.trim(),
                        genre = currentState.genre!!.trim(),
                        releaseDate = currentState.releaseDate!!,
                        stock = currentState.stock!!,
                        price = currentState.price!!,
                        artworkUrl = updatedURL,
                        dateCreated = null,
                        dateModified = null
                    )
                    Log.i(TAG, "Album with updated Artwork URL: $album")
                    postAlbum(album = album, currentState = currentState)
                } else {
                    val album = Album(
                        id = null,
                        title = currentState.title!!,
                        artist = currentState.artist!!,
                        genre = currentState.genre!!,
                        releaseDate = currentState.releaseDate!!,
                        stock = currentState.stock!!,
                        price = currentState.price!!,
                        artworkUrl = null,
                        dateCreated = null,
                        dateModified = null
                    )
                    Log.i(TAG, "Album with updated Artwork URL: $album")
                    postAlbum(album = album, currentState = currentState)
                }
            }
        }

        Log.i(TAG, "Add Album Button Clicked")
    }

    private suspend fun postAlbum(album: Album, currentState: State) {
        when (val networkResponse = repository.addAlbum(album)) {
            is NetworkResponse.Exception -> {
                _state.value = (currentState as State.AddAlbum).copy(
                    isLoading = false
                )
                emitEvent(
                    Event.AlbumNotAdded(
                        message = networkResponse.exception.message ?: "Unknown Exception Occurred"
                    )
                )
            }

            is NetworkResponse.Failed -> {
                _state.value = (currentState as State.AddAlbum).copy(
                    isLoading = false
                )
                emitEvent(
                    Event.AlbumNotAdded(
                        message = networkResponse.message ?: "Unknown Error Message",
                        responseCode = networkResponse.code
                    )
                )

            }

            is NetworkResponse.Success -> {
                _state.value = (currentState as State.AddAlbum).copy(
                    isLoading = false
                )
                emitEvent(Event.AlbumAdded)
                Log.i(TAG, "Album Successfully Posted: ${networkResponse.data}")
            }
        }
    }

    private suspend fun emitEvent(event: Event) {
        _events.emit(event)
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
            var stock: Int? = null,
            var isLoading: Boolean = false

        ) : State

        data class EditAlbum(val data: Album) : State

        data class Error(val responseCode: Int, val error: String?) : State

        data class NetworkError(val error: String?) : State
    }

    sealed interface Event {
        data object AlbumAdded : Event
        data class AlbumNotAdded(val message: String, val responseCode: Int? = null) : Event
        data class MandatoryTextFieldEmpty(val attribute: String) : Event
    }

    companion object {
        private const val TAG = "AddOrEditAlbumViewModel"
    }
}