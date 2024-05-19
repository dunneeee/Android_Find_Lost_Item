package com.example.findlostitemapp.utils

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtils {
    fun uriToFile(context: Context, uri: Uri, fileName: String? = null): File? {
        val file = (fileName ?: uri.lastPathSegment)?.let { File(context.cacheDir, it) }
        file?.let {
            it.outputStream().use { output ->
                context.contentResolver.openInputStream(uri)?.use { input ->
                    input.copyTo(output)
                }
            }
        }
        return file
    }
}