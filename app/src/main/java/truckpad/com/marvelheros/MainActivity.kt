package truckpad.com.marvelheros

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import truckpad.com.marvelheros.view.CharacterAdapter
import truckpad.com.marvelheros.view.CharacteresViewModel

class MainActivity : AppCompatActivity() {



    private val viewModel : CharacteresViewModel by lazy {
       ViewModelProviders.of(this).get(CharacteresViewModel::class.java)
    }
    private val adapter : CharacterAdapter by lazy {
        CharacterAdapter()
    }

    private var recyclerState : Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerCharacters.layoutManager = LinearLayoutManager(this)
        recyclerCharacters.adapter = adapter
        subscribeToList()

    }



    private fun subscribeToList(){
        val disposable =  viewModel.characterList.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    list -> adapter.submitList(list)
                    if (recyclerState != null) {
                        recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null
                    }
            },{
                    e ->
                Log.e("RSM", "ERROR : $e")
            })
    }
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("lmState",recyclerCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }
}
