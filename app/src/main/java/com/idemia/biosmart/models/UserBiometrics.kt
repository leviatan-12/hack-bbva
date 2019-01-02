package com.idemia.biosmart.models

import com.google.gson.annotations.SerializedName

data class UserBiometrics(@SerializedName("customer_id") val userId: String,
                          @SerializedName("os_type") val osType: Int = 1, // 1 Android App
                          val encrypted: Boolean = false) {

    // Image Face
    @SerializedName("image_src")    val photo: String? = null

    // Left Hand
    @SerializedName("left_thumb")   val leftThumb: String? = null
    @SerializedName("left_index")   val leftIndex: String? = null
    @SerializedName("left_middle")  val leftMiddle: String? = null
    @SerializedName("left_ring")    val leftRing: String? = null
    @SerializedName("left_little")  val leftLittle: String? = null

    // Right Hand
    @SerializedName("right_thumb")  val rightThumb: String? = null
    @SerializedName("right_index")  val rightIndex: String? = null
    @SerializedName("right_middle") val rightMiddle: String? = null
    @SerializedName("right_ring")   val rightRing: String? = null
    @SerializedName("right_little") val rightLittle: String? = null

    // Iris
    @SerializedName("left_iris")    val leftIris: String? = null
    @SerializedName("right_iris")   val rightIris: String? = null
}