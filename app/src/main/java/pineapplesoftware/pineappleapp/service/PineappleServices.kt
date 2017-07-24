package pineapplesoftware.pineappleapp.service;

/**
 * Created by root on 2017-07-23.
 */

 class PineappleServices private constructor() {

    companion object {

        private var pineappleApiInstance : PineappleApiService? = null

        fun getPineappleServices() : PineappleApiService? {
            if (pineappleApiInstance == null) {
                pineappleApiInstance = PineappleClient.getClient()?.create(PineappleApiService::class.java)
            }
            return pineappleApiInstance
        }

        fun authenticate() {
            getPineappleServices()?.authenticate()
        }
    }

}
