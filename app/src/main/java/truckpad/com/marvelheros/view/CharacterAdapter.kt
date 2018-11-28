package truckpad.com.marvelheros.view

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_character.view.*
import truckpad.com.marvelheros.R
import truckpad.com.marvelheros.extensions.load
import truckpad.com.marvelheros.remote.model.Character

class CharacterAdapter : PagedListAdapter<Character,CharacterAdapter.VH>(characterDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent,false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val character = getItem(position)
        character?.let {
            holder.txtName.text = character.name
            holder.imgThumbmail.load("${character.thumbnail.path}/standard_medium.${character.thumbnail.extension}")
        }
    }


    inner class VH(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imgThumbmail = itemView.imgThumbnail
        val txtName = itemView.txtName
    }

    companion object {
        val characterDiff = object : DiffUtil.ItemCallback<Character>(){
            override fun areItemsTheSame(oldItem: Character?, newItem: Character?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: Character?, newItem: Character?): Boolean {
                return  oldItem == newItem
            }

        }
    }
}