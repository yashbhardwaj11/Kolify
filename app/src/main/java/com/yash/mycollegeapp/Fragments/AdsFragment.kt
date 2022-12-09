package com.yash.mycollegeapp.Fragments

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import com.yash.mycollegeapp.AdsAdapter
import com.yash.mycollegeapp.Daos.PostAdDoa
import com.yash.mycollegeapp.Models.PostAd
import com.yash.mycollegeapp.R
import com.yash.mycollegeapp.databinding.FragmentAdsBinding

class AdsFragment : Fragment() {

    private var _binding : FragmentAdsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : AdsAdapter
    private lateinit var postAdDoa: PostAdDoa
    private lateinit var mlist : ArrayList<PostAd>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdsBinding.inflate(layoutInflater, container, false)

        val swipeRefreshLayout : SwipeRefreshLayout = binding.refreshLayout

        binding.postAdBT.setOnClickListener{
            replaceFragment(PostAdsFragment())
        }

        swipeRefreshLayout.setOnRefreshListener {
            mlist.clear()
            setUpRecyclerAdapter()

            binding.refreshLayout.isRefreshing = false
        }

        mlist = arrayListOf()

        postAdDoa = PostAdDoa()
        setUpRecyclerAdapter()
        adapter = AdsAdapter(mlist)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        return binding.root
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout , fragment)
        fragmentTransaction?.commit()
    }

    private fun setUpRecyclerAdapter() {
        val postCollection = postAdDoa.postCollection
        val query = postCollection.orderBy("postedOn" , Query.Direction.DESCENDING)

        val db = FirebaseFirestore.getInstance()
//        db.collection("Ads")

            query.addSnapshotListener(object : EventListener<QuerySnapshot?> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.d("FireStore" , error.message.toString())
                    Toast.makeText(context, "FireStore Error", Toast.LENGTH_SHORT).show()
                }

                for (dc : DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        mlist.add(dc.document.toObject(PostAd::class.java))
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}