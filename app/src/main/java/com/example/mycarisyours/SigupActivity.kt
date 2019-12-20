package com.example.mycarisyours



import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import util.BackendSettings

/**
 * Cette class va permet a l'utilisateur d'ajouter un compt
 *
 * **/

class SigupActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //connection de notre appli a BackendLess
        Backendless.initApp(this,
            BackendSettings.APPLICATION_ID, BackendSettings.ANDROID_SECRET_KEY )


        //le bouton submit recup l'email et le mot de passe
        submit.setOnClickListener {


            val mail=ed_email.text.toString()
            val nom=ed_nom.text.toString()
           val motdepasse= ed_mdp.text.toString()
            val motdepassec= ed_cmdp.text.toString()


            if (motdepassec==motdepasse){

                val user = BackendlessUser()
                user.email = mail
                user.password = motdepasse
                user.setProperty("name", nom)

                val intent1 = Intent(this, MainActivity::class.java)


                Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser> {
                    override fun handleResponse(registeredUser: BackendlessUser?) {
                        toast( "User bien ajouté")
                        startActivity(intent1)
                        finish()
                    }

                    override fun handleFault(fault: BackendlessFault?) {
                        toast( "une erreur interne est survenue")
                        ed_cmdp.setText("")
                        ed_mdp.setText("")
                    }
                })

            }else
            {
                toast("les deux mots de passes ne correspandent pas")
            }






        }




    }







}