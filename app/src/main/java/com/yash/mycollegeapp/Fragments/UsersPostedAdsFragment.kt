package com.yash.mycollegeapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.yash.mycollegeapp.Adapters.UsersAdAdapter
import com.yash.mycollegeapp.Daos.PostAdDoa
import com.yash.mycollegeapp.Models.PostAd
import com.yash.mycollegeapp.R
import com.yash.mycollegeapp.databinding.FragmentPostAdsBinding
import com.yash.mycollegeapp.databinding.FragmentUsersPostedAdsBinding

class UsersPostedAdsFragment : Fragment(), UsersAdAdapter.OnItemClickListener {

    private var _binding : FragmentUsersPostedAdsBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseDatabase
    private lateinit var root : DatabaseReference
    private lateinit var mlist : ArrayList<PostAd>
    private lateinit var adsDao : PostAdDoa

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersPostedAdsBinding.inflate(layoutInflater , container, false )

        auth = FirebaseAuth.getInstance()

        mlist = arrayListOf()
        binding.recyclerViewPostedAds.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPostedAds.setHasFixedSize(true)

        binding.progressbar.visibility = View.VISIBLE
        binding.recyclerViewPostedAds.visibility = View.GONE
        binding.totalAds.visibility = View.GONE

        adsDao = PostAdDoa()

        getData()
        return binding.root
    }

    private fun getData() {
        root = FirebaseDatabase.getInstance().getReference("usersAds").child(auth.currentUser!!.uid)
        val adsCollection = adsDao.postCollection
        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (data in snapshot.children){
                        val adData = data.getValue(PostAd::class.java)
                        mlist.add(adData!!)
                        binding.totalAds.text = "Total Ads posted : ${mlist.size}"
                    }
                    binding.recyclerViewPostedAds.adapter = UsersAdAdapter(mlist,this@UsersPostedAdsFragment)
                    binding.progressbar.visibility = View.GONE
                    binding.recyclerViewPostedAds.visibility = View.VISIBLE
                    binding.totalAds.visibility = View.VISIBLE
                }
                else{
                    Toast.makeText(context,"Nothing to show", Toast.LENGTH_SHORT).show()
                    binding.progressbar.visibility = View.GONE
                    binding.recyclerViewPostedAds.visibility = View.VISIBLE
                    binding.totalAds.visibility = View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed due to ${error.message.toString()}", Toast.LENGTH_SHORT).show()
                binding.progressbar.visibility = View.GONE
                binding.recyclerViewPostedAds.visibility = View.VISIBLE
                binding.totalAds.visibility = View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context, "this is deleting", Toast.LENGTH_SHORT).show()
        val current = mlist[position]

        root = FirebaseDatabase.getInstance().getReference("usersAds").child(auth.currentUser!!.uid).child(
            current.adTitle.toString()
        )
        root.setValue(null)

        Toast.makeText(context, "Swipe down to refresh", Toast.LENGTH_SHORT).show()

    }



}