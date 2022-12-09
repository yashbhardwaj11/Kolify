package com.yash.mycollegeapp.Daos

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.yash.mycollegeapp.Models.PostAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostAdDoa {
    private val db = FirebaseFirestore.getInstance()
    val postCollection = db.collection("Ads")
    val root = FirebaseDatabase.getInstance().getReference()
    val auth = FirebaseAuth.getInstance()
    fun postAd(postAd: PostAd?){
        val currentUser = auth.currentUser!!.uid

        postAd?.let {
            GlobalScope.launch(Dispatchers.IO) {
                postCollection.document().set(it)
            }
        }

    }
}