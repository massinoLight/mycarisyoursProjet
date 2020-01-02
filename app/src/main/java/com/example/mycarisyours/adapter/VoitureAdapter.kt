package com.example.mycarisyours.adapter



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycarisyours.R
import kotlinx.android.synthetic.main.style_dune_ligne.view.*
import java.time.LocalDate
import java.util.ArrayList


data class Voiture(val marque:String, val modele:String, val energie:String, val vitesse:String
                   , val places:String, val portes:String, val fumeur:String, val animaux:String
                   , val description:String, val matricule:String, val dateDeb: LocalDate, val datefin: LocalDate,
                   val prix:String,val photo:ArrayList<String>)

class VoitureAdapter(val voitureAAfficher: Array<Voiture>, val listener: (Voiture)-> Unit):
    RecyclerView.Adapter<VoitureAdapter.ViewHolder>() {


    //creer un elements ViewHolder du recuclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoitureAdapter.ViewHolder {
        val uneLigneView=LayoutInflater.from(parent.context).inflate(R.layout.style_dune_ligne,parent,false)
        return ViewHolder(uneLigneView)
    }


    //charger le contenu d un objet personne dans un element du recyclerview
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.i("XXXX","onBindViewHolder")

        //on fournit l'objet personne a afficher et l'action a effectuer lors du clic sur l'element
        holder.bind(voitureAAfficher[position],listener)

    }


    //dans le cas ou l'on souhaite avoir le nombre d'objet personne fourni
    override fun getItemCount()=voitureAAfficher.size

    class ViewHolder(val elmtDeListe:View):RecyclerView.ViewHolder(elmtDeListe)
    {

        //cette fonction permet de charger les donnees dans l element du recyview
        fun bind(voiture:Voiture,listener: (Voiture) -> Unit)= with(itemView)
        {
            android.util.Log.i("XXXX","FCT Bind ")
            //remplissage de la partie nom
            itemView.tv_marque.text=voiture.marque
            itemView.tv_modele.text= voiture.modele

            //l'action a realiser lors du clic  sur un element
            setOnClickListener{(listener(voiture))}
        }



    }




}