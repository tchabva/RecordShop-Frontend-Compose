package uk.udemy.recordshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import uk.udemy.recordshop.ui.navigation.NavRoot
import uk.udemy.recordshop.ui.theme.RecordShopTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RecordShopTheme {
                NavRoot()
            }
        }
    }
}