package com.example.mytrainer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.ScheduleRequest

class RequestAdapter(
    dataSet: List<ScheduleRequest>,
    context: Context
) :
    ArrayAdapter<ScheduleRequest>(context, R.layout.template_request, dataSet) {

    private var lastPosition = -1

    // View lookup cache
    private class ViewHolder {
        internal var athlete: TextView? = null
        internal var info: TextView? = null
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
            newview = inflater.inflate(R.layout.template_request, parent, false)
            viewHolder.athlete = newview.findViewById(R.id.request_from)
            viewHolder.info = newview.findViewById(R.id.request_info)

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

        viewHolder.athlete!!.text = dataModel.athlete.name()
        viewHolder.info!!.text = dataModel.info

        // Return the completed view to render on screen
        return newview
    }
}