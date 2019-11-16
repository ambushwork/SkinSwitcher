package com.netatmo.ylu.core

import android.app.Application
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils


class SkinManager(private val application: Application) {
    var isDefaultSkin = true
    private val appResources: Resources = application.resources
    private lateinit var skinResources: Resources
    private var skinPackageName: String? = null

    private val ADD_ASSET_PATH = "addAssetPath" // method name

    companion object {
        var instance: SkinManager? = null
        fun init(application: Application) {
            if (instance == null) {
                synchronized(SkinManager::class.java) {
                    if (instance == null) {
                        instance = SkinManager(application)
                    }
                }
            }
        }
    }


    fun getInstance(): SkinManager {
        return if (instance == null) throw IllegalStateException("SkinManager not initialized!")
        else instance!!
    }


    fun loadSkinResources(path: String?) {
        if (TextUtils.isEmpty(path)) {
            isDefaultSkin = true
            return
        }

        val assetManager = AssetManager::class.java.newInstance()
        val addAssetPath = assetManager.javaClass.getDeclaredMethod(ADD_ASSET_PATH, String::class.java)
        addAssetPath.isAccessible = true
        addAssetPath.invoke(assetManager, path)

        skinResources = Resources(assetManager, appResources.displayMetrics, appResources.configuration)

        //check inside the skin package AndroidManifest.xml
        skinPackageName = application.packageManager
                .getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES).packageName
        isDefaultSkin = TextUtils.isEmpty(skinPackageName)
    }

    private fun getSkinResourceId(resourceId: Int): Int {
        if (isDefaultSkin) return resourceId
        val resName = appResources.getResourceEntryName(resourceId)
        val resType = appResources.getResourceTypeName(resourceId)

        val skinResId = skinResources.getIdentifier(resName, resType, skinPackageName)

        isDefaultSkin = skinResId == 0
        return if (skinResId == 0) resourceId else skinResId
    }

    public fun getBackgroundOrSrc(resourceId: Int): Any? {
        val resourceTypeName = appResources.getResourceTypeName(resourceId)
        when (resourceTypeName) {
            "color" -> return getColor(resourceId)
            "mipmap", "drawable" -> return getDrawableOrMipMap(resourceId)
        }
        return null
    }

    //font
    fun getTypeface(resourceId: Int): Typeface {

        val skinTypefacePath = getString(resourceId)

        if (TextUtils.isEmpty(skinTypefacePath)) return Typeface.DEFAULT
        return if (isDefaultSkin)
            Typeface.createFromAsset(appResources.assets, skinTypefacePath)
        else
            Typeface.createFromAsset(skinResources.assets, skinTypefacePath)
    }

    private fun getColor(resourceId: Int): Int {
        val ids = getSkinResourceId(resourceId)
        return if (isDefaultSkin) appResources.getColor(ids) else skinResources.getColor(ids)
    }

    fun getColorStateList(resourceId: Int): ColorStateList {
        val ids = getSkinResourceId(resourceId)
        return if (isDefaultSkin) appResources.getColorStateList(ids) else skinResources.getColorStateList(ids)
    }

    private fun getDrawableOrMipMap(resourceId: Int): Drawable {
        val ids = getSkinResourceId(resourceId)
        return if (isDefaultSkin) appResources.getDrawable(ids) else skinResources.getDrawable(ids)
    }

    private fun getString(resourceId: Int): String {
        val ids = getSkinResourceId(resourceId)
        return if (isDefaultSkin) appResources.getString(ids) else skinResources.getString(ids)
    }


}