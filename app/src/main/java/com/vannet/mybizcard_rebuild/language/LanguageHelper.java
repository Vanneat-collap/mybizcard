package com.vannet.mybizcard_rebuild.language;

/*
 * Created by ChanYouvita on 1/18/18.
 */

import android.content.Context;


import com.vannet.mybizcard_rebuild.R;

import java.util.ArrayList;

public class LanguageHelper {
    public static String ISO_CODE  = "ISO_CODE";
    public static String LNGG_DSNC = "LNGG_DSNC";

    public static ArrayList<LanguageItemObject> getLanguageItems(Context context) {
        ArrayList<LanguageItemObject> languageList = new ArrayList<>();
        LanguageItemObject lang = new LanguageItemObject();
        lang.setLoca_key(context.getResources().getString(R.string.LANGUAGE_FLAG_KO));
        lang.setIso_code("ko");
        lang.setLanguage_id(1);
        lang.setFlag_code("ko_flag");
        lang.setNation_cd("");
        languageList.add(lang);

        lang = new LanguageItemObject();
        lang.setLoca_key(context.getResources().getString(R.string.LANGUAGE_FLAG_EN));
        lang.setIso_code("en");
        lang.setLanguage_id(2);
        lang.setFlag_code("en_flag");
        lang.setNation_cd("840");
        languageList.add(lang);

        lang = new LanguageItemObject();
        lang.setLoca_key(context.getResources().getString(R.string.LANGUAGE_FLAG_JP));
        lang.setIso_code("ja");
        lang.setLanguage_id(4);
        lang.setFlag_code("jp_flag");
        lang.setNation_cd("392");
        languageList.add(lang);

        lang = new LanguageItemObject();
        lang.setLoca_key(context.getResources().getString(R.string.LANGUAGE_FLAG_ZH));
        lang.setIso_code("zh");
        lang.setLanguage_id(3);
        lang.setFlag_code("zh_flag");
        lang.setNation_cd("156");
        languageList.add(lang);

        lang = new LanguageItemObject();
        lang.setLoca_key(context.getResources().getString(R.string.LANGUAGE_FLAG_VI));
        lang.setIso_code("vi");
        lang.setLanguage_id(5);
        lang.setFlag_code("vn_flag");
        lang.setNation_cd("704");
        languageList.add(lang);

        return languageList;
    }

    public static String initConvertLanguageUpperCase(String iso) {
        return (iso.equals("ko") ? "df" : iso).toUpperCase();
    }
}
