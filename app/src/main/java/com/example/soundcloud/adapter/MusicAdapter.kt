package com.example.soundcloud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.soundcloud.R
import com.example.soundcloud.databinding.ItemDetailListBinding
import com.example.soundcloud.model.person.Music
import com.example.soundcloud.static.CommonUtils
import com.example.soundcloud.adapter.MusicAdapter.MyHolder
import com.example.soundcloud.model.person.PlayListPerson
import com.example.soundcloud.view.stream.MyMusicFragment
import com.example.soundcloud.view.stream.PlayListDetailFragment


class MusicAdapter(private val context: Context, private var musicList: ArrayList<Music>?, private val playlistDetails: Boolean = false,
                   private val selectionActivity: Boolean = false)
    : RecyclerView.Adapter<MyHolder>() {

    var onClickMusic: ((Music, Int,String) -> Unit)? = null
    var onMusicPos: ((Music, Int,String) -> Unit)? = null

    class MyHolder(binding: ItemDetailListBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitleDetailList
        val image = binding.imgDetailList
        val duration = binding.tvDuration
        val artist = binding.tvMusicGenreDetailList
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ItemDetailListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = musicList?.get(position)?.title
        holder.artist.text = musicList?.get(position)?.artist
        holder.duration.text = CommonUtils.formatDuration(musicList?.get(position)!!.duration)
        Glide.with(context)
            .load(musicList?.get(position)?.imgUri)
            .into(holder.image)
        when{
            playlistDetails ->{
                holder.root.setOnClickListener{
                    onMusicPos?.invoke(musicList!![position],position,"PlayListDetailAdapter")
                }
            }
            selectionActivity -> {
                holder.root.setOnClickListener {
                    if (addSong(musicList!![position]))
                        holder.root.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.purple_500
                            )
                        )
                    else
                        holder.root.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )

                }
            }
            else ->{
                holder.root.setOnClickListener{
                    onClickMusic?.invoke(musicList!![position],position,"MusicAdapter")
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return musicList!!.size
    }

    fun setMusicList(list: MutableList<Music>){
        musicList?.clear()
        musicList?.addAll(list)
        notifyDataSetChanged()
    }

    private fun addSong(song: Music): Boolean{
        MyMusicFragment.musicPlayList.ref[PlayListDetailFragment.pos].playlist?.forEachIndexed { index, music ->
            if(song.id == music.id){
                MyMusicFragment.musicPlayList.ref[PlayListDetailFragment.pos].playlist?.removeAt(index)
                return false
            }
        }
        MyMusicFragment.musicPlayList.ref[PlayListDetailFragment.pos].playlist?.add(song)
        return true
    }
}