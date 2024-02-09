package dev.sanmer.geomag


internal object ImplLibrary: Library {
    override val name: String = "geomag-jni"

    override fun load() {
        System.loadLibrary(name)
    }
}
