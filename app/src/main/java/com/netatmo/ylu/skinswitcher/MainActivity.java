package com.netatmo.ylu.skinswitcher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netatmo.ylu.core.view.SkinActivity;

import java.io.File;

public class MainActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageView imageView = findViewById(R.id.iv_avatar);
        imageView.setImageResource(R.drawable.ic_avatar);
        Switch btn_switch = findViewById(R.id.switch_skin);
        final String skinPath = getSkinPath();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }
        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //int uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (isChecked) {
                    //setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    setSkin(skinPath, R.color.defaultTextColor);
                } else {
                    setSkin(null, R.color.defaultTextColor);
                    //setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
        RecyclerView rv = findViewById(R.id.timeline);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ContentAdapter adapter = new ContentAdapter();
        rv.setAdapter(adapter);
    }

    @Override
    public boolean openSkin() {
        return true;
    }

    private String getSkinPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "my.skin";
    }
}
