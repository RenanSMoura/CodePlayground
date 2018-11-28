package truckpad.com.marvelheros.paging

import android.arch.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import truckpad.com.marvelheros.remote.MarvelService
import truckpad.com.marvelheros.remote.model.Character

class CharactersDataSourceFactory (private val compositeDisposable: CompositeDisposable, private val marvelApi : MarvelService) : DataSource.Factory<Int, Character>(){

    override fun create(): DataSource<Int, Character> {
        return CharacteresDataSource(marvelApi,compositeDisposable)
    }
}