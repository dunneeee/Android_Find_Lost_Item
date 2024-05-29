package com.example.findlostitemapp.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File

object FileUtils {
    fun uriToFile(context: Context, uri: Uri, fileName: String? = null): File? {
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
        val nameWithExtension = (fileName ?: uri.lastPathSegment)?.let { "$it.$extension" }
        val file = nameWithExtension?.let { File(context.cacheDir, it) }
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