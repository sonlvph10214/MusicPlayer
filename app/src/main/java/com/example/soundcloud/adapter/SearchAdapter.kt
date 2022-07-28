package com.example.soundcloud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.soundcloud.R
import com.example.soundcloud.model.Item
import com.example.soundcloud.model.Track


class SearchAdapter(var context: Context) :
    RecyclerView.Adapter<SearchAdapter.AudioHolder>(){

    private var soundList: MutableList<Track> = mutableListOf()


    var onClickItemSearch: ((Track, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_search, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return soundList.size
    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvNameSearch: TextView = itemView.findViewById(R.id.tvNameSearch)

        init {
            itemView.setOnClickListener {
                val item = soundList[layoutPosition]
                onClickItemSearch?.invoke(item, layoutPosition)
            }
        }

        fun onBind(position: Int) {
            tvNameSearch.text = soundList[position].name

        }
    }

    fun setList(list: MutableList<Track>){
        soundList.clear()
        soundList.addAll(list)
        notifyDataSetChanged()
    }


}