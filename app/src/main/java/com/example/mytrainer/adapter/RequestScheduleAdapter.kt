package com.example.mytrainer.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mytrainer.R
import com.example.mytrainer.component.ScheduleRequest
import com.example.mytrainer.component.User
import com.example.mytrainer.database.locale.Query as locale
import com.example.mytrainer.database.remote.Query as remote

class RequestScheduleAdapter : RecyclerView.Adapter<RequestScheduleAdapter.Companion.RequestScheduleViewHolder>(),
    AdapterView.OnItemSelectedListener {
    private var trainers: List<User> = listOf(User("Seleziona", "istruttore"))
    private var selectedTrainer: User = User()

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedTrainer = trainers[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestScheduleViewHolder {
        return RequestScheduleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_request_schedule, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: RequestScheduleViewHolder, position: Int) {
        val spinner = holder.spinner
        trainers = trainers + locale.getInstance().getAllUsersType("trainer")
        val adapter = ArrayAdapter<User>(holder.context, R.layout.support_simple_spinner_dropdown_item, trainers)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        holder.button.setOnClickListener {
            if (selectedTrainer.id.isNotEmpty()) {
                remote.addScheduleRequest(
                    ScheduleRequest(
                        locale.getInstance().getUser(),
                        selectedTrainer,
                        holder.info.text.toString()
                    )
                ) { request ->
                    locale.getInstance().addScheduleRequest(request)
                    Toast.makeText(holder.context, "Richiesta effettuata con successo!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(holder.context, "Specifica un istruttore!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {

        class RequestScheduleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val spinner = itemView.findViewById(R.id.spinner) as Spinner
            val context = itemView.context!!
            val info = itemView.findViewById(R.id.requestInfo) as EditText
            val button = itemView.findViewById(R.id.sendRequestScheduleButton) as Button
        }
    }
}