package com.example.soundcloud.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.soundcloud.view.home.HomeFragment
import com.example.soundcloud.view.home.HomeZingFragment
import com.example.soundcloud.view.personal.PersonalFragment
import com.example.soundcloud.view.search.SearchFragment
import com.example.soundcloud.view.stream.MyMusicFragment
import com.example.soundcloud.view.stream.SongLocalDeviceFragment

class BottomNavigationViewPagerAdapter (fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }
    private var hashMap : HashMap<Int,Fragment> = HashMap()

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeZingFragment()

            1 -> MyMusicFragment()

            2 -> SearchFragment()

            3 -> PersonalFragment()



            else -> HomeZingFragment()
        }
    }




}