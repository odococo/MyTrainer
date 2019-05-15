package com.example.mytrainer.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.database.locale.Query

class ProfileAdapter: RecyclerView.Adapter<ProfileAdapter.Companion.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val userData = Query.getInstance().getUser()
        holder.surname.text = userData.firstName
        holder.name.text = userData.lastName
        holder.type.text = userData.type
    }

    companion object {

        class ProfileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            //Elenco dei campi relativi ad ogni esercizio
            val surname: TextView = itemView.findViewById(R.id.profileSurname)
            val name: TextView = itemView.findViewById(R.id.profileName)
            val type: TextView = itemView.findViewById(R.id.userType)
        }
    }
}