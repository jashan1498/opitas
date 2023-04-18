package com.sample.opitas

import com.sample.opitas.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        var retrofitService: SearchService? = null

        fun getInstance() : SearchService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(SearchService::class.java)
            }
            return retrofitService!!
        }
    }
}