package com.example.mycarisyours.activites

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mycarisyours.R
import kotlinx.android.synthetic.main.activity_afficher_detail_vehicule.*

class AfficherDetailVehicule : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afficher_detail_vehicule)


        val marque = intent.getStringExtra(ConfirmerActivity.EXTRA_MARQUE)
        val modele = intent.getStringExtra(ConfirmerActivity.EXTRA_MODÉLE)
        val photo =  Uri.parse(intent.getStringExtra(ConfirmerActivity.EXTRA_PHOTOS))


        val marqueModele=marque+"     "+modele

        textViewModeleMarque.text=marqueModele
        //imageViewVoiture.setImageBitmap(photo)
        imageViewVoiture.setImageURI(photo)


        contacter.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED) {
                val numTel = "+33 7 52 97 69 34"
                val intCall = Intent(Intent.ACTION_CALL)
                intCall.data = Uri.parse("tel:$numTel")
                if (intCall.resolveActivity(packageManager)
                    != null) {
                    startActivity(intCall)
                }
            } else {
           // On demande la permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.
                        permission.CALL_PHONE), 1)
            }





        }



    }
}
