package com.example.mycarisyours

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.mycarisyours.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.ed_email

import org.jetbrains.anko.toast
import util.BackendSettings

class MainActivity : AppCompatActivity() {

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Backendless.initApp(this,
            BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY )


        log.setOnClickListener {



            val mail=ed_email.text.toString()
            val mdp=ed_mdp.text.toString()

            val intent1 = Intent(this, ChoixActivity::class.java)



            val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            when (connMgr.activeNetworkInfo?.type) {
                ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE ->{
                    Backendless.UserService.login(mail,mdp,
                        object : AsyncCallback<BackendlessUser> {
                            override fun handleResponse(user: BackendlessUser?) {
                                toast("bienvenu")
                                startActivity(intent1)
                                finish()

                            }

                            override fun handleFault(fault: BackendlessFault?) {
                                toast("login ou mot de passe incorrect ")
                            }
                        })

                }


                    null -> {

                toast("pas de réseau")
            }


            }

        }

     signUp.setOnClickListener {

        val intent2 = Intent(this, SigupActivity::class.java)

            startActivity(intent2)


      }


    }









}