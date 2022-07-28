package com.example.soundcloud.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.soundcloud.R
import com.example.soundcloud.model.ItemArtist
import com.example.soundcloud.model.person.PlayListPerson

class PlayListPersonAdapter(
    var context: Context,
    private var onClickItem: ((PlayListPerson,Int) -> Unit)?,
    private var playlistDetail:Boolean=false
) :
    RecyclerView.Adapter<PlayListPersonAdapter.PlayListViewHolder>(){

    private var playLists: MutableList<PlayListPerson> = mutableListOf()
    private var viewBinderHelper : ViewBinderHelper = ViewBinderHelper()
     var onDeleteItem: ((PlayListPerson,Int) -> Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlayListViewHolder(
            inflater.inflate(R.layout.item_playlist_person, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int {
        return playLists.size
    }


    inner class PlayListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvNamePlayList: TextView = itemView.findViewById(R.id.tvItemPlayList)
        private var imgItemPlayList: ImageView = itemView.findViewById(R.id.imgItemPlaylist)
        private var swipeRevealLayout: SwipeRevealLayout = itemView.findViewById(R.id.swipeLayout)
        private var layoutDel: LinearLayout = itemView.findViewById(R.id.layoutDelete)
        private var layoutItem: LinearLayout = itemView.findViewById(R.id.layoutItem)

        init {
            layoutItem.setOnClickListener {
                val item = playLists[layoutPosition]
                onClickItem?.invoke(item,layoutPosition)
            }
            layoutDel.setOnClickListener {
//                playLists.removeAt(adapterPosition)
//                notifyItemRemoved(layoutPosition)
                onDeleteItem?.invoke(playLists[layoutPosition],layoutPosition)
            }
        }

        fun onBind(position: Int) {
            tvNamePlayList.text = playLists[position].name
            viewBinderHelper.bind(swipeRevealLayout,playLists[position].name)


        }
    }

    fun setPlayLists(list: MutableList<PlayListPerson>){
        playLists.clear()
        playLists.addAll(list)
        notifyDataSetChanged()
    }


}