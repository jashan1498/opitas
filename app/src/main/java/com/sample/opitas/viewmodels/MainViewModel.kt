package com.sample.opitas.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.opitas.models.LanguagesListModel
import com.sample.opitas.models.SearchRequestModel
import com.sample.opitas.models.SearchResponseModel
import com.sample.opitas.repository.BaseRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {

    var searchResponseModel = MutableLiveData<Response<SearchResponseModel>>()
    var languagesListModel = MutableLiveData<Response<ArrayList<LanguagesListModel>>>()

    companion object {
        const val TAG = "MainViewModel"
    }

    fun getSearchTranslations(query: String, source: String = "en", target: String = "en") {
        val searchRequestModel = SearchRequestModel(query, source, target)

        viewModelScope.launch {
            BaseRepository.getSearchTranslations(searchRequestModel).catch {
                Log.d(TAG, it.message.toString())
            }.collect {
                searchResponseModel.value = it
            }
        }
    }

    fun getLanguagesList() {
        viewModelScope.launch {
            BaseRepository.getLanguagesList().catch {
                Log.d(TAG, it.message.toString())
            }.collect {
                languagesListModel.value = it
            }
        }
    }


}