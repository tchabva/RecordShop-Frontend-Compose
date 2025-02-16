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
                    if (itunesAlbumArtist.lowercase().contains(Regex(artist.lowercase()))) {
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
//                val artistName = currentState.artist!!.trim()
//                val albumTitle = currentState.title!!.trim()
//
//                when (val networkResponse =
//                    itunesRepository.getAlbumArtwork("$artistName $albumTitle")) {
//                    is NetworkResponse.Exception -> {
//                        TODO()
//                    }
//
//                    is NetworkResponse.Failed -> {
//                        TODO()
//                    }
//
//                    is NetworkResponse.Success -> {
//                        Log.i(TAG, networkResponse.data.toString())
//                    }
//                }
                val artworkUrl = getItunesAlbum(
                    artist = currentState.artist!!,
                    albumTitle = currentState.title!!
                )
                Log.i(TAG, "Artwork URL: $artworkUrl")
            }
        }

        Log.i(TAG, "Add Album Button Clicked")
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
            var stock: Int? = null

        ) : State

        data class EditAlbum(val data: Album) : State

        data class Error(val responseCode: Int, val error: String?) : State

        data class NetworkError(val error: String?) : State
    }

    sealed interface Event {
        data object AlbumAdded : Event
        data object AlbumNotAdded : Event
        data class MandatoryTextFieldEmpty(val attribute: String) : Event
    }

    companion object {
        private const val TAG = "AddOrEditAlbumViewModel"
    }
}