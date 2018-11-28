package truckpad.com.marvelheros.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import truckpad.com.marvelheros.remote.model.MarvelModel

interface MarvelService {

    @GET("characters")
    fun getCharacters(@Query("offset") offset: Int? = 0) : Observable<MarvelModel>

}
