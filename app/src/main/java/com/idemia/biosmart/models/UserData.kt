package com.idemia.biosmart.models

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("m_last_name")
    val mLastName: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("encrypted")
    val encrypted: Boolean = false,
    @SerializedName("photo")
    val photo: String?
)