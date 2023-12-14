package com.sanmer.geomag.utils

import android.content.Context
import com.sanmer.geomag.model.data.Record
import com.sanmer.geomag.model.json.RecordJson
import com.sanmer.geomag.model.json.toJson
import com.sanmer.geomag.utils.extensions.createJson
import com.sanmer.geomag.utils.extensions.now
import com.sanmer.geomag.utils.extensions.shareFile
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.datetime.LocalDateTime

object JsonUtils {
    private const val INDENT = "  "
    private val moshi by lazy { Moshi.Builder().build() }

    private fun Record.toJsonText() =
        moshi.adapter<RecordJson>()
            .indent(INDENT)
            .toJson(toJson())

    private fun List<Record>.toJsonText() =
        moshi.adapter<List<RecordJson>>()
            .indent(INDENT)
            .toJson(
                map { it.toJson() }
            )

    fun shareJsonFile(context: Context, value: Record) {
        val jsonText = value.toJsonText() ?: return
        val file = context.createJson(value.time.toString())
            .apply {
                writeText(jsonText)
            }

        context.shareFile(file, "text/json")
    }

    fun shareJsonFile(context: Context, values: List<Record>) {
        val jsonText = values.toJsonText() ?: return
        val file = context.createJson(LocalDateTime.now().toString())
            .apply {
                writeText(jsonText)
            }

        context.shareFile(file, "text/json")
    }
}