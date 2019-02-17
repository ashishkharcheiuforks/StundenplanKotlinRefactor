package com.maxkrass.stundenplankotlinrefactor.extensions

import android.app.Activity
import android.app.Service
import android.content.Intent
import androidx.fragment.app.Fragment
import org.jetbrains.anko.*
import org.jetbrains.anko.internals.AnkoInternals

fun Fragment.browse(url: String, newTask: Boolean = false): Boolean? = activity?.browse(url, newTask)

fun Fragment.share(text: String, subject: String = ""): Boolean? = activity?.share(text, subject)

fun Fragment.email(email: String, subject: String = "", text: String = ""): Boolean? =
        activity?.email(email, subject, text)

fun Fragment.makeCall(number: String): Boolean? = activity?.makeCall(number)

fun Fragment.sendSMS(number: String, text: String = ""): Boolean? = activity?.sendSMS(number, text)

inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) {
    AnkoInternals.internalStartActivity(requireContext(), T::class.java, params)
}

inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int, vararg params: Pair<String, Any?>) {
    startActivityForResult(AnkoInternals.createIntent(requireContext(), T::class.java, params), requestCode)
}

inline fun <reified T : Service> Fragment.startService(vararg params: Pair<String, Any?>) {
    AnkoInternals.internalStartService(requireContext(), T::class.java, params)
}

inline fun <reified T : Service> Fragment.stopService(vararg params: Pair<String, Any?>) {
    AnkoInternals.internalStopService(requireContext(), T::class.java, params)
}

inline fun <reified T : Any> Fragment.intentFor(vararg params: Pair<String, Any?>): Intent =
        AnkoInternals.createIntent(requireContext(), T::class.java, params)