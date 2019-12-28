package com.example.mycarisyours

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_confirmer.*
import java.time.LocalDate
import java.util.*

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

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmer)

        //page 1
        val marque = intent.getStringExtra(ConfirmerActivity.EXTRA_MARQUE)
        val modele = intent.getStringExtra(ConfirmerActivity.EXTRA_MODÉLE)
        val energie = intent.getStringExtra(ConfirmerActivity.EXTRA_ENERGIE)
        val vitesse = intent.getStringExtra(ConfirmerActivity.EXTRA_VITESSE)

        //page 2
        val description = intent.getStringExtra(ConfirmerActivity.EXTRA_DESCRIPTION)
        val nbplaces = intent.getStringExtra(ConfirmerActivity.EXTRA_PLACES)
        val nbportes = intent.getStringExtra(ConfirmerActivity.EXTRA_PORTES)
        val fumeur = intent.getStringExtra(ConfirmerActivity.EXTRA_FUMEUR)
        val animeaux = intent.getStringExtra(ConfirmerActivity.EXTRA_ANIMEAUX)

        //page 3
        val matricule = intent.getStringExtra(ConfirmerActivity.EXTRA_MATRICULE)
        val datedebut = intent.getSerializableExtra(ConfirmerActivity.EXTRA_DATEDEBUT) as LocalDate
        val datefin = intent.getSerializableExtra(ConfirmerActivity.EXTRA_DATEFIN) as LocalDate



        Confirmer.setOnClickListener {



            val intent2 = Intent(this, AccueilActivity::class.java)


            intent2.putExtra(ConfirmerActivity.EXTRA_VALIDER,true )
            intent2.putExtra(ConfirmerActivity.EXTRA_MARQUE,marque )
            intent2.putExtra(ConfirmerActivity.EXTRA_MODÉLE,modele )
            intent2.putExtra(ConfirmerActivity.EXTRA_ENERGIE,energie )
            intent2.putExtra(ConfirmerActivity.EXTRA_VITESSE,vitesse )
            intent2.putExtra(ConfirmerActivity.EXTRA_DESCRIPTION,description )
            intent2.putExtra(ConfirmerActivity.EXTRA_PLACES, nbplaces)
            intent2.putExtra(ConfirmerActivity.EXTRA_PORTES,nbportes )
            intent2.putExtra(ConfirmerActivity.EXTRA_FUMEUR, fumeur)
            intent2.putExtra(ConfirmerActivity.EXTRA_ANIMEAUX, animeaux)
            intent2.putExtra(ConfirmerActivity.EXTRA_MATRICULE,matricule )
            intent2.putExtra(ConfirmerActivity.EXTRA_DATEDEBUT,datedebut )
            intent2.putExtra(ConfirmerActivity.EXTRA_DATEFIN,datefin )
            //intent2.putExtra("EXTRA_PHOTO",image_uri )
            intent2.putExtra("EXTRA_CONFIRMER",true )


            startActivity(intent2)

            finish()




        }







    }
}
