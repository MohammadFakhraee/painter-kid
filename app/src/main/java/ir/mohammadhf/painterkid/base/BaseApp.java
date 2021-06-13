package ir.mohammadhf.painterkid.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import me.cheshmak.android.sdk.core.Cheshmak;
import me.cheshmak.cheshmakplussdk.core.CheshmakPlus;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Cheshmak.with(this);
        CheshmakPlus.with(this);
        Cheshmak.initTracker("N3O8LWD3vYhFJCktrne1CQ==");
        CheshmakPlus.setTestMode(false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(WrapContext.setLocale(base));
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        WrapContext.overrideLocale(this);
    }
}
