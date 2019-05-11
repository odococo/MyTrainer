package com.example.mytrainer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.User

class UsersAdapter(
    dataSet: List<User>,
    context: Context
) :
    ArrayAdapter<User>(context, R.layout.user_template, dataSet) {

    private var lastPosition = -1

    // View lookup cache
    private class ViewHolder {
        internal var id: TextView? = null
        internal var firstname: TextView? = null
        internal var lastname: TextView? = null
        internal var type: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var newview = convertView
        // Get the data item for this position
        val dataModel = getItem(position)!!
        // Check if an existing view is being reused, otherwise inflate the view
        val viewHolder: ViewHolder // view lookup cache stored in tag

        val result: View

        if (newview == null) {

            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            newview = inflater.inflate(R.layout.user_template, parent, false)
            viewHolder.id = newview.findViewById(R.id.user_id)
            viewHolder.firstname = newview.findViewById(R.id.user_first_name)
            viewHolder.lastname = newview.findViewById(R.id.user_last_name)
            viewHolder.type = newview.findViewById(R.id.user_type)

            result = newview

            newview!!.tag = viewHolder
        } else {
            viewHolder = newview.tag as ViewHolder
            result = newview
        }

        val animation = AnimationUtils.loadAnimation(
            context,
            if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top
        )
        result.startAnimation(animation)
        lastPosition = position

        viewHolder.id!!.text = dataModel.id
        viewHolder.firstname!!.text = dataModel.firstName
        viewHolder.lastname!!.text = dataModel.lastName
        viewHolder.type!!.text = dataModel.type

        // Return the completed view to render on screen
        return newview
    }
}