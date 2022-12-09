package com.yash.mycollegeapp.Adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.yash.mycollegeapp.Models.OnBoardingItem
import com.yash.mycollegeapp.R

class ViewPagerAdapter(private val onBoardingItems: List<OnBoardingItem>) :
    RecyclerView.Adapter<ViewPagerAdapter.OnBoardingItemViewHolder>() {

    inner class OnBoardingItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val image : ImageView = itemView.findViewById(R.id.sliderImage)
        private val head : TextView = itemView.findViewById(R.id.sliderHead)
        private val comment : TextView = itemView.findViewById(R.id.sliderComment)

        fun bind(onBoardingItem : OnBoardingItem){
            image.setImageResource(onBoardingItem.onBoardingImage)
            head.text = onBoardingItem.title
            comment.text = onBoardingItem.comment
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider_layout , parent , false))
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }

    override fun getItemCount(): Int = onBoardingItems.size


}