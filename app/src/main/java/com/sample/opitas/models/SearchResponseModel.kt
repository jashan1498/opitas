package com.sample.opitas.models

data class SearchResponseModel(
    val detectedLanguage: DetectedLanguage,
    val translatedText: String
) {
    data class DetectedLanguage(
        val confidence: Long,
        val language: String
    )
}