package com.example.mycarisyours.activites

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.mycarisyours.R
import kotlinx.android.synthetic.main.activity_ajoutoiture_last.*
import org.jetbrains.anko.toast
import java.util.*
import java.time.LocalDate

class AjoutoitureLastActivity : AppCompatActivity() {

    var Matricule=""
     var dateDebut: LocalDate? = null
    var dateFin: LocalDate? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajoutoiture_last)



        val marque = intent.getStringExtra(ConfirmerActivity.EXTRA_MARQUE)
        val modele = intent.getStringExtra(ConfirmerActivity.EXTRA_MODÉLE)
        val energie = intent.getStringExtra(ConfirmerActivity.EXTRA_ENERGIE)
        val vitesse = intent.getStringExtra(ConfirmerActivity.EXTRA_VITESSE)
        val description = intent.getStringExtra(ConfirmerActivity.EXTRA_DESCRIPTION)
        val nbplaces = intent.getStringExtra(ConfirmerActivity.EXTRA_PLACES)
        val nbportes = intent.getStringExtra(ConfirmerActivity.EXTRA_PORTES)
        val fumeur = intent.getStringExtra(ConfirmerActivity.EXTRA_FUMEUR)
        val animeaux = intent.getStringExtra(ConfirmerActivity.EXTRA_ANIMEAUX)







        val calendrier =Calendar.getInstance()

        val annee=calendrier.get(Calendar.YEAR)
        val mois=calendrier.get(Calendar.MONTH)
        val jours=calendrier.get(Calendar.DAY_OF_MONTH)


        date1.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view,  mYear,  mMonth,  mDay ->
                // Display Selected date in TextView

                val lemois:String=when(mMonth) {
                    0 ->"Janvier"
                    1 ->"Février"
                    2 ->"Mars"
                    3 ->"Avril"
                    4 ->"Mai"
                    5 ->"Juin"
                    6 ->"Juillet"
                    7 ->"Aout"
                    8 ->"Séptembre"
                    9 ->"Octobre"
                    10 ->"Novembre"
                    11 ->"Décembre"


                    else ->""
                }


                 dateDebut = LocalDate.of(mYear, mMonth+1, mDay)


                ladateDETV.setText(""+mDay + " " + lemois + " " + mYear)
            }, annee, mois, jours)
            dpd.show()

        }


        date2.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view,  mYear,  mMonth,  mDay ->

                val lemois:String=when(mMonth) {
                    0 ->"Janvier"
                    1 ->"Février"
                    2 ->"Mars"
                    3 ->"Avril"
                    4 ->"Mai"
                    5 ->"Juin"
                    6 ->"Juillet"
                    7 ->"Aout"
                    8 ->"Séptembre"
                    9 ->"Octobre"
                    10 ->"Novembre"
                    11 ->"Décembre"


                    else ->""
                }

                 dateFin = LocalDate.of(mYear, mMonth+1, mDay)


                ladateATV.setText(""+mDay + " " + lemois + " " + mYear)
            }, annee, mois, jours)
            dpd.show()

        }



        /**
         * les boutons suivant et precedent
         *
         * **/



        suivant.setOnClickListener {

            Matricule=imatriculation.getText().toString()



            if ((Matricule=="")||(dateDebut==null)||(dateFin==null)){



                toast("vous avez oublié certaines informations")

            }
            else
            {
                if (!varifierFormatMatricule(Matricule)) {

                    imatriculation.setHintTextColor(Color.RED)
                    imatriculation.setHint("Format incorrect :FAB123CD7575")

                    toast("verifier votre matricule ")

                } else {

                    val compar = dateFin!!.compareTo(dateDebut)
                    if (compar >=0) {

                        val intent2 = Intent(this, AjouterPhotoActivity::class.java)


                        intent2.putExtra(ConfirmerActivity.EXTRA_MARQUE, marque)
                        intent2.putExtra(ConfirmerActivity.EXTRA_MODÉLE, modele)
                        intent2.putExtra(ConfirmerActivity.EXTRA_ENERGIE, energie)
                        intent2.putExtra(ConfirmerActivity.EXTRA_VITESSE, vitesse)
                        intent2.putExtra(ConfirmerActivity.EXTRA_DESCRIPTION, description)
                        intent2.putExtra(ConfirmerActivity.EXTRA_PLACES, nbplaces)
                        intent2.putExtra(ConfirmerActivity.EXTRA_PORTES, nbportes)
                        intent2.putExtra(ConfirmerActivity.EXTRA_FUMEUR, fumeur)
                        intent2.putExtra(ConfirmerActivity.EXTRA_ANIMEAUX, animeaux)
                        intent2.putExtra(ConfirmerActivity.EXTRA_MATRICULE, Matricule)
                        intent2.putExtra(ConfirmerActivity.EXTRA_DATEDEBUT, dateDebut)
                        intent2.putExtra(ConfirmerActivity.EXTRA_DATEFIN, dateFin)



                        startActivity(intent2)

                    } else {
                        toast("Merci de verifier les dates introduite")
                    }

                }
            }

        }


        precedent.setOnClickListener {

            val intent2 = Intent(this, AjoutVoitureSuitActivity::class.java)

            startActivity(intent2)
            finish()


        }

        }

    private fun varifierFormatMatricule(m:String):Boolean{
        /**
         * le format des plaques d'immatriculations
         * en france est sous forme
         * F-AB-123-CD-75
          * */
        var bien=true

        var c='a'


        if (m.length != 10){

            bien=false
        }
        else {

            //on verifie le 1er caractére si c'est bien F pour France
            if ((m.first() !='F')&&(m.first() !='f')){

                bien=false
            }

            //on verifie les 2 caractéres si c'est des lettres
            c=m.get(1)
            if (!lettre(c)){
                bien=false

            }

            c=m.get(2)
            if (!lettre(c)){
                bien=false

            }

            //on verifie si les trois chiffres sont présent
            c=m.get(3)
            if (!chiffre(c)){
                bien=false

            }

            c=m.get(4)
            if (!chiffre(c)){
                bien=false

            }
            c=m.get(5)
            if (!chiffre(c)){
                bien=false

            }

            //on verifie les 2 derniéres lettres
            c=m.get(6)
            if (!lettre(c)){
                bien=false

            }

            c=m.get(7)
            if (!lettre(c)){
                bien=false

            }

            //on verifie les deux chiffres du département
            c=m.get(8)
            if (!chiffre(c)){
                bien=false

            }

            c=m.get(9)
            if (!chiffre(c)){
                bien=false

            }

        }


   return bien
    }


}

