package com.netatmo.ylu.core.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.netatmo.ylu.core.AttributeBean
import com.netatmo.ylu.core.R
import com.netatmo.ylu.core.SkinManager

class SkinConstraintLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ViewsChange {
    private val attributeBean = AttributeBean()

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SkinConstraintLayout, defStyleAttr, 0)
        attributeBean.saveViewResource(typedArray, R.styleable.SkinConstraintLayout)
        typedArray.recycle()

    }

    override fun skinChangeAction() {
        val key = R.styleable.SkinConstraintLayout[R.styleable.SkinConstraintLayout_android_background]
        val backgroundId = attributeBean.getViewResource(key)
        if (backgroundId > 0) {
            SkinManager.instance?.let {
                if (it.isDefaultSkin) {
                    val drawable = ContextCompat.getDrawable(context, backgroundId)
                    background = drawable
                } else {
                    val skinResourceId = it.getBackgroundOrSrc(backgroundId)
                    if (skinResourceId is Int) {
                        setBackgroundColor(skinResourceId)
                    } else {
                        val drawable = skinResourceId as Drawable
                        background = drawable
                    }
                }
            }

        }
    }
}