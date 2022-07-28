package com.example.soundcloud.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.model.Track
import java.time.Duration


class TopTracksAdapter(var trackList: MutableList<Track>, var context: Context) :
    RecyclerView.Adapter<TopTracksAdapter.AudioHolder>() {


    var onClickSong: ((Track, Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AudioHolder(
            inflater.inflate(R.layout.item_detail_list, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
        private var tvDuration: TextView = itemView.findViewById(R.id.tvDuration)

        init {
            itemView.setOnClickListener {
                val item = trackList[layoutPosition]
                onClickSong?.invoke(item, layoutPosition)
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun onBind(position: Int) {
            tvTitleDetailList.text = trackList[position].name
            tvMusicGenreDetailList.text = trackList[position].artists[0].name
            Glide.with(context).load(trackList[position].album.images[0].url).into(imgDetailList)
            tvDuration.text =  elapsedTime(trackList[position].durationMs)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun elapsedTime(time: String): String {
        val duration: Duration = Duration.ofMillis(time.toLong())
        val seconds = duration.seconds
        val hH = seconds / 3600
        val mM = seconds % 3600 / 60
        val sS = seconds % 60
        return String.format("%02d:%02d", mM, sS)
    }
}