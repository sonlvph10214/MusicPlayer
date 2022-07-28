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
import com.example.soundcloud.databinding.ItemDetailListBinding
import com.example.soundcloud.databinding.ItemMyArtistBinding
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.model.person.Music

class MyArtistAdapter(var artistList: MutableList<ItemArtist>, var context: Context) :
    RecyclerView.Adapter<MyArtistAdapter.AudioHolder>() {

    var onClickArtist: ((ItemArtist, Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_my_artist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return artistList.size
    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgArtist: ImageView = itemView.findViewById(R.id.imgMyArtist)
        private var tvArtist: TextView = itemView.findViewById(R.id.tvMyArtist)

        init {
            itemView.setOnClickListener {
                val item = artistList[layoutPosition]
                onClickArtist?.invoke(item, layoutPosition)
            }
        }

        fun onBind(position: Int) {
            tvArtist.text = artistList[position].name
            Glide.with(context).load(artistList[position].images[0].url).into(imgArtist)
        }
    }
}