package com.app.onlinemedic.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import com.app.onlinemedic.R
import com.app.onlinemedic.commoners.AppUtils
import com.app.onlinemedic.commoners.BaseActivity
import com.app.onlinemedic.commoners.PrefData
import com.app.onlinemedic.model.User
import com.app.onlinemedic.utils.PreferenceHelper
import com.bumptech.glide.Glide
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.lang.Exception

class EditProfileActivity : BaseActivity() {
    private val FILE_REQUEST_CODE: Int = 1001
    private lateinit var prefs: SharedPreferences
    private var avatar = ""
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(toolbar)
        prefs = PreferenceHelper.defaultPrefs(this)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Profile"
        setAvatarClickListener()

        setData()
        updateButton.setOnClickListener { updateUi() }
    }

    private fun setData() {
        profileMobile.setText(prefs.getString(PrefData.PHONE, ""))
        try {
            user = User.findById(User::class.java, 1)
            if (!user?.fname.isNullOrEmpty()) {
                profileFirstname.setText(user?.fname)
                profileLastname.setText(user?.lname)
                Glide.with(this@EditProfileActivity).load(user?.avatar).into(profileAvatar)
                avatar = user?.avatar ?: ""
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@EditProfileActivity,
                "Something went wrong. Please try again in sometime",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateUi() {
        if (!AppUtils.validated(profileFirstname, profileLastname, profileMobile)) return
        updateButton.startAnimation()
        val user = User(
            1,
            "${profileFirstname.text.toString().trim()}",
            "${profileLastname.text.toString().trim()}",
            avatar,
            "${profileMobile.text.toString().trim()}",
            user?.password
        )
        user.update()
        User.findById(User::class.java, 1)

        Handler().postDelayed({
            Toast.makeText(
                this@EditProfileActivity,
                "Profile updated successfully",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }, 1000)
    }

    private fun setAvatarClickListener() {
        profileAvatar.setOnClickListener {
            val intent = Intent(this, FilePickerActivity::class.java)
            intent.putExtra(
                FilePickerActivity.CONFIGS, Configurations.Builder()
                    .setCheckPermission(true)
                    .setShowImages(true)
                    .setShowVideos(false)
                    .enableImageCapture(true)
                    .setMaxSelection(1)
                    .setSkipZeroSizeFiles(true)
                    .build()
            )
            startActivityForResult(intent, FILE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val mediaFile =
                data.getParcelableArrayListExtra<MediaFile>(FilePickerActivity.MEDIA_FILES)
            Glide.with(this@EditProfileActivity).load(mediaFile!![0].uri).into(profileAvatar)
            avatar = mediaFile[0].uri.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        AppUtils.animateEnterLeft(this)
    }
}