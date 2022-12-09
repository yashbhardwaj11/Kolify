package com.yash.mycollegeapp.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.yash.mycollegeapp.Daos.PostAdDoa
import com.yash.mycollegeapp.Models.PostAd
import com.yash.mycollegeapp.R
import com.yash.mycollegeapp.databinding.FragmentPostAdsBinding
import java.net.URI

class PostAdsFragment : Fragment() {

    private var _binding : FragmentPostAdsBinding? = null
    private val binding get() = _binding!!
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var storageRef : StorageReference = FirebaseStorage.getInstance().reference.child("AdsImages")
    private var firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var imageUri : Uri? = null
    private var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var ref : DatabaseReference = database.getReference()
    private lateinit var postData : PostAd


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostAdsBinding.inflate(layoutInflater, container, false)

        registerClickEvent()

        binding.backButton.setOnClickListener{
            replaceFragment(AdsFragment())
        }

        return binding.root
    }

    private fun registerClickEvent() {
        binding.postAd.setOnClickListener {
            val title = binding.AdTitle.text.toString().trim()
            val desc = binding.AdDesc.text.toString().trim()
            val price = binding.AdPrice.text.toString().trim()

            
            if (imageUri!=null){
                if (title.isNotEmpty() && price.isNotEmpty() && desc.isNotEmpty()){
                    uploadData()
                }
                else{
                    Toast.makeText(context, "All Fields Are Required", Toast.LENGTH_SHORT).show()
                }   
            }
            else{
                Toast.makeText(context, "Please Select Image", Toast.LENGTH_SHORT).show()
            }
            
        }
        binding.chooseImage.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private fun uploadData() {
        binding.progressbar.visibility = View.VISIBLE
        binding.postAd.visibility = View.GONE

        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener{
                if (it.isSuccessful){
                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        val title = binding.AdTitle.text.toString().trim()
                        val desc = binding.AdDesc.text.toString().trim()
                        val price = binding.AdPrice.text.toString().trim()
                        val currentTime = System.currentTimeMillis()

                        postData = PostAd(auth.uid, uri.toString(), title, desc, price, currentTime)
                        val postDataID = ref.push().key
                        val postAdDoa = PostAdDoa()
                        postAdDoa.postAd(postData)
                        ref.child("usersAds").child(auth.uid!!).child(title).setValue(postData)


                    }.addOnCompleteListener {
                        replaceFragment(AdsFragment())
                    }
                }
                else{
                    binding.progressbar.visibility = View.GONE
                    binding.postAd.visibility = View.VISIBLE
                    Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frameLayout,fragment)
        fragmentTransaction?.commit()
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()){
        imageUri = it
        binding.chooseImage.setImageURI(it)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}