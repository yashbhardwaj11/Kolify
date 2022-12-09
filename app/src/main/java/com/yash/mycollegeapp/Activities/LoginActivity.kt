package com.yash.mycollegeapp.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.yash.mycollegeapp.Daos.UserDao
import com.yash.mycollegeapp.MainActivity
import com.yash.mycollegeapp.Models.User
import com.yash.mycollegeapp.R
import com.yash.mycollegeapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN : Int = 123
    lateinit var binding : ActivityLoginBinding
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient =GoogleSignIn.getClient(this,gso)
        binding.googleSignInButton.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
            if (result.resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResult(task)
            }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account!=null){
                firebaseAuthWithGoogle(account)
            }

        }
        else{
            Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        binding.googleSignInButton.visibility = View.GONE
        binding.progressbar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            auth.signInWithCredential(credential).await()
            val firebaseUser = auth.currentUser
            withContext(Dispatchers.Main){
                updateUI(firebaseUser)
            }
        }

    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if (firebaseUser!=null){
            val user = User(firebaseUser.uid , firebaseUser.displayName , firebaseUser.photoUrl.toString())
            val userDao = UserDao()
            userDao.addUser(user)
            val i = Intent(this , MainActivity::class.java)
            finish()
            startActivity(i)
        }
        else{
            binding.googleSignInButton.visibility = View.VISIBLE
            binding.progressbar.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        updateUI(user)
    }
}