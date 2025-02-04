package uk.udemy.recordshop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.udemy.recordshop.model.RecordShopState
import uk.udemy.recordshop.data.albumService

class MainViewModel : ViewModel() {

    private val _recordShopState = mutableStateOf(RecordShopState())
    val recordShopState: State<RecordShopState> = _recordShopState

    init {
        getAlbums()
    }

    private fun getAlbums(){
        viewModelScope.launch {

            try {
                val response = albumService.getAllInStockAlbums()
                _recordShopState.value = _recordShopState.value.copy(
                    loading = false,
                    list = response,
                    error = null
                )
            }catch (e: Exception){
                _recordShopState.value = _recordShopState.value.copy(
                    loading = false,
                    error = "Error Loadining albums ${e.message}"
                )
            }
        }
    }
}