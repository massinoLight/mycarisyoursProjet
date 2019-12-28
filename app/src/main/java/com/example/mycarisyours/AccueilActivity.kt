package com.example.mycarisyours


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.mycarisyours.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.time.LocalDate
import java.util.*

class AccueilActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val valider = intent?.getBooleanExtra(ConfirmerActivity.EXTRA_VALIDER, false) ?: false



        if (valider) {
            //page 1
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

            //page photo

            toast("${marque}"+" ${modele}"+" ${energie}"+" $vitesse"+" $description"+" $datedebut"+" $datefin")

        }



        add.setOnClickListener {
            val intent2 = Intent(this, DebutActivity::class.java)
            startActivity(intent2)
            finish()

        }


    }


}