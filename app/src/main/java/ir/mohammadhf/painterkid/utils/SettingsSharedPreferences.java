package ir.mohammadhf.painterkid.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsSharedPreferences {
    private static final String KEY_TALKER_NAME = "key_talker_name";
    private static final String KEY_MUSIC_NAME = "key_music_name";
    private static final String KEY_ADS_REPEAT_PERIOD = "key_ads_repeat_period";

    private SharedPreferences sharedPreferences;

    public SettingsSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("settings shared pref", Context.MODE_PRIVATE);
    }

    public String getTalkerName() {
        return sharedPreferences.getString(KEY_TALKER_NAME, "Rina");
    }

    public void setTalkerName(String talkerName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TALKER_NAME, talkerName);
        editor.apply();
    }

    public String getMusicName() {
        return sharedPreferences.getString(KEY_MUSIC_NAME, "positive");
    }

    public void setMusicName(String musicName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MUSIC_NAME, musicName);
        editor.apply();
    }

    public int getAdsRepeatPeriod() {
        return sharedPreferences.getInt(KEY_ADS_REPEAT_PERIOD, 0);
    }

    public void setAdsRepeatPeriod(int count) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ADS_REPEAT_PERIOD, count);
        editor.apply();
    }
}
