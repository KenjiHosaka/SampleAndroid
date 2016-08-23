package com.example.kenjihosaka.sampleandroid;


import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    private EquipmentComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        component = DaggerEquipmentComponent.builder()
                .appModule(new AppModule(this))
                .build();
        JodaTimeAndroid.init(this);
    }

    public EquipmentComponent getComponent() {
        return component;
    }
}
