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
import com.example.soundcloud.model.Track


class ArtistTrackAdapter(var trackList: MutableList<Track>, var context: Context) :
    RecyclerView.Adapter<ArtistTrackAdapter.AudioHolder>() {


    var onClickSong: ((Track, Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_song_artist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        val sz: Int = trackList.size
        return if (sz < 3) {
            sz
        } else 3

    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgSongArtist: ImageView = itemView.findViewById(R.id.imgSongArtist)
        private var tvTitleSongArtist: TextView = itemView.findViewById(R.id.tvTitleSongArtist)
        private var tvMusicGenreSongArtist: TextView = itemView.findViewById(R.id.tvMusicGenreSongArtist)

        init {
            itemView.setOnClickListener {
                val item = trackList[layoutPosition]
                onClickSong?.invoke(item, layoutPosition)
            }
        }

        fun onBind(position: Int) {
            tvTitleSongArtist.text = trackList[position].name
            tvMusicGenreSongArtist.text = trackList[position].artists[0].name
            Glide.with(context).load(trackList[position].album.images[0].url).into(imgSongArtist)
        }
    }
}