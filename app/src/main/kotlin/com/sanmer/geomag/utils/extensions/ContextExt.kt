package com.sanmer.geomag.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.sanmer.geomag.BuildConfig
import java.io.File

val Context.jsonDir get() = cacheDir.resolve("json")

fun Context.deleteJson() {
    jsonDir.listFiles().orEmpty()
        .forEach {
            if (it.extension == "json") {
                it.delete()
            }
        }
}

fun Context.createJson(name: String) = jsonDir
    .resolve("${name}.json")
    .apply {
        parentFile?.apply { if (!exists()) mkdirs() }
        createNewFile()
    }

fun Context.openUrl(url: String) {
    Intent.parseUri(url, Intent.URI_INTENT_SCHEME).apply {
        startActivity(this)
    }
}

fun Context.getUriForFile(file: File): Uri {
    return FileProvider.getUriForFile(this,
        "${BuildConfig.APPLICATION_ID}.provider", file
    )
}

fun Context.shareFile(file: File, mimeType: String) {
    ShareCompat.IntentBuilder(this)
        .setType(mimeType)
        .addStream(getUriForFile(file))
        .startChooser()
}