package com.example.findlostitemapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.findlostitemapp.navigation.NavGraph
import com.example.findlostitemapp.navigation.BottomNavigationBar
import com.example.findlostitemapp.navigation.NavProvider
import com.example.findlostitemapp.providers.StateProvider
import com.example.findlostitemapp.ui.components.Footer

import com.example.findlostitemapp.ui.theme.FindLostItemAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

val LocalSnackBar = compositionLocalOf<SnackbarHostState> {
    error("No SnackBar provided")
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val snackHostState = remember {
        SnackbarHostState()
    }
    FindLostItemAppTheme {
        CompositionLocalProvider(LocalSnackBar provides snackHostState) {
            NavProvider(navController) {
                StateProvider {
                    Scaffold(bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }, snackbarHost = { SnackbarHost(hostState = snackHostState) }) { paddingValues ->
                        NavGraph(
                            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                            navController =
                            navController
                        )
                    }
                }
            }
        }
    }
}