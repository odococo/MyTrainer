package com.example.mytrainer.adapter.trainer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mytrainer.R
import com.example.mytrainer.component.TrainingExercise

class ExercisesAdapter(
    dataSet: List<TrainingExercise>,
    context: Context
) :
    ArrayAdapter<TrainingExercise>(context, R.layout.template_exercise, dataSet) {

    private var lastPosition = -1

    // View lookup cache
    private class ViewHolder {
        internal var title: TextView? = null
        internal var overallWork: TextView? = null
        internal var recoveryTime: TextView? = null
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
            newview = inflater.inflate(R.layout.template_exercise, parent, false)
            viewHolder.title = newview.findViewById(R.id.exercise_title)
            viewHolder.overallWork = newview.findViewById(R.id.overall_work)
            viewHolder.recoveryTime = newview.findViewById(R.id.recovery_time)

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

        viewHolder.title!!.text = dataModel.id
        viewHolder.overallWork!!.text = dataModel.overallWork()
        viewHolder.recoveryTime!!.text = dataModel.time()

        // Return the completed view to render on screen
        return newview
    }
}