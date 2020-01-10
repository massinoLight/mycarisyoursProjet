package com.example.mycarisyours.activites

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
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
    var image_url=""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_photo)


        //backendless informations key
        Backendless.initApp(this,
            BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY )

        //page 1
        val marque = intent.getStringExtra(ConfirmerActivity.EXTRA_MARQUE)
        val modele = intent.getStringExtra(ConfirmerActivity.EXTRA_MODÉLE)


        //page 2
        val description = intent.getStringExtra(ConfirmerActivity.EXTRA_DESCRIPTION)
        val nbplaces = intent.getStringExtra(ConfirmerActivity.EXTRA_PLACES)


        //page 3
        val matricule = intent.getStringExtra(ConfirmerActivity.EXTRA_MATRICULE)

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

            intent2.putExtra(ConfirmerActivity.EXTRA_DESCRIPTION,description )


            intent2.putExtra(ConfirmerActivity.EXTRA_MATRICULE,matricule )

            intent2.putExtra(ConfirmerActivity.EXTRA_PRIX, prix)

            if (image_url==""){
                image_url="https://img.autoplus.fr/picture/fiat/panda/1508621/Fiat_Panda_2017_c4305-1600-1108.jpg?r"

            }

           intent2.putExtra(ConfirmerActivity.EXTRA_DERNIERE_PHOTOS,image_url)




            startActivity(intent2)
            finish()
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

                    toast( "Permission non accordée")
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

                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.progress_dialog,null)
                val message = dialogView.findViewById<TextView>(R.id.message)
                message.text = "Téléchargement..."
                builder.setView(dialogView)
                builder.setCancelable(false)
                val dialog = builder.create()
                dialog.show()
                Handler().postDelayed({dialog.dismiss()},30000)
                Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.PNG,
                    100, current+".png", "files/photos", object : AsyncCallback<BackendlessFile> {

                        override fun handleResponse(uploadedFile: BackendlessFile) {
                            toast("image bien inséré au clound. File URL is - ${uploadedFile.fileURL}")

                            image_url=uploadedFile.fileURL.toString()


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
