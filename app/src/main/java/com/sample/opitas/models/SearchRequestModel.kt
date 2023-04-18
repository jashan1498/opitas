package com.sample.opitas.models

import com.google.gson.annotations.SerializedName
import com.sample.opitas.utils.Constants

data class SearchRequestModel(
    @SerializedName("q")
    var q: String,
    @SerializedName("source")
    var source: String,
    @SerializedName("target")
    var target: String,
    @SerializedName("format")
    var format: String = "text",
    @SerializedName("api_key")
    var apiKey: String = Constants.API_KEY
)