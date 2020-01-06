package com.example.mycarisyours.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.backendless.Backendless
import com.example.mycarisyours.R
import com.example.mycarisyours.adapter.Voiture
import kotlinx.android.synthetic.main.activity_confirmer.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import util.BackendSettings
import java.time.LocalDate
import java.util.*


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
                              var Bmatricule: String = "",
    // var Bdatedebut: String = "",
    // var Bdatefin: String = "",
                              var Bprix: String = ""
    // var Bphotos1: String = "",
    // var Bphotos2: String = "",
    // var Bphotos3: String = "",
    // var Bphotos4: String = ""
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
        const val EXTRA_PHOTOS = "Vehicule.photos"

    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmer)

        Backendless.initApp(this,
            BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY )

        //page 1
        val marque = intent.getStringExtra(EXTRA_MARQUE)
        val modele = intent.getStringExtra(EXTRA_MODÉLE)
        val energie = intent.getStringExtra(EXTRA_ENERGIE)
        val vitesse = intent.getStringExtra(EXTRA_VITESSE)

        //page 2
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val nbplaces = intent.getStringExtra(EXTRA_PLACES)
        val nbportes = intent.getStringExtra(EXTRA_PORTES)
        val fumeur = intent.getStringExtra(EXTRA_FUMEUR)
        val animeaux = intent.getStringExtra(EXTRA_ANIMEAUX)

        //page 3
        val matricule = intent.getStringExtra(EXTRA_MATRICULE)
        val datedebut = intent.getSerializableExtra(EXTRA_DATEDEBUT) as LocalDate
        val datefin = intent.getSerializableExtra(EXTRA_DATEFIN) as LocalDate
        val prix = intent.getStringExtra(ConfirmerActivity.EXTRA_PRIX)

        //page 4

        val photos = intent.getStringArrayListExtra(EXTRA_PHOTOS)



        Confirmer.setOnClickListener {

            /******
             *
             * la partie stockage sur le cloud
             *
             * **/

            val voi = VoitureBackendLess(
                null, marque, modele,
                description, matricule, prix)

            doAsync {
                Backendless.Persistence
                    .of(VoitureBackendLess::class.java).save(voi)
            }

            toast("données bien ajouté au clound")
            val intent2 = Intent(this, AccueilActivity::class.java)
            startActivity(intent2)

            finish()

        }


    }
}
