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
import com.example.soundcloud.model.zing.Song

class ChillZingAdapter(var songList: MutableList<Song>, var context: Context) :
    RecyclerView.Adapter<ChillZingAdapter.AudioHolder>() {

    companion object{
        val URL_IMG ="https://photo-resize-zmp3.zmdcdn.me/w94_r1x1_webp/"
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_party, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return songList.size
    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgParty: ImageView = itemView.findViewById(R.id.imgParty)
        private var tvTitleParty: TextView = itemView.findViewById(R.id.tvTitleParty)
        private var tvMusicGenre: TextView = itemView.findViewById(R.id.tvMusicGenre)

        init {
//            image.setOnClickListener(View.OnClickListener {
//            })
        }

        fun onBind(position: Int) {
            tvTitleParty.text = songList[position].name
            tvMusicGenre.text = songList[position].artist
            Glide.with(context).load(URL_IMG+songList[position].thumb).into(imgParty)
        }
    }
}
