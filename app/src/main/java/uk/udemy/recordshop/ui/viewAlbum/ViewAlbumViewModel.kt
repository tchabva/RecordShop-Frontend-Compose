package uk.udemy.recordshop.ui.viewAlbum

import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
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
    private val _viewAlbumScreenState: MutableState<ViewAlbumScreenState> =
        mutableStateOf(ViewAlbumScreenState.Loading)
    val viewAlbumScreenState: State<ViewAlbumScreenState> = _viewAlbumScreenState

    fun getAlbumById(albumId: Long) {
        viewModelScope.launch {
            when (val networkResponse = repository.getAlbumById(albumId)) {
                is NetworkResponse.Exception -> {
                    _viewAlbumScreenState.value =
                        ViewAlbumScreenState.NetworkError(
                            error = networkResponse.exception.message ?: ""
                        )
                }

                is NetworkResponse.Failed -> {
                    _viewAlbumScreenState.value =
                        ViewAlbumScreenState.Error(
                            responseCode = networkResponse.code!!,
                            error = networkResponse.message
                        )
                }

                is NetworkResponse.Success -> {
                    _viewAlbumScreenState.value =
                        ViewAlbumScreenState.Loaded(
                            data = networkResponse.data
                        )
                }
            }
        }
    }

    fun deleteAlbum(albumId: Long) {
        _viewAlbumScreenState.value = ViewAlbumScreenState.Loading

//        viewModelScope.launch {
//            when (val networkResponse = repository.deleteAlbumById(albumId)) {
//                is NetworkResponse.Exception -> {
//                    _viewAlbumScreenState.value =
//                        ViewAlbumScreenState.NetworkError(
//                            error = networkResponse.exception.message ?: ""
//                        )
//                }
//
//                is NetworkResponse.Failed -> {
//                    _viewAlbumScreenState.value =
//                        ViewAlbumScreenState.Error(
//                            responseCode = networkResponse.code!!,
//                            error = networkResponse.message
//                        )
//                }
//
//                is NetworkResponse.Success -> {
//                    _viewAlbumScreenState.value =
//                        ViewAlbumScreenState.AlbumDeleted
//                }
//            }
//        }
        Log.i(TAG, "Deleted Album of ID: $albumId")
    }

    companion object {
        private const val TAG = "ViewAlbumViewModel"
    }
}