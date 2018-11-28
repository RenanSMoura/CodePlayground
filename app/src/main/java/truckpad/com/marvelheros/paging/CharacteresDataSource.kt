package truckpad.com.marvelheros.paging

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import truckpad.com.marvelheros.remote.MarvelService
import truckpad.com.marvelheros.remote.model.Character

class CharacteresDataSource(
    private val marvelApi: MarvelService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Character>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0,1,numberOfItems,callback, null)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val numberOfItems = params.requestedLoadSize
        val page = params.key
        createObservable(page,page +1,numberOfItems,null,callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val numberOfItems = params.requestedLoadSize
        val page = params.key
        createObservable(page,page - 1,numberOfItems,null,callback)
    }


    private fun createObservable(
        requestedPage: Int, adjacentPage: Int, requestedLoadSize: Int,
        initialCallback: LoadInitialCallback<Int, Character>?,
        callback: LoadCallback<Int, Character>?
    ) {
        compositeDisposable.add(
            marvelApi.getCharacters(requestedPage * requestedLoadSize).subscribe( { response ->
                Log.d("RSM","LoadingPage : $requestedPage")
                initialCallback?.onResult(response.data.results,null,adjacentPage)
                callback?.onResult(response.data.results,adjacentPage)
            },{t->
                Log.d("RSM","LoadingPage error : ${t.message}")
            })
        )

    }

}