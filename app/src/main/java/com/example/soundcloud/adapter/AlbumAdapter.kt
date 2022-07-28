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
import com.example.soundcloud.model.ItemAlbum

class AlbumAdapter(var itemList: MutableList<ItemAlbum>, var context: Context) :
    RecyclerView.Adapter<AlbumAdapter.AudioHolder>() {


    var onClickSong: ((ItemAlbum, Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_album, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgAlbum: ImageView = itemView.findViewById(R.id.imgAlbum)
        private var tvTitleAlbum: TextView = itemView.findViewById(R.id.tvTitleAlbum)
        private var tvArtistAlbum: TextView = itemView.findViewById(R.id.tvArtistAlbum)

        init {
            itemView.setOnClickListener {
                val item = itemList[layoutPosition]
                onClickSong?.invoke(item, layoutPosition)
            }
        }

        fun onBind(position: Int) {
            tvTitleAlbum.text = itemList[position].name
            tvArtistAlbum.text = itemList[position].artists[0].name
            Glide.with(context).load(itemList[position].images[0].url).into(imgAlbum)
        }
    }
}