package com.example.kenjihosaka.sampleandroid.app.common.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kenjihosaka.sampleandroid.EquipmentComponent;
import com.example.kenjihosaka.sampleandroid.MyApplication;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public EquipmentComponent getEquipmentComponent() {
        return ((MyApplication)getApplication()).getComponent();
    }
}
