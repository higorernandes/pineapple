package pineapplesoftware.pineappleapp.service

import pineapplesoftware.pineappleapp.main.model.TransactionItemDto
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by root on 2017-07-23.
 */

interface PineappleApiService {

    @GET("somehost/someservice")
    fun authenticate() : Call<TransactionItemDto>

}