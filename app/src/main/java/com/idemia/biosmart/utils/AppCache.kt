package com.idemia.biosmart.utils

import com.idemia.biosmart.models.UserBiometrics
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

    val userBiometrics: UserBiometrics?
        get() {
            if(!username.isNullOrEmpty()){
                val info = UserBiometrics(username!!, 1, false)
                facePhoto?.let {
                    info.photo = Base64.encode(it.jpegImage)
                }

                imageListLeft?.let {
                    if(it.size == 4){
                        info.leftIndex = Base64.encode(it[0].jpegImage)
                        info.leftMiddle = Base64.encode(it[1].jpegImage)
                        info.leftRing = Base64.encode(it[2].jpegImage)
                        info.leftLittle = Base64.encode(it[3].jpegImage)
                    }
                }

                imageListRight?.let {
                    if(it.size == 4){
                        info.rightIndex = Base64.encode(it[0].jpegImage)
                        info.rightMiddle = Base64.encode(it[1].jpegImage)
                        info.rightRing = Base64.encode(it[2].jpegImage)
                        info.rightLittle = Base64.encode(it[3].jpegImage)
                    }
                }
                return info
            }
            return null
        }

}
