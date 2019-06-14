package com.idemia.biosmart.scenes.enrolment_details.view.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPageUserInfoAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    private var fragmentList = ArrayList<Fragment>()
    private var titleList = ArrayList<String>()

    override fun getItem(index: Int): Fragment = fragmentList[index]
    override fun getCount(): Int = fragmentList.size
    override fun getPageTitle(position: Int): CharSequence? = titleList[position]

    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
}