package com.netatmo.ylu.core.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.netatmo.ylu.core.AttributeBean
import com.netatmo.ylu.core.R
import com.netatmo.ylu.core.SkinManager

class SkinImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), ViewsChange {

    private val attributeBean = AttributeBean()

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinImageView, defStyleAttr, 0)
        attributeBean.saveViewResource(typedArray, R.styleable.SkinImageView)
        typedArray.recycle()

    }

    override fun skinChangeAction() {
        var key = R.styleable.SkinImageView[R.styleable.SkinImageView_android_src]
        val backgroundId = attributeBean.getViewResource(key)
        if (backgroundId > 0) {
            SkinManager.instance?.let {
                if (it.isDefaultSkin) {
                    val drawable = ContextCompat.getDrawable(context, backgroundId)
                    setImageDrawable(drawable)
                } else {
                    val skinResourceId = it.getBackgroundOrSrc(backgroundId)
                    if (skinResourceId is Int) {
                        setImageResource(skinResourceId)
                    } else {
                        val drawable = skinResourceId as Drawable
                        setImageDrawable(drawable)
                    }
                }
            }

        }
    }

}