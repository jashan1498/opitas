package com.sample.opitas

import androidx.lifecycle.MutableLiveData
import com.sample.opitas.models.LanguagesListModel
import com.sample.opitas.models.SearchRequestModel
import com.sample.opitas.models.SearchResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.Flow

interface SearchService {
    @POST("translate")
    suspend fun getTextTranslation(@Body requestModel: SearchRequestModel): Response<SearchResponseModel>

    @GET("languages")
    suspend fun getLanguagesList(): Response<ArrayList<LanguagesListModel>>
}