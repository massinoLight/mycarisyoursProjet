package com.example.mycarisyours.activites

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.backendless.Backendless
import com.backendless.exceptions.BackendlessFault
import com.backendless.files.BackendlessFile
import kotlinx.android.synthetic.main.activity_ajouter_photo.*
import org.jetbrains.anko.toast
import java.time.LocalDate
import java.util.*
import com.example.mycarisyours.R
import util.BackendSettings
import java.io.File
import java.time.LocalDateTime
import com.backendless.async.callback.AsyncCallback as AsyncCallback


class AjouterPhotoActivity : AppCompatActivity() {

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private var NB_PHOTO=5
    var image_uri: Uri? = null
    var image_url = ArrayList<String?>()



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_photo)
        image_url.addAll(listOf("", "", "",""))

        //backendless informations key
        Backendless.initApp(this,
            BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY )

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
        val prix = intent.getStringExtra(ConfirmerActivity.EXTRA_PRIX)




        //le bouton pour ouvrir la camera
        capture_btn.setOnClickListener {
            /***
             * on verifie a chaque fois que les permissions sont accordées
             * dans le cas ou l'utilisateur la retire par erreur apres l'instalation
             * pour ne pas faire cracher l'applicaton
             * **/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    //permission was not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{

                    openCamera()
                    NB_PHOTO--
                }
            }
            else{
              //dans le cas des anciens telephones <marshmallo
                openCamera()
                NB_PHOTO--
            }
        }


        suivant.setOnClickListener {

            val intent2 = Intent(this, ConfirmerActivity::class.java)

            intent2.putExtra(ConfirmerActivity.EXTRA_MARQUE,marque )
            intent2.putExtra(ConfirmerActivity.EXTRA_MODÉLE,modele )
            intent2.putExtra(ConfirmerActivity.EXTRA_ENERGIE,energie )
            intent2.putExtra(ConfirmerActivity.EXTRA_VITESSE,vitesse )
            intent2.putExtra(ConfirmerActivity.EXTRA_DESCRIPTION,description )
            intent2.putExtra(ConfirmerActivity.EXTRA_PLACES, nbplaces)
            intent2.putExtra(ConfirmerActivity.EXTRA_PORTES,nbportes )
            intent2.putExtra(ConfirmerActivity.EXTRA_FUMEUR, fumeur)
            intent2.putExtra(ConfirmerActivity.EXTRA_ANIMEAUX, animeaux)
            intent2.putExtra(ConfirmerActivity.EXTRA_MATRICULE,matricule )
            intent2.putExtra(ConfirmerActivity.EXTRA_DATEDEBUT,datedebut )
            intent2.putExtra(ConfirmerActivity.EXTRA_DATEFIN,datefin )
            intent2.putExtra(ConfirmerActivity.EXTRA_PRIX, prix)
           // intent2.putExtra(ConfirmerActivity.EXTRA_PHOTOS,lesphotos)
            intent2.putStringArrayListExtra(ConfirmerActivity.EXTRA_PHOTOS, image_url as ArrayList<String?>)



            startActivity(intent2)
        }


        precedent.setOnClickListener {

            val intent2 = Intent(this, AjoutoitureLastActivity::class.java)

            startActivity(intent2)
            finish()


        }



    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)





        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, lesphotos.get(NB_PHOTO))

        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){

                    openCamera()
                }
                else{

                    Toast.makeText(this, "Permission non accordée", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK){

            if (NB_PHOTO!=0){

                // now upload the file
                val current = LocalDateTime.now().toString()
                var bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri)
                Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.PNG,
                    100, current+".png", "files/photos", object : AsyncCallback<BackendlessFile> {
                    override fun handleResponse(uploadedFile: BackendlessFile) {
                        //toast("image bien inséré au clound. File URL is - ${uploadedFile.fileURL}")

                        image_url.add(uploadedFile.fileURL.toString())

                    }

                    override fun handleFault(fault: BackendlessFault) {
                        toast(fault.message)

                    }
                })

                when(NB_PHOTO) {

                    1 ->image_view.setImageURI(image_uri)
                    2 ->image_view2.setImageURI(image_uri)
                    3 ->image_view3.setImageURI(image_uri)
                    4 ->image_view4.setImageURI(image_uri)

                    else ->toast("4 photos suffisent largement ")
                }


            }
            else
            {
                toast("4 photos suffisent largement ")
            }




        }
    }
}
