package com.example.mycarisyours



import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.voiture_ajout.*
import org.jetbrains.anko.toast


class AjouterVoitureActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {



    companion object {
        const val EXTRA_VALIDER = "AjouterPersonne.valider"
        const val EXTRA_NOM = "AjouterPersonne.nom"
        const val EXTRA_EMAIL = "AjouterPersonne.email"
        const val EXTRA_TEL = "AjouterPersonne.tel"
        const val EXTRA_FAXE = "AjouterPersonne.fixe"
        const val EXTRA_BATERIE = "AjouterPersonne.Labaterie"
    }

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




        suivant.setOnClickListener {

            val intent2 = Intent(this, SigupActivity::class.java)

            startActivity(intent2)
        }

        precedent.setOnClickListener {

            val intent2 = Intent(this, ChoixActivity::class.java)

            startActivity(intent2)
            finish()


        }



    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var laMarque:String= parent?.getItemAtPosition(position).toString()

        /**
         * la liste pour choisir le modéle qui va s'adapter selon la marque
         * */

        val model : Spinner =findViewById(R.id.spinnerModel)

        val  modelAdapter = when(laMarque) {
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

        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        model.setAdapter(modelAdapter)



    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }





}

