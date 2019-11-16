package com.netatmo.ylu.core.view

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.netatmo.ylu.core.AttributeBean
import com.netatmo.ylu.core.R
import com.netatmo.ylu.core.SkinManager

class SkinTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), ViewsChange {

    private val attributeBean = AttributeBean()

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinTextView, defStyleAttr, 0)
        attributeBean.saveViewResource(typedArray, R.styleable.SkinTextView)
        typedArray.recycle()

    }

    override fun skinChangeAction() {
        var key = R.styleable.SkinTextView[R.styleable.SkinTextView_android_background]
        val backgroundId = attributeBean.getViewResource(key)
        if (backgroundId > 0) {
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


        key = R.styleable.SkinTextView[R.styleable.SkinTextView_android_textColor]
        val textColorResourceId = attributeBean.getViewResource(key)
        if (textColorResourceId > 0) {
            SkinManager.instance?.let {
                if (it.isDefaultSkin) {
                    val color = ContextCompat.getColorStateList(context, textColorResourceId)
                    setTextColor(color)
                } else {
                    val color = it.getColorStateList(textColorResourceId)
                    setTextColor(color)
                }
            }

        }

        key = R.styleable.SkinTextView[R.styleable.SkinTextView_custom_typeface]
        val textTypefaceResourceId = attributeBean.getViewResource(key)
        if (textTypefaceResourceId > 0) {
            SkinManager.instance?.let {
                typeface = if (it.isDefaultSkin) {
                    Typeface.DEFAULT
                } else {
                    it.getTypeface(textTypefaceResourceId)
                }
            }
        }
    }

}