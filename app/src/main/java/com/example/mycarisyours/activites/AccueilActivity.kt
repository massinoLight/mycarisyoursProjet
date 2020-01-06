package com.example.mycarisyours.activites


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager.widget.ViewPager
import com.backendless.Backendless
import com.backendless.persistence.DataQueryBuilder
import com.example.mycarisyours.R
import com.example.mycarisyours.adapter.Voiture
import com.example.mycarisyours.adapter.VoitureAdapter
import com.example.mycarisyours.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import util.BackendSettings
import java.time.LocalDate

class AccueilActivity : AppCompatActivity() {




    var voitures = mutableListOf<Voiture>()


    @SuppressLint("ServiceCast")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Backendless.initApp(this,
            BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY )


        //val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        //val viewPager: ViewPager = findViewById(R.id.view_pager)
        //viewPager.adapter = sectionsPagerAdapter
        //tabs.setupWithViewPager(viewPager)
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.addTab(tabs.newTab().setText("Voiture"))
        tabs.addTab(tabs.newTab().setText("Vélo/Moto"))
        tabs.addTab(tabs.newTab().setText("Utilitaire"))

        val rec=Recuperation()

        /***
         * verifier si il y a accée a internet si oui
         * faire la récupération de backendless
         * sinn dire a l'utilisateur qu'il n'a pas accé a internet
         *
         * **/
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        when(connMgr.activeNetworkInfo?.type){
            ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE ->
                rec.execute("stp")
            null -> { toast("Pas de réseau") }
        }





        add.setOnClickListener {
            val intent2 = Intent(this, DebutActivity::class.java)
            startActivity(intent2)
            finish()

        }


    }



    inner class Recuperation(): AsyncTask<String, Int, MutableList<MutableMap<Any?, Any?>>>() {

        lateinit var mcontext :Context


          override fun onPreExecute() {
            super.onPreExecute()


        }

        /***
         *
         * Fonction qui va s'executer en tache de fond pour recupérer les informations du cloud
         * a la fin de son traitement elle va les transmette a la  fonction onPostExecute
         * qui est chargé de l'affichage
         *
         * **/
        override fun doInBackground(vararg params: String?): MutableList<MutableMap<Any?, Any?>>? {

            val lesvoitures = Backendless.Data.of("VoitureBackendLess")
                .find(DataQueryBuilder.create().setPageSize(25).setOffset(0))
           return lesvoitures
        }


        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }

        /**
         * Cette fonction va s'executer apres le doInBackground qui est une tache Asy
         * elle va recup le resultat emis par cette dérniére et va les
         * placer dans notre liste et les afficher sur notre recyclerView
         *
         * **/
        override fun onPostExecute(result: MutableList<MutableMap<Any?, Any?>>) {
            super.onPostExecute(result)
            for (msg in result){

        val p10=Voiture("${msg["Bmarque"]}","${msg["Bmodele"]}", "${msg["Benergie"]}","${msg["Bvitesse"]}",
                    "${msg["Bnbplaces"]}","${msg["Bnbportes"]}","${msg["Bfumeur"]}","${msg["Banimeaux"]}","${msg["Bdescription"]}",
                    "${msg["Bmatricule"]}","${msg["Bdatedebut"]}","${msg["Bdatefin"]}","${msg["Bprix"]}",
                    "${msg["Bphotos1"]}","${msg["Bphotos2"]}","${msg["Bphotos3"]}","${msg["Bphotos4"]}")


                voitures.add(p10)
                voitures.sortWith(compareBy({it.marque}))
                buildRecyclerView()
                mon_recycler.adapter?.notifyItemInserted(0)

            }

        }

    }





    fun buildRecyclerView() {
        mon_recycler.setHasFixedSize(true)
        //mon_recycler.setAdapter(mAdapter)
        mon_recycler.layoutManager = LinearLayoutManager(this)

        mon_recycler.adapter = VoitureAdapter(voitures.toTypedArray())
        {
            //ici on affiche juste toutes les informations dans un Toast
            //on aurait tres bien pu les passer en parametre avec un intent et les afficher dans une autre activity



        }


    }


}