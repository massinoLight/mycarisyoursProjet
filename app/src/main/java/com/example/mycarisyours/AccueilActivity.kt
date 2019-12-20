package com.example.mycarisyours

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.backendless.Backendless
import com.example.mycarisyours.ui.main.SectionsPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivityForResult

class AccueilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        

        add.setOnClickListener { view ->
            startActivityForResult<AjouterVoitureActivity >(1)
        }


    }

   
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                // Résultat de startActivityForResult<ModifierActivity>
                if (resultCode == Activity.RESULT_OK) {
                    val valider = data?.getBooleanExtra(AjouterVoitureActivity.EXTRA_VALIDER, false) ?: false
                    if (valider) {






                       /* var p8 = Personne(
                            nouvValeurnom
                            , nouvValeuremail, nouvValeurtel,
                            nouvValeurfixe, chemin
                        )


                        //un objet PersonneBackendLess que l on va stocker dans notre cloud
                        val per = PersonneBackendLess(
                            null, chemin.toString(), nouvValeurnom, nouvValeuremail,
                            nouvValeurtel, nouvValeurfixe
                        )




                        doAsync {
                            Backendless.Persistence
                                .of(PersonneBackendLess::class.java).save(per)

                        }


                        //toast("Données bien ajouté au cloud")
                        personnes.add(0, p8)
                        //cette ligne permet de trier la liste des contactes par ordre alphabetique
                        personnes.sortWith(compareBy({ it.nom }))
                        buildRecyclerView()
                        mon_recycler.adapter?.notifyItemInserted(0)*/


                    } else {
                        //ID--
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // L'utilisateur a utilisé le bouton retour <- de son téléphone
                    // on ne fait rien de spécial non plus
                }
            }
        }
    }




}