//fonction pour verifier les lettres
private fun lettre(m:Char):Boolean{
    var boo=false
    if ((m=='A')||(m=='Z')||(m=='E')||(m=='R')||(m=='T')||(m=='Y')||(m=='U')||(m=='I')||(m=='O')||(m=='P')||(m=='Q')||(m=='S')||(m=='D')
        ||(m=='F')||(m=='G')||(m=='H')||(m=='J')||(m=='K')||(m=='L')||(m=='M')||(m=='W')||(m=='X')||(m=='C')||(m=='V')||(m=='B')||(m=='N')
        ||(m=='a')||(m=='z')||(m=='e')||(m=='r')||(m=='t')||(m=='y')||(m=='u')||(m=='i')||(m=='o')||(m=='p')||(m=='q')||(m=='s')||(m=='d')||(m=='f')
        ||(m=='g')||(m=='h')||(m=='j')||(m=='k')||(m=='l')||(m=='m')||(m=='w')||(m=='x')||(m=='c')||(m=='c')||(m=='v')||(m=='b')||(m=='n'))
    {
       boo=true

    }
    return  boo

}

//fonction pour verifier les chiffres
private fun chiffre(m:Char):Boolean{

    var boo=false

    if (((m=='0')||(m=='1')||(m=='2')||(m=='3'))||(m=='4')||(m=='5')||(m=='6')||(m=='7')||(m=='8')||(m=='9'))
    {
        boo=true

    }

return boo
}