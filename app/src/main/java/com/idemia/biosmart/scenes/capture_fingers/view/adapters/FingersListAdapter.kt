package com.idemia.biosmart.scenes.capture_fingers.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idemia.biosmart.R
import com.idemia.biosmart.utils.BitmapHelper
import com.morpho.mph_bio_sdk.android.sdk.msc.data.results.MorphoImage
import kotlinx.android.synthetic.main.layout_finger_item.view.*

class FingersListAdapter: RecyclerView.Adapter<FingersListAdapter.ViewHolder>(){

    var imageList = ArrayList<MorphoImage>()

    companion object {
        const val TAG = "FingersListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_finger_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 4 //imageList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //val bitmap = BitmapHelper.byteArrayToBitmap(imageList[position].jpegImage)
        //viewHolder.fingerImage.setImageBitmap(bitmap)
        //viewHolder.fingerName.text = imageList[0].biometricLocation.name
        viewHolder.fingerName.text = "Finger name example"
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val fingerImage = itemView.iv_finger
        val fingerName =  itemView.tv_finger_name
    }
}