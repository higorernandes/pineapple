package pineapplesoftware.pineappleapp.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by root on 2017-07-24.
 */
class PineappleClient private constructor() {

    init { }

    private object Holder {

        var retrofitInstance : Retrofit? = null
        private val BASE_URL : String = "https://google.com/"

        fun getClient() : Retrofit? {
            if (retrofitInstance == null) {
                val httpClient : OkHttpClient = OkHttpClient.Builder().build()
                retrofitInstance = Retrofit.Builder().client(httpClient).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            }

            return retrofitInstance
        }
    }

    companion object {
        fun getClient() : Retrofit? {
            return Holder.getClient()
        }
    }

}