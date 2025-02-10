package uk.udemy.recordshop.ui.viewAlbum

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.udemy.recordshop.data.remote.NetworkResponse
import uk.udemy.recordshop.data.repository.RecordsRepository
import javax.inject.Inject

@HiltViewModel
class ViewAlbumViewModel @Inject constructor(
    private val repository: RecordsRepository
) : ViewModel() {
    private val _viewAlbumScreenState = mutableStateOf(ViewAlbumScreenState())
    val viewAlbumScreenState: State<ViewAlbumScreenState> = _viewAlbumScreenState

    fun getAlbumById(albumId: Long) {
        viewModelScope.launch {
            when (val networkResponse = repository.getAlbumById(albumId)) {
                is NetworkResponse.Exception -> {
                    _viewAlbumScreenState.value = _viewAlbumScreenState.value.copy(
                        isLoading = false,
                        error = networkResponse.exception.message
                    )
                }

                is NetworkResponse.Failed -> {
                    _viewAlbumScreenState.value = _viewAlbumScreenState.value.copy(
                        isLoading = false,
                        error = networkResponse.message
                    )
                }

                is NetworkResponse.Success -> {
                    _viewAlbumScreenState.value = _viewAlbumScreenState.value.copy(
                        isLoading = false,
                        data = networkResponse.data
                    )
                }
            }
        }
    }

    companion object{
        private const val TAG = "ViewAlbumViewModel"
    }
}