package com.sanmer.geomag.utils.timber

import timber.log.Timber

class ReleaseTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, "<MAG_REL>$tag", message, t)
    }
}