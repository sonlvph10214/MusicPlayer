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
import com.example.soundcloud.model.zing.Song


class SongZingAdapter(var songList: MutableList<Song>, var context: Context) :
    RecyclerView.Adapter<SongZingAdapter.AudioHolder>() {

    companion object{
        val URL_IMG ="https://photo-resize-zmp3.zmdcdn.me/w94_r1x1_webp/"
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_song, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return songList.size
    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgSong: ImageView = itemView.findViewById(R.id.imgSong)
        private var tvTitleSong: TextView = itemView.findViewById(R.id.tvTitleSong)
        private var tvSinger: TextView = itemView.findViewById(R.id.tvNameSinger)

        init {

        }

        fun onBind(position: Int) {
            tvTitleSong.text = songList[position].name
            tvSinger.text = songList[position].artist
            Glide.with(context).load(URL_IMG+songList[position].thumb).into(imgSong)

        }
    }
}
