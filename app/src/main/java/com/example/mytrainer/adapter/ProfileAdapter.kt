package com.example.mytrainer.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R

class ProfileAdapter: RecyclerView.Adapter<ProfileAdapter.Companion.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return Companion.ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ProfileViewHolder?, position: Int) {
       holder!!.surname.text = "Roshka"
       holder!!.name.text = "Anatoliy"
    }

    companion object {

        class ProfileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            //Elenco dei campi relativi ad ogni esercizio
            val cardView: CardView = itemView.findViewById(R.id.profileCardView)
            val surname: TextView = itemView.findViewById(R.id.profileSurname)
            val name: TextView = itemView.findViewById(R.id.profileName)
        }
    }
}