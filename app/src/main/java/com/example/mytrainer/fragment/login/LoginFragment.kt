package com.example.mytrainer.fragment.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytrainer.R
import com.example.mytrainer.auth.Facebook
import com.example.mytrainer.auth.Google
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private val TAG = "SignIn"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Google.newInstance(context, google_sign_in_button)
        Facebook.newInstance(context, facebook_sign_in_button)
    }
}
