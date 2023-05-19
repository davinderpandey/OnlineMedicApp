package com.app.onlinemedic.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.onlinemedic.R
import com.app.onlinemedic.activities.MainActivity
import com.app.onlinemedic.commoners.AppUtils
import com.app.onlinemedic.commoners.AppUtils.drawableToBitmap
import com.app.onlinemedic.commoners.AppUtils.setDrawable
import com.app.onlinemedic.commoners.BaseFragment
import com.app.onlinemedic.commoners.PrefData
import com.app.onlinemedic.utils.PreferenceHelper
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.mikepenz.iconics.typeface.library.ionicons.Ionicons
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit

class LoginFragment : BaseFragment() {
    private var resendToken: ForceResendingToken? = null
    private var vId: String? = null
    private lateinit var signupSuccessful: Bitmap
    private var isLoggingIn = false
    private lateinit var prefs: SharedPreferences
    private var mAuth: FirebaseAuth? = null
    private var phoneNumber = ""
    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val successfulIcon = setDrawable(
            requireActivity(),
            Ionicons.Icon.ion_checkmark_round,
            R.color.white,
            25
        )
        signupSuccessful = drawableToBitmap(successfulIcon)
        prefs = PreferenceHelper.defaultPrefs(requireActivity())

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        tvLoginButton?.setOnClickListener {
            Toast.makeText(requireActivity(), "Please wait...", Toast.LENGTH_SHORT).show()
            signIn()
        }
        verifyButton?.setOnClickListener {
            verifyButton?.startAnimation()
            verifyCode(loginOTP.text.toString())
        }
    }

    private fun displayOTPView() {
        layoutLogin.visibility = View.GONE
        layoutOTP.visibility = View.VISIBLE
        tvWrongNo.setOnClickListener {
            layoutLogin.visibility = View.VISIBLE
            loginOTP.setText("")
            layoutOTP.visibility = View.GONE
        }
        tvResendOtp.setOnClickListener {
            //resend otp
            resendVerificationCode(tvLoginMobile.text.toString())
        }
    }

    private fun resendVerificationCode(
        phoneNumber: String?
    ) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber!!,  // Phone number to verify
            60,  // Timeout duration
            TimeUnit.SECONDS,  // Unit of timeout
            requireActivity(),  //a reference to an activity if this method is in a custom service
            callbacks,
            resendToken
        ) // resending with token got at previous call's `callbacks` method `onCodeSent`
        // [END start_phone_auth]
    }

    private fun verifyCode(code: String) {
        // below line is used for getting credentials from our verification id and code.
        if (vId != null) {
            val credential = PhoneAuthProvider.getCredential(vId!!, code)

            // after getting credential we are calling sign in method.
            signInWithCredential(credential)
        }
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    print("Complete")
                    updateUI()
                } else {
                    // if the code is not correct then we are displaying an error message to the user.
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            print("onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
            mAuth?.signInWithCredential(credential)
                ?.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        print("signInWithCredential:success")
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            println("invalid:failure"+ task.exception)
                        }
                    }
                }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            // Show a message and update the UI
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(
            verificationId: String,
            token: ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            displayOTPView()
            // Save verification ID and resending token so we can use them later
            vId = verificationId
            resendToken = token
        }
    }

    private fun signIn() {
        if (mAuth != null) {
            if (!AppUtils.validated(tvLoginMobile)) return
            if (isFirst) {
                isFirst = false
                phoneNumber = tvLoginMobile.text.toString().trim()
                sendOtp()
            } else {
                if (phoneNumber != tvLoginMobile.text.toString().trim()) {
                phoneNumber = tvLoginMobile.text.toString().trim()
                sendOtp()
            }
            else{ displayOTPView() }}
        }
    }

    private fun sendOtp() {
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun updateUI() {
        isLoggingIn = true
        prefs.edit().putString(PrefData.PHONE, tvLoginMobile.text.toString()).apply()

        Handler().postDelayed({
            Toast.makeText(requireActivity(), "Welcome", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().overridePendingTransition(R.anim.enter_b, R.anim.exit_a)
            requireActivity().finish()
        }, 400)
    }


    // Check if user has initiated logging in process. If in process, disable back button
    fun backPressOkay(): Boolean = !isLoggingIn

    override fun onDestroy() {
        if (tvLoginButton != null) tvLoginButton.dispose()
        super.onDestroy()
    }
}

private operator fun SharedPreferences.set(name: String, value: String?) {

}
