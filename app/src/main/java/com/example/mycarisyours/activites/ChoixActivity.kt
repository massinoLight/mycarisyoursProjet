package com.example.mycarisyours.activites



import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycarisyours.R

import kotlinx.android.synthetic.main.activity_choix.*

class ChoixActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix)


        trouver.setOnClickListener {

            val intent = Intent(this, MapActivity::class.java)

            startActivity(intent)



        }

        proposer.setOnClickListener {

            val intent = Intent(this, AccueilActivity::class.java)

            startActivity(intent)



        }


    }
}