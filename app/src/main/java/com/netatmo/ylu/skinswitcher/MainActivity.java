package com.netatmo.ylu.skinswitcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.netatmo.ylu.core.view.SkinActivity;

public class MainActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Button button = findViewById(R.id.btn_switch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (uiMode){
                    case Configuration.UI_MODE_NIGHT_NO:
                        setSkinMode(AppCompatDelegate.MODE_NIGHT_YES);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        setSkinMode(AppCompatDelegate.MODE_NIGHT_NO);
                        break;
                }
            }
        });


    }

    @Override
    public boolean openSkin() {
        return true;
    }
}
