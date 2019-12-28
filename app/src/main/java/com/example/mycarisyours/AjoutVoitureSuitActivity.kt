package com.example.mycarisyours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_ajout_voiture_suit.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*
import org.jetbrains.anko.toast


class AjoutVoitureSuitActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{


    var Description=""
    var nbPlaces=""
    var nbPortes=""
    var cigarette=""
    var toutou=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajout_voiture_suit)


        val marque = intent.getStringExtra(ConfirmerActivity.EXTRA_MARQUE)
        val modele = intent.getStringExtra(ConfirmerActivity.EXTRA_MODÉLE)
        val energie = intent.getStringExtra(ConfirmerActivity.EXTRA_ENERGIE)
        val vitesse = intent.getStringExtra(ConfirmerActivity.EXTRA_VITESSE)






        //le spinner de nombre de places
        val places : Spinner =findViewById(R.id.spinnerPlaces)
        val placesAdapter: ArrayAdapter<CharSequence> =
            ArrayAdapter.createFromResource(this,R.array.place,android.R.layout.simple_spinner_item)
        placesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        places.setAdapter(placesAdapter)
        places.setOnItemSelectedListener(this)



        /**
         * accepte les fumeurs ou non
         * */

        val fumeur: CheckBox = findViewById(R.id.fumeur)
        val nonfumeur: CheckBox =findViewById(R.id.nonfumeur)

        fumeur.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                nonfumeur.setChecked(false)
                cigarette="oui"

            }
        }


        nonfumeur.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                fumeur.setChecked(false)
                cigarette="non"

            }
        }



        /**
         * accepte le annimeaux ou pas
         * */
        val animeaux: CheckBox = findViewById(R.id.animeaux)
        val pasanimeaux: CheckBox =findViewById(R.id.pasannimeaux)

        animeaux.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                pasanimeaux.setChecked(false)
                toutou="oui"

            }
        }


        pasanimeaux.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                animeaux.setChecked(false)
                toutou="non"

            }
        }


        //le spinner de nombre de portes

        val portes : Spinner =findViewById(R.id.spinnerPortes)
        val portesAdapter: ArrayAdapter<CharSequence> =
            ArrayAdapter.createFromResource(this,R.array.porte,android.R.layout.simple_spinner_item)
        portesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        portes.setAdapter(portesAdapter)
        portes.setOnItemSelectedListener(this)



        /**
         * les boutons suivant et precedent
         *
         * **/



        suivant.setOnClickListener {


            Description=description.getText().toString()

            if ((nbPlaces=="")||(nbPortes=="")||(cigarette=="")||(toutou=="")){

                toast("vous avez oublié certaines informations")

            }else
            {

                val intent2 = Intent(this, AjoutoitureLastActivity::class.java)

                intent2.putExtra(ConfirmerActivity.EXTRA_MARQUE,marque )
                intent2.putExtra(ConfirmerActivity.EXTRA_MODÉLE,modele )
                intent2.putExtra(ConfirmerActivity.EXTRA_ENERGIE,energie )
                intent2.putExtra(ConfirmerActivity.EXTRA_VITESSE,vitesse )
                intent2.putExtra(ConfirmerActivity.EXTRA_DESCRIPTION,Description )
                intent2.putExtra(ConfirmerActivity.EXTRA_PLACES, nbPlaces)
                intent2.putExtra(ConfirmerActivity.EXTRA_PORTES,nbPortes )
                intent2.putExtra(ConfirmerActivity.EXTRA_FUMEUR, cigarette)
                intent2.putExtra(ConfirmerActivity.EXTRA_ANIMEAUX, toutou)



                startActivity(intent2)

            }


        }

        precedent.setOnClickListener {

            val intent2 = Intent(this, AjouterVoitureActivity::class.java)

            startActivity(intent2)
            finish()


        }


    }




    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


        if (parent != null) {
            if(parent.getId() == R.id.spinnerPlaces)
            {
                nbPlaces = parent.getItemAtPosition(position).toString()
            }
            else if(parent.getId() == R.id.spinnerPortes)
            {
                nbPortes = parent.getItemAtPosition(position).toString()
            }
        }



    }




    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
