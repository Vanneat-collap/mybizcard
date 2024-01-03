package com.vannet.mybizcard_rebuild.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by Huo Chhunleng on 27/Jun/2018.
 *
 * References :
 *      https://stackoverflow.com/a/48531811/2365580
 *      https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
 *      https://gunhansancar.com/change-language-programmatically-in-android/
 *
 * Usage :
 *
 *  1. Create A class Ex: MyApp extends Application
 *      *@Override
 *      protected void attachBaseContext(Context base) {
 *          super.attachBaseContext(LocaleHelper.onAttach(base));
 *      }
 *
 *      //On android API 19(Galaxy note 2) after change language and then you use camera function
 *      //the screen will be rotated and then language will set to device language
 *      //so you need to override this method
 * *    @Override
 *      public void onConfigurationChanged(Configuration newConfig) {
 *          super.onConfigurationChanged(newConfig);
 *          LocaleHelper.onAttach(this, Locale.getDefault().getLanguage());
 *      }
 *
 *      in AndroidManifest
 *      <application
 *          android:name=".MyApp"
 *          ...
 *      </application>
 *
 *
 *  2. Create BaseActivity and override method below. For all activities need to extends BaseActivity
 *      *@Override
 *      protected void attachBaseContext(Context base) {
 *          super.attachBaseContext(LocaleHelper.onAttach(base));
 *      }
 *
 *  3. Change Language
 *     LocaleHelper.setLocale(LanguageSettingActivity.this, "en"); // "ko", "ja", "zh"...
 *
 *  4. On android API >= 24
 *      After change language activity need to recreate to apply
 *      and then you need to override attachBaseContext method again on activity that recreate to set language works correctly
 *      *Note* if you have first and second activity need to recreate
 *          apply this method only once (on which activity that recreate after change language)
 **/

public class LocaleHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        return setLocale(context, lang);
    }

    public static Context onAttach(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        return setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static Context setLocale(Context context, String language) {
        persist(context, language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
}



