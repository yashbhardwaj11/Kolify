package com.yash.mycollegeapp.Activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.yash.mycollegeapp.MainActivity
import com.yash.mycollegeapp.R

class SplashScreenActivity : AppCompatActivity() {

    lateinit var onBoardingScreen : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        Handler().postDelayed({
            onBoardingScreen = getSharedPreferences("onBoardingScreen" , MODE_PRIVATE)
            val isfirstTime : Boolean = onBoardingScreen.getBoolean("firstTime",true)


            if (isfirstTime){


                val editor : SharedPreferences.Editor = onBoardingScreen.edit()
                editor.putBoolean("firstTime",false)
                editor.commit()

                val i = Intent(this,onBoardingScreen::class.java)
                startActivity(i)
                finish()

            }
            else{
                val i = Intent(this,LoginActivity::class.java)
                startActivity(i)
                finish()
            }


        },3000L)

    }
}