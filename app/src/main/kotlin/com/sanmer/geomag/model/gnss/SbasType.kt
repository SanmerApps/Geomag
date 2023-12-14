package com.sanmer.geomag.model.gnss

enum class SbasType {
    WAAS,
    EGNOS,
    MSAS,
    GAGAN,
    SDCM,
    BDSBAS,
    SOUTHPAN,
    UNKNOWN;

    companion object {
        val SbasType.code: String get() {
            return when (this) {
                WAAS -> "US"
                EGNOS -> "EU"
                MSAS -> "JP"
                GAGAN -> "IN"
                SDCM -> "RU"
                BDSBAS -> "CN"
                SOUTHPAN -> "AU"
                UNKNOWN -> "XX"
            }
        }

        fun Int.toSbasType(): SbasType {
            return if (this == 120 || this == 123 || this == 126 || this == 136) {
                EGNOS
            } else if (this == 125 || this == 140 || this == 141) {
                SDCM
            } else if (this == 130 || this == 143 || this == 144) {
                BDSBAS
            } else if (this == 131 || this == 133 || this == 135 || this == 138) {
                WAAS
            } else if (this == 127 || this == 128 || this == 139) {
                GAGAN
            } else if (this == 129 || this == 137) {
                MSAS
            } else if (this == 122) {
                SOUTHPAN
            } else {
                UNKNOWN
            }
        }
    }
}