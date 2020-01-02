package com.example.mycarisyours.activites


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.viewpager.widget.ViewPager
import com.example.mycarisyours.R
import com.example.mycarisyours.adapter.Voiture
import com.example.mycarisyours.adapter.VoitureAdapter
import com.example.mycarisyours.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class AccueilActivity : AppCompatActivity() {

    var voitures = mutableListOf<Voiture>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        //val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        //val viewPager: ViewPager = findViewById(R.id.view_pager)
        //viewPager.adapter = sectionsPagerAdapter
        //tabs.setupWithViewPager(viewPager)
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs!!.addTab(tabs!!.newTab().setText("Voiture"))
        tabs!!.addTab(tabs!!.newTab().setText("Vélo/Moto"))
        tabs!!.addTab(tabs!!.newTab().setText("Utilitaire"))

        val valider = intent?.getBooleanExtra(ConfirmerActivity.EXTRA_VALIDER, false) ?: false



        if (valider) {

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
            val prix = intent.getStringExtra(ConfirmerActivity.EXTRA_PRIX)

            //page 4

            val photos = intent.getStringArrayListExtra(ConfirmerActivity.EXTRA_PHOTOS)

            var v=Voiture(marque,modele,energie,vitesse,
                description,nbplaces,nbportes,fumeur,animeaux,
                matricule,datedebut,datefin,prix,photos)


            voitures.add(v)
            buildRecyclerView()
            /*
            val premier=photos.get(1)
            toast("$marque"+"$premier")*/


        }



        add.setOnClickListener {
            val intent2 = Intent(this, DebutActivity::class.java)
            startActivity(intent2)
            finish()

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