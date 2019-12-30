package com.example.mycarisyours.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycarisyours.R
import kotlinx.android.synthetic.main.activity_debut.*

class DebutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debut)

        Confirmer.setOnClickListener {
              val intent2 = Intent(this, AjouterVoitureActivity::class.java)
                startActivity(intent2)
                finish()

        }

    }
}
