package com.sanmer.geomag.app.utils

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

object OsUtils {
    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    val atLeastU get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE

    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    val atLeastT get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    val atLeastS get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}