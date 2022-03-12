package com.yowayowa.yawning.ui.ranking

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yowayowa.yawning.ui.pro.ProFragment

private const val NUM_TABS = 3

class TabPagerAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RankingComboFragment()
            1 -> RankingDistanceFragment()
            2 -> RankingRegionFragment()
            else -> ProFragment()
        }
    }

}
