package com.alvayonara.openweatherapps.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.callbacks.onShow
import com.alvayonara.openweatherapps.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import timber.log.Timber

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    private lateinit var permissionListener: MultiplePermissionsListener

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setup()
    }

    abstract fun setup()

    protected fun showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    protected fun setLog(message: String) = Timber.e(message)

    protected fun requestPermissions(
        permissions: List<String>,
        action: () -> Unit, actionDeny: () -> Unit
    ) {
        Dexter.withContext(this)
            .withPermissions(permissions)
            .withListener(getPermissionsListener(action, actionDeny))
            .check()
    }

    private fun getPermissionsListener(
        action: () -> Unit, actionDeny: () -> Unit
    ): MultiplePermissionsListener {
        if (!this::permissionListener.isInitialized) {
            permissionListener = object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let { rpt ->
                        if (rpt.areAllPermissionsGranted()) action()
                        else actionDeny()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }
        }
        return permissionListener
    }

    protected fun showDenyPermission() {
        MaterialDialog(this).show {
            title(R.string.request_permission)
            message(R.string.request_permission_1)
            positiveButton(R.string.request_permission_2) { dialog ->
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .also { intent ->
                        intent.data = Uri.parse("package:${packageName}")
                        startActivity(intent)
                    }
                dialog.dismiss()
            }
            negativeButton(R.string.request_permission_3) { dialog ->
                dialog.dismiss()
            }
            cancelable(false)
            onShow {
                it.getActionButton(WhichButton.POSITIVE).updateTextColor(Color.BLACK)
                it.getActionButton(WhichButton.NEGATIVE).updateTextColor(Color.BLACK)
            }
        }
    }

    protected fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}