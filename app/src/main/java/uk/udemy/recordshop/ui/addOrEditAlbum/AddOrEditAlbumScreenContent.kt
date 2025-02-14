package uk.udemy.recordshop.ui.addOrEditAlbum

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import java.util.Locale


@Composable
fun AddOrEditAlbumScreenContent(
    state: AddOrEditAlbumViewModel.State,
) {

    when (state) {
        is AddOrEditAlbumViewModel.State.EditAlbum -> {
            TODO()
        }

        is AddOrEditAlbumViewModel.State.Error -> {
            TODO()
        }

        AddOrEditAlbumViewModel.State.Loading -> {
            TODO()
        }

        is AddOrEditAlbumViewModel.State.NetworkError -> {
            TODO()
        }

        is AddOrEditAlbumViewModel.State.AddAlbum -> {
            val title = remember { mutableStateOf(state.title) }
            val artist = remember { mutableStateOf(state.artist) }
            val genre = remember { mutableStateOf(state.genre) }
            val releaseDate = remember { mutableStateOf(state.releaseDate) }
            val artworkUrl = remember { mutableStateOf(state.artworkUrl) }
            val price = remember { mutableStateOf(state.price?.toString() ?: "") }
            val stock = remember { mutableStateOf(state.stock?.toString() ?: "") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Title TextField
                OutlinedTextField(
                    value = title.value ?: "",
                    onValueChange = {
                        title.value = it
                        state.title = title.value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    label = { Text("Title") }
                )
                // Artist TextField
                OutlinedTextField(
                    value = artist.value ?: "",
                    onValueChange = {
                        artist.value = it
                        state.artist = artist.value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    label = { Text("Artist") }
                )
                // Genre TextField
                OutlinedTextField(
                    value = genre.value ?: "",
                    onValueChange = {
                        genre.value = it
                        state.genre = genre.value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    label = { Text("Genre") }
                )
                // Release Date TextField
                OutlinedTextField(
                    value = releaseDate.value ?: "",
                    onValueChange = {
                        releaseDate.value = it
                        state.releaseDate = releaseDate.value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    label = { Text("Release Date") },
                    placeholder = { Text("YYYY-MM-DD") }
                )
                // Artwork URL TextField
                OutlinedTextField(
                    value = artworkUrl.value ?: "",
                    onValueChange = {
                        artworkUrl.value = it
                        state.artworkUrl = artworkUrl.value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    singleLine = true,
                    label = { Text("Artwork URL") }
                )
                // Price TextField
                OutlinedTextField(
                    value = if (price.value.isNotBlank()) String.format(
                        Locale.UK,
                        "%.2f",
                        price.value.toDouble()
                    ) else "",
                    onValueChange = { priceString ->
//
//                        if (it.isNotBlank()) {
//                            val formattedString = String.format(Locale.UK, "%.2f", it)
//                            price.value = formattedString
//                            state.price = price.value.toDouble()
//                        }
                        if (priceString.isEmpty() && !priceString.matches(Regex("^0\\d+$"))) {
                            if (price.value == ".00"){
                                price.value = ""
                            } else {
                                price.value = priceString
                            }

                        }
                        else {
                            price.value = when (priceString.toDoubleOrNull()) {
                                null -> price.value
                                else -> {
                                    state.price = priceString.toDouble()
                                    priceString
                                }
                            }
                        }
                        Log.i("AddAlbumScreen", "String Value ${price.value}")
                        Log.i("AddAlbumScreen", "State Double Value ${state.price}")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = { Text("Price") },
                    placeholder = { Text("0.00") }
                )
                // Stock TextField
                OutlinedTextField(
                    value = stock.value,
                    onValueChange = {
                        if (
                            it.isNotBlank() &&
                            !it.matches(Regex("^0\\d+$")) &&
                            it.length <= 9
                        ) {
                            stock.value = it.take(9)
                            state.stock = stock.value.toInt()
                        } else if (it.length > 9) {
                            // Prevents the stock from resetting when the string is longer than 9
                        } else {
                            stock.value = ""
                            state.stock = 0
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = { Text("Stock") },
                    placeholder = { Text("0") }
                )
            }
        }
    }

}