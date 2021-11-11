package com.kazantsev.coder.repo.api.model

import com.google.gson.annotations.SerializedName

data class ResponseApi(
    @SerializedName("items") val items: List<UsersApi>
)