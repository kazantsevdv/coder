package com.kazantsev.coder.model

import com.google.gson.annotations.SerializedName

data class ResponseApi(
    @SerializedName("items") val items: List<UsersApi>
)