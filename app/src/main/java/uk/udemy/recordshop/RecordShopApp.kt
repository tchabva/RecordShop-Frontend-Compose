package uk.udemy.recordshop

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun RecordShopApp(paddingValues: PaddingValues, navHostController: NavHostController){

    val viewModel: MainViewModel = viewModel()
    val viewState by viewModel.recordShopState

    /*
    TODO : FINISH IMPLEMENTING HOMESCREEN USING NAVHOST
     */


}