package com.netatmo.ylu.core.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.netatmo.ylu.core.AttributeBean
import com.netatmo.ylu.core.R
import com.netatmo.ylu.core.SkinManager

class SkinButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr), ViewsChange {

    private val attributeBean = AttributeBean()

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinButton, defStyleAttr, 0)
        attributeBean.saveViewResource(typedArray, R.styleable.SkinButton)
        typedArray.recycle()

    }

    override fun skinChangeAction() {
        var key = R.styleable.SkinButton[R.styleable.SkinButton_android_background]
        val backgroundId = attributeBean.getViewResource(key)
        if(backgroundId > 0){
            SkinManager.instance?.let {
                if (it.isDefaultSkin) {
                    val drawable = ContextCompat.getDrawable(context, backgroundId)
                    setBackgroundDrawable(drawable)
                } else {
                    val skinResourceId = it.getBackgroundOrSrc(backgroundId)
                    if (skinResourceId is Int) {
                        setBackgroundColor(skinResourceId)
                    } else {
                        val drawable = skinResourceId as Drawable
                        setBackgroundDrawable(drawable)
                    }
                }
            }

        }


        key = R.styleable.SkinButton[R.styleable.SkinButton_android_textColor]
        val textColorId = attributeBean.getViewResource(key)
        if(textColorId > 0){
            val color = ContextCompat.getColorStateList(context, textColorId)
            setTextColor(color)
        }
    }

}