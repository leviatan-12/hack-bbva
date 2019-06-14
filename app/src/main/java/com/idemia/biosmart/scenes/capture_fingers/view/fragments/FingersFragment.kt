package com.idemia.biosmart.scenes.capture_fingers.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.idemia.biosmart.R
import com.idemia.biosmart.scenes.capture_fingers.view.adapters.FingersListAdapter
import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoImage
import kotlinx.android.synthetic.main.fingers_fragment.*

class FingersFragment : Fragment() {
    private val adapter = FingersListAdapter()
    private lateinit var mView: View
    var imageList: ArrayList<MorphoImage> = arrayListOf()

    companion object {
        fun newInstance() = FingersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView  = inflater.inflate(R.layout.fingers_fragment, container, false)
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onLoadFragment()
    }

    private fun onLoadFragment(){
        initRecyclerView()
    }

    private fun initRecyclerView(){
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(mView.context)
        adapter.imageList = imageList
        adapter.notifyDataSetChanged()
    }

    fun bind(imageList: ArrayList<MorphoImage>){
        this.imageList = imageList
        adapter.imageList.clear()
        adapter.imageList = imageList
        adapter.notifyDataSetChanged()
    }
}
