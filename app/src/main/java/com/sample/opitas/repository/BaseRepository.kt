package com.sample.opitas.repository

import com.sample.opitas.RetrofitClient
import com.sample.opitas.models.LanguagesListModel
import com.sample.opitas.models.SearchRequestModel
import com.sample.opitas.models.SearchResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class BaseRepository {

    companion object {
        private val apiInterface = RetrofitClient.getInstance()

        suspend fun getSearchTranslations(searchRequestModel: SearchRequestModel) =
            flow {
                emit(apiInterface.getTextTranslation(searchRequestModel))
            }.flowOn(Dispatchers.IO)

        fun getLanguagesList() = flow {
            emit(apiInterface.getLanguagesList())
        }.flowOn(Dispatchers.IO)

    }

}