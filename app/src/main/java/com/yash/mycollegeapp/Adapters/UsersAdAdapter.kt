package com.yash.mycollegeapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yash.mycollegeapp.Models.PostAd
import com.yash.mycollegeapp.Models.User
import com.yash.mycollegeapp.R

class UsersAdAdapter (val mlist : ArrayList<PostAd> , val listener : OnItemClickListener) : RecyclerView.Adapter<UsersAdAdapter.UserAdViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdViewHolder {
        return UserAdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.users_ad_item , parent,false))
    }

    override fun onBindViewHolder(holder: UserAdViewHolder, position: Int) {
        val current = mlist[position]
        holder.adImage.load(current.adImageUrl){
            crossfade(true)
        }
        holder.adTitle.text = current.adTitle
    }

    override fun getItemCount(): Int = mlist.size

    inner class UserAdViewHolder(view : View) : RecyclerView.ViewHolder(view) , View.OnLongClickListener {
        val adImage : ImageView = view.findViewById(R.id.adImageProfile)
        val adTitle : TextView = view.findViewById(R.id.adTitleProfile)

        init {
            view.setOnLongClickListener(this)
        }

        override fun onLongClick(p0: View?): Boolean {
            if (adapterPosition!=RecyclerView.NO_POSITION){
                listener.onItemClick(adapterPosition)
            }
            return true
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

}