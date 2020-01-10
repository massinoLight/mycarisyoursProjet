package com.example.mycarisyours.activites

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.backendless.Backendless
import com.backendless.persistence.DataQueryBuilder
import com.example.mycarisyours.R
import com.example.mycarisyours.adapter.Voiture
import kotlinx.android.synthetic.main.activity_confirmer.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import util.BackendSettings
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


data class VoitureBackendLess(var objectId: String? = null,
                              var Bmarque:String="",
                              var Bmodele:String="",
    //var Benergie:String="",
    //var Bvitesse:String="",
                              var Bdescription:String="",
    // var Bnbplaces: String = "",
    // var Bnbportes: String = "",
    // var Bfumeur: String = "",
    // var Banimeaux: String = "",
                              var Bmatricule: String = ""
    // var Bdatedebut: String = "",
    // var Bdatefin: String = "",
                              //var Bprix: String = "",
                              //var Bphoto1: String = ""
    // var Bphoto2: String = "",
    // var Bphoto3: String = "",
    // var Bphoto4: String = ""
)

class ConfirmerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_VALIDER = "Vehicule.valider"
        const val EXTRA_MARQUE = "Vehicule.marque"
        const val EXTRA_MODÉLE = "Vehicule.modéle"
        const val EXTRA_ENERGIE = "Vehicule.energie"
        const val EXTRA_VITESSE = "Vehicule.vitesse"
        //2EME PAGE
        const val EXTRA_PLACES = "Vehicule.places"
        const val EXTRA_PORTES = "Vehicule.portes"
        const val EXTRA_DESCRIPTION = "Vehicule.description"
        const val EXTRA_FUMEUR = "Vehicule.fumeur"
        const val EXTRA_ANIMEAUX = "Vehicule.animeaux"

        //3EME PAGE
        const val EXTRA_MATRICULE = "Vehicule.matricule"
        const val EXTRA_DATEDEBUT = "Vehicule.datedebut"
        const val EXTRA_DATEFIN = "Vehicule.datefin"
        const val EXTRA_PRIX = "Vehicule.prix"

        //4eme page
        const val EXTRA_DERNIERE_PHOTOS = "Vehicule.dernierephotos"
        const val EXTRA_PHOTOS = "Vehicule.photos"

    }



    var lesurl = arrayListOf<String?>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmer)

        Backendless.initApp(this,
            BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY )

        //page 1
        val marque = intent.getStringExtra(EXTRA_MARQUE)?:""
        val modele = intent.getStringExtra(EXTRA_MODÉLE)?:""
        //page 2
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)?:""
        //page 3
        val matricule = intent.getStringExtra(EXTRA_MATRICULE)?:""
        val prix = intent.getStringExtra(EXTRA_PRIX)?:""
        //page 4
        var derniérePhoto = intent.getStringExtra(EXTRA_DERNIERE_PHOTOS) ?: "https://img.autoplus.fr/picture/fiat/panda/1508621/Fiat_Panda_2017_c4305-1600-1108.jpg?r"


        if (derniérePhoto==""){
            derniérePhoto="https://img.autoplus.fr/picture/fiat/panda/1508621/Fiat_Panda_2017_c4305-1600-1108.jpg?r"

        }

        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        when(connMgr.activeNetworkInfo?.type){
            ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE ->
                RecuperationUrlImage().execute("stp")
            null -> { toast("verifier votre connexion internet") }
        }


        lesurl.add(derniérePhoto)
        Confirmer.setOnClickListener {

            /******
             * la partie stockage sur le cloud
             ** **/

            val voi = VoitureBackendLess(
                null, marque, modele,
                description, matricule)
            Log.e("EEEEEEEEEEE", "on a affecté les valeurs a notre obj et on s appréte a le passer au cloud")


            doAsync {
                Log.e("EEEEEEEEEEE", "on rentre dans le doAsync et on ajoute la voiture au cloud")
                Backendless.Persistence
                    .of(VoitureBackendLess::class.java).save(voi)
            }

            Log.e("EEEEEEEEEEE", "on est sortie de du thread la voiture doit etre ajouté au cloud")


            val intent2 = Intent(this, AccueilActivity::class.java)
            intent2.putStringArrayListExtra(ConfirmerActivity.EXTRA_PHOTOS, lesurl as ArrayList<String?>)
            startActivity(intent2)

            finish()

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

            val lesvoitures = Backendless.Data.of("VoitureBackendLess")
                .find(DataQueryBuilder.create().setPageSize(25).setOffset(0))
            return lesvoitures
        }


        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
        }


        override fun onPostExecute(result: MutableList<MutableMap<Any?, Any?>>) {
            super.onPostExecute(result)
            for (msg in result) {
                lesurl?.add("${msg["Bphoto1"]}")

            }
        }

    }


}
