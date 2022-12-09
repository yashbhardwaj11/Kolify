package com.yash.mycollegeapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yash.mycollegeapp.Models.ProfileButtons
import com.yash.mycollegeapp.R

class ProfileAdapter (val mlist : ArrayList<ProfileButtons> , private val listener : onItemClickListner) : RecyclerView.Adapter<ProfileAdapter.ProfileButtonViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileButtonViewHolder {
        return ProfileButtonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_item , parent,false))
    }

    override fun onBindViewHolder(holder: ProfileButtonViewHolder, position: Int) {
        val current = mlist[position]
        holder.buttonImage.setImageResource(current.buttonImage)
        holder.buttonName.text = current.buttonName
    }

    override fun getItemCount(): Int  = mlist.size

    inner class ProfileButtonViewHolder(view : View) : RecyclerView.ViewHolder(view) , View.OnClickListener{
        val buttonName : TextView = view.findViewById(R.id.profileButtonName)
        val buttonImage : ImageView = view.findViewById(R.id.profileButtonImage)

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION){
                listener.onItemClick(adapterPosition)
            }
        }
    }

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }
}