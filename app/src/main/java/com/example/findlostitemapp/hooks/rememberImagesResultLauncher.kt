package com.example.findlostitemapp.hooks

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberImagesResultLauncher(callback: (List<Uri>) -> Unit = {}):
        ManagedActivityResultLauncher<String,
                List<Uri>> {
    return rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) {
        callback(it)
    }
}

@Composable
fun rememberImageResultLauncher(callback: (Uri?) -> Unit = {}): ManagedActivityResultLauncher<String, Uri?> {
    return rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        callback(it)
    }

}