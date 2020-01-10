package com.example.mycarisyours.activites


import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
import kotlinx.android.synthetic.main.style.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import util.BackendSettings
import java.io.*
import java.net.URL
import java.time.LocalDate
import java.util.*

class AccueilActivity : AppCompatActivity() {

    var voitures = mutableListOf<Voiture>()
    var IMAGE: Bitmap? = null

    var lesimage =arrayListOf<Bitmap?>()

    val fixe=arrayListOf("https://images.caradisiac.com/logos-ref/modele/modele--dacia-logan-2/S0-modele--dacia-logan-2.jpg",
        "https://content.carventura.com/50518/medium-50518_1.jpg",
        "https://imganuncios.mitula.net/toyota_yaris_2007_toyota_yaris_ii_1_3_87_vvt_i_navimedia_2900124570460334483.jpg",
        "http://www.ministore-pays-de-loire-automobile.com/161-large_default/mini-cooper-5-portes.jpg"
        )


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

        val les_url = intent.getStringArrayListExtra(ConfirmerActivity.EXTRA_PHOTOS)

        //si rien n'est récupéré on fixe les valeur quand méme pour renvoyer quelque chose
        if (les_url.get(0)==""){
            for (i in 0..(fixe.size-1)) {
                RecuperationImage()
                    .execute(fixe?.get(i))
            }


        }else{

            for (i in 0..(les_url.size-1)) {
                RecuperationImage()
                    .execute(les_url?.get(i))
            }

        }




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
        /***
         *
         * Fonction qui va s'executer en tache de fond pour recupérer les informations du cloud
         * a la fin de son traitement elle va les transmette a la  fonction onPostExecute
         * qui est chargé de l'affichage
         *
         * **/
        override fun doInBackground(vararg params: String?): MutableList<MutableMap<Any?, Any?>>? {

            Log.i("doInBackground", "ca recup les voiture de BL Recuperation() doInBackground")

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

            var h=0

            Log.i("onPostExecute", "ca créer la liste onPostExecute")
            for (msg in result) {
                val p10 = Voiture(
                    "${msg["Bmarque"]}","${msg["Bmodele"]}","${msg["Bdescription"]}",
                    "${msg["Bmatricule"]}","${msg["Bprix"]}",lesimage.get(h))

                h++
                voitures.add(p10)
                voitures.sortWith(compareBy({ it.marque }))
                buildRecyclerView()
                mon_recycler.adapter?.notifyItemInserted(0)

            }


        }

    }

    /***
     * on telecharge  l'image qu'on a stocké sur backendless
     * en http pour l'afficher pour chaque voitures
     *
     * **/

    inner class RecuperationImage() : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            Log.i("doInBackground", "ca recup les images en http avec les url passé en paramétre doInBackground")
            val urlOfImage = urls[0]
            return try {
                val inputStream = URL(urlOfImage).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) { // Catch the download exception
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                Log.i("onPostExecute", "ca affecte les image bitmap a la liste onPostExecute")
                IMAGE=result
                lesimage?.add(IMAGE)

            } else {
                toast("Erreur lors du telechargement")
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

            var  marque="${it.marque}"
            var  modele="${it.modele}"
            var uri=getImageUriFromBitmap(this,it.IMAGE)

            val intent3 = Intent(this, AfficherDetailVehicule::class.java)
            intent3.putExtra(ConfirmerActivity.EXTRA_MARQUE,marque )
            intent3.putExtra(ConfirmerActivity.EXTRA_MODÉLE,modele )
            intent3.putExtra(ConfirmerActivity.EXTRA_PHOTOS,uri.toString())
            startActivity(intent3)



        }


    }


    /****
     *
     * fonction qui permet de passer
     * d'une image Bitmap a une URI
     * ***/

    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap?): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }




}