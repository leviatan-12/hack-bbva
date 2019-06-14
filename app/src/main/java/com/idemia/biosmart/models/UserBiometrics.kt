package com.idemia.biosmart.models

import com.google.gson.annotations.SerializedName

data class UserBiometrics(@SerializedName("customer_id") val userId: String,
                          @SerializedName("os_type") val osType: Int = 1, // 1 Android App
                          val encrypted: Boolean = false) {

    // Image Face
    @SerializedName("image_src")    var photo: String? = null

    // Left Hand
    @SerializedName("left_thumb")   var leftThumb: String? = null
    @SerializedName("left_index")   var leftIndex: String? = null
    @SerializedName("left_middle")  var leftMiddle: String? = null
    @SerializedName("left_ring")    var leftRing: String? = null
    @SerializedName("left_little")  var leftLittle: String? = null

    // Right Hand
    @SerializedName("right_thumb")  var rightThumb: String? = null
    @SerializedName("right_index")  var rightIndex: String? = null
    @SerializedName("right_middle") var rightMiddle: String? = null
    @SerializedName("right_ring")   var rightRing: String? = null
    @SerializedName("right_little") var rightLittle: String? = null

    // Iris
    @SerializedName("left_iris")    var leftIris: String? = null
    @SerializedName("right_iris")   var rightIris: String? = null
}