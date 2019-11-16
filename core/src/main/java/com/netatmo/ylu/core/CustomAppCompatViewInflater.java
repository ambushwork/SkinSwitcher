package com.netatmo.ylu.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatViewInflater;

import com.netatmo.ylu.core.view.SkinButton;
import com.netatmo.ylu.core.view.SkinConstraintLayout;
import com.netatmo.ylu.core.view.SkinImageView;
import com.netatmo.ylu.core.view.SkinLinearLayout;
import com.netatmo.ylu.core.view.SkinTextView;

/**
 * {@link AppCompatViewInflater}
 */
public class CustomAppCompatViewInflater {

    @NonNull
    private String name; //Name of the widget (Button, TextView...)

    @NonNull
    private Context context;

    private AttributeSet attrs;

    public CustomAppCompatViewInflater(@NonNull Context context) {
        this.context = context;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setAttrs(AttributeSet attrs) {
        this.attrs = attrs;
    }

    //copy from AppCompatViewInflater.createView
    public View createView(){
        View view = null;

        switch (name) {
            case "TextView":
                view = new SkinTextView(context, attrs);
                break;
            case "ImageView":
                view = new SkinImageView(context, attrs);
                break;
            case "Button":
                view = new SkinButton(context, attrs);
                break;
            case "LinearLayout":
                view = new SkinLinearLayout(context,attrs);
                break;
            case "androidx.constraintlayout.widget.ConstraintLayout":
                view = new SkinConstraintLayout(context, attrs);
                break;

        }
        return view;
    }
}
