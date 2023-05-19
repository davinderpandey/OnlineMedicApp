package com.app.onlinemedic.commoners

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.onlinemedic.utils.Connectivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
    }

    fun isConnected(): Boolean {
        return Connectivity.isConnected(this)
    }

    // User hasn't requested storage permission; request them to allow
    fun requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {

                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {

                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    // Show progress dialog
    fun showLoading(message: String) {
        progressDialog.setMessage(message)
        progressDialog.setCancelable(true)
        progressDialog.show()
    }

    fun hideLoading() {
        progressDialog.dismiss()
    }

    // Check if user has granted storage permission
    fun storagePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    interface PickImageListener {
        fun onImageFetched(uri: Uri)
    }




}
