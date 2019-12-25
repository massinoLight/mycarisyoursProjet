package com.example.mycarisyours

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_ajoutoiture_last.*
import java.util.*

class AjoutoitureLastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajoutoiture_last)

        val matric: EditText = findViewById(R.id.imatriculation)

        matric.setOnClickListener{
            matric.getText().clear()

            //description=desc.getText().toString()

        }


        val calendrier =Calendar.getInstance()

        val annee=calendrier.get(Calendar.YEAR)
        val mois=calendrier.get(Calendar.MONTH)
        val jours=calendrier.get(Calendar.DAY_OF_MONTH)


        date1.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view,  mYear,  mMonth,  mDay ->
                // Display Selected date in TextView
                ladateDETV.setText(""+mDay + "/" + (mMonth+1) + "/" + mYear)
            }, annee, mois, jours)
            dpd.show()

        }


        date2.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view,  mYear,  mMonth,  mDay ->
                // Display Selected date in TextView
                ladateATV.setText(""+mDay + "/" + (mMonth+1) + "/" + mYear)
            }, annee, mois, jours)
            dpd.show()

        }



        /**
         * les boutons suivant et precedent
         *
         * **/



        suivant.setOnClickListener {

            val intent2 = Intent(this, AjouterPhotoActivity::class.java)

            startActivity(intent2)
        }


        precedent.setOnClickListener {

            val intent2 = Intent(this, AjoutVoitureSuitActivity::class.java)

            startActivity(intent2)
            finish()


        }





        }


    }

