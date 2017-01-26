package com.example.macbook.libprac;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by macbook on 2017. 1. 26..
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        //default 대신 이걸 호출해서 초기화
    }
}
