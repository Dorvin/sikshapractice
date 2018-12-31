package com.example.hyeon.siksha2android.dev.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class VPAdapter(fm: FragmentManager, ff: Array<Fragment>) : FragmentPagerAdapter(fm) {
    private var frags: Array<Fragment>? = null

    init {
        frags = ff
    }

    fun setFrags(ff: Array<Fragment>){
        this.frags = ff
    }
    override fun getItem(i: Int): Fragment {
        return frags!![i]
    }

    override fun getCount(): Int {
        return frags!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
/*        when (position) {
            0 -> return "아침"
            1 -> return "점심"
            2 -> return "저녁"
            else -> return ""
        }*/
        return null
    }
}