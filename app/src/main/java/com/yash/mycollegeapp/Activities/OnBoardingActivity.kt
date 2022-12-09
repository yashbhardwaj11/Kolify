package com.yash.mycollegeapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.yash.mycollegeapp.Adapters.ViewPagerAdapter
import com.yash.mycollegeapp.MainActivity
import com.yash.mycollegeapp.Models.OnBoardingItem
import com.yash.mycollegeapp.R
import com.yash.mycollegeapp.databinding.ActivityOnBoardingBinding
import kotlinx.coroutines.MainScope

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardingBinding
    private lateinit var onBoardingItemAdapter : ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setOnBoardingItems()


    }


    private fun navigateToHome(){
        startActivity(Intent(this , MainActivity::class.java))
        finish()
    }

    private fun setOnBoardingItems(){
        onBoardingItemAdapter = ViewPagerAdapter(
            listOf(
                OnBoardingItem(
                    R.drawable.firstsliderfragment,
                    "Quick and Easy Sale",
                    "Sell your non usable items to the one who need them. Get rid of making extra space for that item."
                ),
                OnBoardingItem(
                    R.drawable.second,
                    "Get Latest Hackathons Alert",
                    "Get Hackathon Alerts and even you can become a partner and start posting latest Hackathon happenings."
                ),
                OnBoardingItem(
                    R.drawable.third,
                    "Get Internships Alert",
                    "Get Internships Alert ,All the students onboard can post about recent openings and internship opportunities"
                )
            )
        )
        binding.viewPager.adapter = onBoardingItemAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })
        (binding.viewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.nextButton.setOnClickListener{
            if(binding.viewPager.currentItem+1 < onBoardingItemAdapter.itemCount){
                binding.viewPager.currentItem+=1
            }
            else{
                navigateToHome()
            }
        }
        binding.skipButton.setOnClickListener{
            navigateToHome()
        }
    }

}