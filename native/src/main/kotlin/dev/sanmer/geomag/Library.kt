package dev.sanmer.geomag

internal interface Library {
    val name: String
    fun load()
}