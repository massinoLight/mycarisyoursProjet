package com.example.mycarisyours



import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

import kotlinx.android.synthetic.main.voiture_ajout.*
import org.jetbrains.anko.toast


class AjouterVoitureActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    var LaMarque=""
    var LeModele=""
    var Lenergie=""
    var LesVitesses=""





    var IMAGE:Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.voiture_ajout)

        /**
         * la liste de choix de la marque sous forme de spinner
         * */
        val marque : Spinner =findViewById(R.id.spinnerMarque)
        val marqueAdapter: ArrayAdapter<CharSequence> =
            ArrayAdapter.createFromResource(this,R.array.marques,android.R.layout.simple_spinner_item)
        marqueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        marque.setAdapter(marqueAdapter)
        marque.setOnItemSelectedListener(this)



/**
 *
 * selection de la boite a vitesses
 * */
        val automatique:CheckBox = findViewById(R.id.pasannimeaux)
        val manuelle:CheckBox=findViewById(R.id.manuelle)

        automatique.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                manuelle.setChecked(false)
                LesVitesses="automatique"

            }
        }


        manuelle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                automatique.setChecked(false)
                LesVitesses="manuelle"

            }
        }

        /**
         * selection de l'energie du moteur
         * */

        val essence:CheckBox = findViewById(R.id.essence)
        val diesel:CheckBox=findViewById(R.id.diesel)
        val electrique:CheckBox=findViewById(R.id.electrique)

        essence.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                diesel.setChecked(false)
                if(electrique.isChecked){
                    Lenergie="hybride/essence/electrique"
                }
                else
                {
                    Lenergie="essence"
                }


            }
        }


        diesel.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                essence.setChecked(false)

                if(electrique.isChecked){
                    Lenergie="hybride/diesel/electrique"
                }
                else
                {
                    Lenergie="diesel"
                }



            }
        }

/***
 *
 * les  boutons suivant et précedent
 * **/

        suivant.setOnClickListener {

            if ((LaMarque=="")||(LeModele=="")||(Lenergie=="")||(LesVitesses=="")){

                toast("vous avez oublié certaines informations")

            }else
            {
                val intent2 = Intent(this, AjoutVoitureSuitActivity::class.java)




                intent2.putExtra(ConfirmerActivity.EXTRA_MARQUE,LaMarque )
                intent2.putExtra(ConfirmerActivity.EXTRA_MODÉLE,LeModele )
                intent2.putExtra(ConfirmerActivity.EXTRA_ENERGIE,Lenergie )
                intent2.putExtra(ConfirmerActivity.EXTRA_VITESSE,LesVitesses )


                startActivity(intent2)
                finish()

            }

        }

        precedent.setOnClickListener {

            val intent2 = Intent(this, AccueilActivity::class.java)

            startActivity(intent2)
            finish()


        }



    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (parent != null) {
            if(parent.getId() == R.id.spinnerMarque)
            {

                /**
                 * la liste pour choisir le modéle qui va s'adapter selon la marque
                 * */


                LaMarque= parent?.getItemAtPosition(position).toString()
                val  modelAdapter = when(LaMarque) {
                    "WOLKSWAGEN" -> ArrayAdapter.createFromResource(this,R.array.WOLKSWAGEN,android.R.layout.simple_spinner_item)
                    "PEUGEOT" -> ArrayAdapter.createFromResource(this,R.array.PEUGEOT,android.R.layout.simple_spinner_item)
                    "AUDI" -> ArrayAdapter.createFromResource(this,R.array.AUDI,android.R.layout.simple_spinner_item)
                    "BMW" -> ArrayAdapter.createFromResource(this,R.array.BMW,android.R.layout.simple_spinner_item)
                    "CHEVROLET" -> ArrayAdapter.createFromResource(this,R.array.CHEVROLET,android.R.layout.simple_spinner_item)
                    "ALFA ROMEO" -> ArrayAdapter.createFromResource(this,R.array.ALFAROMEO,android.R.layout.simple_spinner_item)
                    "CITROEN" -> ArrayAdapter.createFromResource(this,R.array.CITROEN,android.R.layout.simple_spinner_item)
                    "DACIA" -> ArrayAdapter.createFromResource(this,R.array.DACIA,android.R.layout.simple_spinner_item)
                    "FIAT" -> ArrayAdapter.createFromResource(this,R.array.FIAT,android.R.layout.simple_spinner_item)
                    "FORD" -> ArrayAdapter.createFromResource(this,R.array.FORD,android.R.layout.simple_spinner_item)
                    "HYUNDAI" -> ArrayAdapter.createFromResource(this,R.array.HYUNDAI,android.R.layout.simple_spinner_item)
                    "JEEP" -> ArrayAdapter.createFromResource(this,R.array.JEEP,android.R.layout.simple_spinner_item)
                    "KIA" -> ArrayAdapter.createFromResource(this,R.array.KIA,android.R.layout.simple_spinner_item)
                    "MINI" -> ArrayAdapter.createFromResource(this,R.array.MINI,android.R.layout.simple_spinner_item)
                    "OPEL" -> ArrayAdapter.createFromResource(this,R.array.OPEL,android.R.layout.simple_spinner_item)
                    "SEAT" -> ArrayAdapter.createFromResource(this,R.array.SEAT,android.R.layout.simple_spinner_item)
                    "TOYOTA" -> ArrayAdapter.createFromResource(this,R.array.TOYOTA,android.R.layout.simple_spinner_item)

                    else -> ArrayAdapter.createFromResource(this,R.array.VIDE,android.R.layout.simple_spinner_item)
                }
                val model : Spinner =findViewById(R.id.spinnerModel)

                modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                model.setAdapter(modelAdapter)
                model.setOnItemSelectedListener(this)


            }
            else if(parent.getId() == R.id.spinnerModel)
            {
                LeModele= parent?.getItemAtPosition(position).toString()

            }
        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }





}

