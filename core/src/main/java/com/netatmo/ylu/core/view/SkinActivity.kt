package com.netatmo.ylu.core.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.children
import com.netatmo.ylu.core.BarUtils
import com.netatmo.ylu.core.CustomAppCompatViewInflater
import com.netatmo.ylu.core.SkinManager

open class SkinActivity : AppCompatActivity(){

    private var myInflater : CustomAppCompatViewInflater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {

        if(openSkin()){
            if(null == myInflater){
                myInflater = CustomAppCompatViewInflater(this)
               }
            myInflater?.setName(name)
            myInflater?.setAttrs(attrs)
            return myInflater?.createView()
        }
        return super.onCreateView(parent, name, context, attrs)
    }


    //must override this if the user wants to enable skin change
    open fun openSkin() : Boolean{
        return false
    }

    fun setSkin(skinPath: String?, themeColorId: Int) {
        SkinManager.instance?.loadSkinResources(skinPath)
        if (themeColorId != 0) {
            BarUtils.forStatusBar(this, themeColorId)
            BarUtils.forActionBar(this, themeColorId)
            BarUtils.forNavigation(this, themeColorId)
        }
        changeSkin(window.decorView)
    }

    fun setDayNightMode(mode: Int) {
        delegate.localNightMode = mode
        if(Build.VERSION.SDK_INT > 21){
            BarUtils.forStatusBar(this)
            BarUtils.forActionBar(this)
            BarUtils.forNavigation(this)
        }
        changeSkin(window.decorView)
    }

    private fun changeSkin(decorView : View){
        if(decorView is ViewsChange){
            val viewChange = decorView as ViewsChange
            viewChange.skinChangeAction()
        }
        if(decorView is ViewGroup){
            for( view in decorView.children){
                changeSkin(view)
            }
        }
        //add recyclerview support
    }
}