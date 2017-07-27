package pineapplesoftware.pineappleapp.account.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by root on 2017-07-26.
 */
class AuthenticationResult
{
    @SerializedName("access_token")
    @Expose
    var accessToken : String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn : String? = null
}