package com.example.soundcloud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.model.Item

class ChillAdapter(var itemList: MutableList<Item>, var context: Context) :
    RecyclerView.Adapter<ChillAdapter.AudioHolder>() {


    var onClickSong: ((Item, Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_play_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgPlayList: ImageView = itemView.findViewById(R.id.imgPlayList)
        private var tvTitlePlayList: TextView = itemView.findViewById(R.id.tvTitlePlayList)
        private var tvDescriptionPlayList: TextView = itemView.findViewById(R.id.tvDescriptionPlayList)

        init {
            itemView.setOnClickListener {
                val item = itemList[layoutPosition]
                onClickSong?.invoke(item, layoutPosition)
            }
        }

        fun onBind(position: Int) {
            tvTitlePlayList.text = itemList[position].name
            tvDescriptionPlayList.text = itemList[position].description
            Glide.with(context).load(itemList[position].images[0].url).into(imgPlayList)
        }
    }
}