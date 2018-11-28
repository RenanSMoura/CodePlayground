package truckpad.com.marvelheros.view

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import truckpad.com.marvelheros.BuildConfig
import truckpad.com.marvelheros.paging.CharactersDataSourceFactory
import truckpad.com.marvelheros.remote.MarvelServiceFactory
import truckpad.com.marvelheros.remote.model.Character

class CharacteresViewModel : ViewModel() {

    var characterList : Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 20

    private val sourceFactory : CharactersDataSourceFactory


    init {
        sourceFactory = CharactersDataSourceFactory(compositeDisposable,MarvelServiceFactory.makeMarvelService(BuildConfig.DEBUG))
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()
        characterList = RxPagedListBuilder(sourceFactory,config).setFetchScheduler(Schedulers.io()).buildObservable()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}