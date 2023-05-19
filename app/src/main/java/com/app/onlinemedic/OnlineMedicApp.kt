package com.app.onlinemedic

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.orm.SugarApp
import com.orm.SugarContext
import com.squareup.picasso.Picasso
import timber.log.Timber

class OnlineMedicApp : SugarApp() {

    override fun onCreate() {
        super.onCreate()

        SugarContext.init(applicationContext)

        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
                Picasso.get().load(uri).placeholder(placeholder).into(imageView)
            }

            override fun cancel(imageView: ImageView) {
                Picasso.get().cancelRequest(imageView)
            }
        })

        Timber.plant(Timber.DebugTree())
    }

    override fun onTerminate() {
        SugarContext.terminate()
        super.onTerminate()

    }
}