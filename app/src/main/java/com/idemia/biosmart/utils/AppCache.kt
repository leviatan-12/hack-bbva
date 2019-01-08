package com.idemia.biosmart.utils

import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoImage

object AppCache {
    // Face and fingers
    var imageListLeft: List<MorphoImage>? = null
    var imageListRight: List<MorphoImage>? = null
    var facePhoto: MorphoImage? = null

    // User info
    var username: String? = null
    var name: String? = null
    var lastName: String? = null
    var secondLastName: String? = null

    /** Clear Cache */
    fun clearCache(){
        imageListLeft = null
        imageListRight = null
        facePhoto = null

        username = null
        name = null
        lastName = null
        secondLastName = null
    }
}
