package uk.udemy.recordshop.ui.screens.addOrEditAlbum

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
import java.time.DateTimeException
import java.time.LocalDate
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

    private suspend fun emitEvent(event: Event) {
        _events.emit(event)
    }

    // Method for retrieving album artwork from the iTunes URL.
    private suspend fun getItunesAlbum(artist: String, albumTitle: String): String? {
        var artworkUrl: String?
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

    // The addAlbum method that is called when the Add Album Button is selected
    fun addAlbum() {
        viewModelScope.launch {
            val currentState = _state.value as State.AddAlbum

            // TextField Inputs Validation
            if (currentState.title.isNullOrBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter an Album Title!"))
            } else if (currentState.artist.isNullOrBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter an the Artist's name!"))
            } else if (currentState.genre.isNullOrBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter a Genre for the Album"))
            } else if (currentState.releaseDate.isNullOrBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter a Release Date!"))
            } else if (!isReleaseDateValid(currentState.releaseDate!!)) {
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

                // Adapts the URL for a higher resolution image from the Itunes API
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

    // Sends the Album object to the backend for persistence
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

    suspend fun getAlbumById(albumId: Long) {
        _state.value = State.Loading
        Log.i(TAG, "getAlbumById Method Called")

        _state.value = State.Loading
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
                    State.EditAlbum(
                        data = networkResponse.data,
                        unmodifiedAlbum = networkResponse.data.copy(),
                        isLoading = false
                    )
            }
        }
    }

    fun updateAlbum() {
        val currentState = state.value as State.EditAlbum
        val currentAlbum = currentState.data.copy(
            title = currentState.data.title.trim(),
            artist = currentState.data.artist.trim(),
            genre = currentState.data.genre.trim(),
            artworkUrl = currentState.data.artworkUrl?.trim()
        )
        val unmodifiedAlbum = currentState.unmodifiedAlbum

        viewModelScope.launch {
            Log.i(TAG, "Current Album = $currentAlbum")
            Log.i(TAG, "Unmodified Album = $unmodifiedAlbum")

            if (currentAlbum.title.isBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter an Album Title!"))
            } else if (currentAlbum.artist.isBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter an the Artist's name!"))
            } else if (currentAlbum.genre.isBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter a Genre for the Album"))
            } else if (currentAlbum.releaseDate.isBlank()) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter a Release Date!"))
            } else if (!isReleaseDateValid(currentAlbum.releaseDate)) {
                emitEvent(Event.MandatoryTextFieldEmpty("Please enter a valid Release Date!"))
            } else if (currentAlbum == unmodifiedAlbum) {
                emitEvent(Event.AlbumUnchanged)
            } else {
                updateAlbum(currentAlbum)
            }
        }
        Log.i(TAG, "Update Button Clicked")
    }

    // Updates the album using coroutines when invoked
    private suspend fun updateAlbum(updatedAlbum: Album) {
        val albumId = updatedAlbum.id!!
        _state.value = (_state.value as State.EditAlbum).copy(
            isLoading = true
        )
        when (val networkResponse = repository.updateAlbum(
            albumId = albumId,
            updatedAlbum = updatedAlbum
        )) {
            is NetworkResponse.Exception -> {
                _state.value = (_state.value as State.EditAlbum).copy(
                    isLoading = false
                )
                emitEvent(
                    Event.NetworkErrorOccurred(
                        message = networkResponse.exception.message ?: "No Error Message",
                    )
                )
            }

            is NetworkResponse.Failed -> {
                _state.value = (_state.value as State.EditAlbum).copy(
                    isLoading = false
                )
                emitEvent(
                    Event.AlbumUpdateFailed(
                        message = networkResponse.message ?: "No Error Message",
                        responseCode = networkResponse.code
                    )
                )
            }

            is NetworkResponse.Success -> {
                _state.value = State.EditAlbum(
                    data = networkResponse.data,
                    unmodifiedAlbum = networkResponse.data.copy()
                )

                emitEvent(
                    Event.AlbumUpdatedSuccessfully
                )
            }
        }
    }

    // The Validation for the Release Date TextField input
    private fun isReleaseDateValid(releaseDate: String): Boolean {
        return if (releaseDate.matches(Regex("^\\d{4}-\\d{2}-\\d{2}"))) {
            try {
                LocalDate.parse(releaseDate)
                true
            } catch (_: DateTimeException) {
                false
            }
        } else {
            false
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
            var stock: Int? = null,
            var isLoading: Boolean = false

        ) : State

        data class EditAlbum(
            val data: Album,
            val unmodifiedAlbum: Album,
            val isLoading: Boolean = false
        ) : State

        data class Error(val responseCode: Int, val error: String?) : State

        data class NetworkError(val error: String?) : State
    }

    sealed interface Event {
        data object AlbumAdded : Event
        data class AlbumNotAdded(val message: String, val responseCode: Int? = null) : Event
        data class MandatoryTextFieldEmpty(val attribute: String) : Event
        data object AlbumUnchanged : Event
        data object AlbumUpdatedSuccessfully : Event
        data class AlbumUpdateFailed(val message: String, val responseCode: Int? = null) : Event
        data class NetworkErrorOccurred(val message: String) : Event
    }

    companion object {
        private const val TAG = "AddOrEditAlbumViewModel"
    }
}