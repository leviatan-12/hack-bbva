package com.idemia.biosmart.models

import com.google.gson.annotations.SerializedName

//region POJO Classes
data class EnrolmentResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("encode_person")
    val encodePerson: EncodePerson,
    @SerializedName("enroll_person")
    val enrollPerson: EnrollPerson?,
    @SerializedName("match_person_to_person")
    val matchPersonToPerson: MatchPersonToPerson?,
    @SerializedName("message")
    val message: String,
    @SerializedName("request_type")
    val requestType: String
)

data class MatchPersonToPerson(
    @SerializedName("candidates")
    val candidates: List<Candidate>,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("no_hit_rank")
    val noHitRank: Int
)

data class Candidate(
    @SerializedName("desicion")
    val desicion: String,
    @SerializedName("encrypted")
    val encrypted: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("score")
    val score: Int
)

data class EnrollPerson(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("message")
    val message: String
)

data class EncodePerson(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("message")
    val message: String
)

data class AuthenticationResponse(
    @SerializedName("authenticate_person")
    val authenticatePerson: AuthenticatePerson?,
    @SerializedName("code")
    val code: Int,
    @SerializedName("encode_person")
    val encodePerson: EncodePerson?,
    @SerializedName("message")
    val message: String,
    @SerializedName("person_id")
    val personId: String?,
    @SerializedName("request_type")
    val requestType: String?
)

data class AuthenticatePerson(
    @SerializedName("candidates")
    val candidates: List<Candidate>,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("error_code")
    val errorCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("no_hit_rank")
    val noHitRank: Int
)

data class IdentifyResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("encode_person")
    val encodePerson: EncodePerson?,
    @SerializedName("match_person_to_person")
    val matchPersonToPerson: MatchPersonToPerson?,
    @SerializedName("message")
    val message: String,
    @SerializedName("request_type")
    val requestType: String
)
//endregion