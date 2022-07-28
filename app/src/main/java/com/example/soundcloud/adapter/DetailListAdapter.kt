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
import com.example.soundcloud.model.ItemTracks


class DetailListAdapter(var trackList: MutableList<ItemTracks>, var context: Context) :
    RecyclerView.Adapter<DetailListAdapter.AudioHolder>() {


    var onClickSong: ((ItemTracks, Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_detail_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AudioHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return trackList.size
    }


    inner class AudioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imgDetailList: ImageView = itemView.findViewById(R.id.imgDetailList)
        private var tvTitleDetailList: TextView = itemView.findViewById(R.id.tvTitleDetailList)
        private var tvMusicGenreDetailList: TextView = itemView.findViewById(R.id.tvMusicGenreDetailList)

        init {
            itemView.setOnClickListener {
                val item = trackList[layoutPosition]
                onClickSong?.invoke(item, layoutPosition)
            }
        }

        fun onBind(position: Int) {
            tvTitleDetailList.text = trackList[position].track.name
            tvMusicGenreDetailList.text = trackList[position].track.artists[0].name
            Glide.with(context).load(trackList[position].track.album.images[0].url).into(imgDetailList)
        }
    }
}