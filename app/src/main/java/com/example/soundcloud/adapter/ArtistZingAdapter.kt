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
import com.example.soundcloud.model.zing.ArtistZing
import com.example.soundcloud.model.zing.Song


class ArtistZingAdapter(var artistList: MutableList<ArtistZing>, var context: Context) :
    RecyclerView.Adapter<ArtistZingAdapter.AudioHolder>() {
    companion object{
        val URL_IMG ="https://photo-resize-zmp3.zmdcdn.me/w94_r1x1_webp/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_artist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return artistList.size
    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgArtist: ImageView = itemView.findViewById(R.id.imgArtist)
        private var tvArtist: TextView = itemView.findViewById(R.id.tvArtistItem)

        init {
//            image.setOnClickListener(View.OnClickListener {
//            })
        }

        fun onBind(position: Int) {


            tvArtist.text = artistList[position].name
            Glide.with(context).load(URL_IMG+artistList[position].thumb).into(imgArtist)
        }
    }
}
