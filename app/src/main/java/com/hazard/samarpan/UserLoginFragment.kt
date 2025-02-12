package com.hazard.samarpan

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UserLoginFragment: Fragment() {
    private var itemView: View?=null

    private var userSignUp: TextView?=null
    private var email: TextInputEditText?=null
    private var password: TextInputEditText?=null
    private var btnLogin: Button?=null

    private lateinit var registerAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        itemView =layoutInflater.inflate(R.layout.user_login_fragment,container,false)

        email=itemView?.findViewById(R.id.donor_email)
        password=itemView?.findViewById(R.id.donor_password)
        userSignUp=itemView!!.findViewById(R.id.userSignUp)
        btnLogin=itemView!!.findViewById(R.id.userLogin)

        registerAuth= Firebase.auth

        userSignUp?.setOnClickListener {
              val userRegistrationPage=UserRegisterFragment()
              activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.loginFragment,userRegistrationPage)
                  ?.addToBackStack(null)?.commit()
        }

        btnLogin?.setOnClickListener {
            val mail=email?.text.toString().trim()
            val pass=password?.text.toString().trim()
            if(TextUtils.isEmpty(mail)){
                email?.error = "Email cannot be empty"
            }
            if(TextUtils.isEmpty(pass)){
                password?.error = "Password cannot be empty"
            }
            else{
                registerAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener{ task ->

                    if(task.isSuccessful){
                        // logic for navigating to the dashboard
                        Toast.makeText(activity,"Logged in Successfully", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(activity,task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        return itemView
    }
}