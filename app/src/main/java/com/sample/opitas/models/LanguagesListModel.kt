package com.sample.opitas.models

import com.google.gson.annotations.SerializedName

data class LanguagesListModel(
    @SerializedName("code") var code: String,
    @SerializedName("name") var name: String,
    @SerializedName("targets") var targets: ArrayList<String> = arrayListOf()
)