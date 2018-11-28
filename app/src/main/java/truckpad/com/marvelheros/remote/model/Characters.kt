package truckpad.com.marvelheros.remote.model

import com.google.gson.annotations.SerializedName

class Characters(
    @SerializedName("id")
    val id : String,
    @SerializedName("name")
    val name : String)
