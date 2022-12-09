package com.yash.mycollegeapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.yash.mycollegeapp.Activities.LoginActivity
import com.yash.mycollegeapp.Adapters.ProfileAdapter
import com.yash.mycollegeapp.Models.ProfileButtons
import com.yash.mycollegeapp.R
import com.yash.mycollegeapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), ProfileAdapter.onItemClickListner {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var adapter : ProfileAdapter
    private lateinit var mlist : ArrayList<ProfileButtons>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        mlist = arrayListOf(
            ProfileButtons(R.drawable.ic_baseline_favorite_24 , "My Ads"),
            ProfileButtons(R.drawable.ic_baseline_bookmark_24 , "My Hackathons"),
            ProfileButtons(R.drawable.ic_baseline_bookmark_24 , "My Applications")
        )

        adapter = ProfileAdapter(mlist , this@ProfileFragment)
        binding.recyclerViewProfile.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewProfile.setHasFixedSize(true)
        binding.recyclerViewProfile.adapter = adapter

        auth = FirebaseAuth.getInstance()

        binding.signOutBT.setOnClickListener {
            auth.signOut()
            val i = Intent(context , LoginActivity::class.java)
            startActivity(i)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {

        when(position){
            0 -> replaceFragment(UsersPostedAdsFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout , fragment)
        fragmentTransaction?.commit()
    }
}