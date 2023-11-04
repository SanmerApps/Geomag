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
    private val moshi = Moshi.Builder().build()
    private val record = moshi.adapter<RecordJson>()
    private val records = moshi.adapter<List<RecordJson>>()

    private fun Record.toJsonText(): String? {
        return record.indent("    ").toJson(toJson())
    }

    private fun List<Record>.toJsonText(): String? {
        val list = map { it.toJson() }
        return records.indent("    ").toJson(list)
    }

    fun shareJsonFile(context: Context, value: Record) {
        val file = context.createJson(value.time.toString())
            .apply {
                writeText(value.toJsonText()!!)
            }

        context.shareFile(file, "text/json")
    }

    fun shareJsonFile(context: Context, values: List<Record>) {
        val file = context.createJson(LocalDateTime.now().toString())
            .apply {
                writeText(values.toJsonText()!!)
            }

        context.shareFile(file, "text/json")
    }
}