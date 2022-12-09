package com.yash.mycollegeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yash.mycollegeapp.Models.PostAd

class AdsAdapter (private val mlist : ArrayList<PostAd>) : RecyclerView.Adapter<AdsAdapter.AdsAdapterViewHolder>() {
    inner class AdsAdapterViewHolder(var view : View) : RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.postItemImage)
        val price = view.findViewById<TextView>(R.id.postItemPrice)
        val title = view.findViewById<TextView>(R.id.postItemTitle)
        val date = view.findViewById<TextView>(R.id.postItemDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsAdapterViewHolder {
        return AdsAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ad_item , parent,false))
    }

    override fun onBindViewHolder(holder: AdsAdapter.AdsAdapterViewHolder, position: Int) {
        val current = mlist[position]
        holder.title.text = current.adTitle
        holder.price.text = "₹ ${current.adPrice}"
        holder.image.load(current.adImageUrl){
            crossfade(true)
            placeholder(R.drawable.placeholder2)
        }
        holder.date.text = Utils.getTimeAgo(current.postedOn!!)
    }

    override fun getItemCount(): Int = mlist.size
}