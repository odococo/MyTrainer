package com.example.mytrainer.fragment.trainer


import android.content.Context
import android.content.res.Configuration.KEYBOARD_12KEY
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.*
import android.widget.AdapterView
import android.widget.EditText
import com.example.mytrainer.Codes
import com.example.mytrainer.R
import com.example.mytrainer.adapter.trainer.RequestAdapter
import com.example.mytrainer.component.ScheduleRequest
import com.example.mytrainer.component.User
import com.example.mytrainer.database.locale.Query
import kotlinx.android.synthetic.main.fragment_pending_request.*


class PendingRequestsFragment : Fragment() {
    private var listener: RequestsListener? = null
    private var requests: List<ScheduleRequest> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_request, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requests = Query.getInstance().getRequests()
        list.adapter = RequestAdapter(requests, context)

        registerForContextMenu(list)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v?.id == list.id) {
            menu?.add(0, Codes.View.PROFILE, 1, getString(R.string.view_profile))
            menu?.add(0, Codes.Create.SCHEDULE, 2, getString(R.string.create_schedule))
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val request =
            list.getItemAtPosition((item?.menuInfo as AdapterView.AdapterContextMenuInfo).position) as ScheduleRequest
        when (item.itemId) {
            Codes.View.PROFILE -> listener?.view(request.athlete)
            Codes.Create.SCHEDULE -> {
                val alert = AlertDialog.Builder(context)
                alert.setTitle(getString(R.string.create_with_day))
                val input = EditText(context)
                input.inputType = InputType.TYPE_CLASS_NUMBER
                input.setRawInputType(KEYBOARD_12KEY)
                alert.setView(input)
                alert.setPositiveButton("Ok") { _, _ ->
                    //Put actions for OK button here
                    val days = input.text.toString().toInt()
                    if (days > 1) {
                        listener?.create(request, days)
                    } else {
                        val error = AlertDialog.Builder(context)
                        error.setTitle(getString(R.string.error_input))
                        error.setMessage(getString(R.string.schedule_days))
                        error.setPositiveButton("Ok") { _, _ ->
                        }
                        error.show()
                    }
                }
                alert.setNegativeButton(getString(R.string.cancel)) { _, _ ->
                }
                alert.show()
            }
        }

        return true
    }

    interface RequestsListener {
        fun view(user: User)

        fun create(request: ScheduleRequest, days: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RequestsListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ProfileListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
