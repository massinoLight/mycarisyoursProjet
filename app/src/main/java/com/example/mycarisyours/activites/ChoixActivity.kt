package com.example.mycarisyours.activites



import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.persistence.DataQueryBuilder
import com.example.mycarisyours.R
import com.example.mycarisyours.adapter.Voiture
import com.example.mycarisyours.map.MapsActivity

import kotlinx.android.synthetic.main.activity_choix.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.ArrayList

class ChoixActivity : AppCompatActivity() {

    var lesurl = arrayListOf<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix)

        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        when(connMgr.activeNetworkInfo?.type){
            ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE ->{
                Log.e("EEEEEEEEEEE", "on va faire appel a la tache Asy!!!!")
                RecuperationUrlImage().execute("stp")}
            null -> { toast("verifier votre connexion internet") }
        }



        trouver.setOnClickListener {

            val intent = Intent(this, MapsActivity::class.java)

            startActivity(intent)



        }

        proposer.setOnClickListener {

            val intent = Intent(this, AccueilActivity::class.java)
            intent.putStringArrayListExtra(ConfirmerActivity.EXTRA_PHOTOS, lesurl as ArrayList<String?>)

            startActivity(intent)



        }


    }

/****
 *
 * on récup les images  dejas contenu dans  BackendLess avant de lancer
 * le  recyclerView
 * car sinon il y aura un décalage entre
 * l'affichage des informations  du véhicule et des images
 *
 * **/
    inner class RecuperationUrlImage(): AsyncTask<String, Int, MutableList<MutableMap<Any?, Any?>>>() {

        override fun doInBackground(vararg params: String?): MutableList<MutableMap<Any?, Any?>>? {

            var lesvoitures = Backendless.Data.of("VoitureBackendLess")
                .find(DataQueryBuilder.create().setPageSize(25).setOffset(0))
            Log.e("EEEEEEEEEEE", "on a recup les voitures du cloud")
            return lesvoitures
        }


        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }


        override fun onPostExecute(result: MutableList<MutableMap<Any?, Any?>>) {
            super.onPostExecute(result)
            var i=0
            for (msg in result) {
                lesurl?.add("${msg["Bphoto1"]}")
                Log.e("EEEEEEEEEEE", "on a affecté les photos a la liste a l'indice ${i}  ${msg["Bphoto1"]}")
                i++

            }
        }

    }




}