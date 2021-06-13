package ir.mohammadhf.painterkid.base;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

import ir.mohammadhf.painterkid.BuildConfig;

class WrapContext {

    static Context setLocale(Context context) {
        return setLocale(context, getLanguage());
    }

    private static Context setLocale(Context context, Locale locale) {
        if (locale == null) return context;

        Locale.setDefault(locale);
        Configuration newConfig = new Configuration();
        newConfig.setLocale(locale);
        return context.createConfigurationContext(newConfig);
    }

    private static Locale getLanguage() {
        return new Locale(BuildConfig.CURRENT_LANG);
    }

    static void overrideLocale(Context context) {
        Locale chosenLocale = getLanguage();
        Locale.setDefault(chosenLocale);
        Configuration configuration = new Configuration();
        configuration.setLocale(chosenLocale);
        context.getResources().updateConfiguration(configuration,
                context.getResources().getDisplayMetrics());
    }
}
