package com.vannet.mybizcard_rebuild.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.core.content.ContextCompat;


import com.vannet.mybizcard_rebuild.R;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 6/9/2016.
 */
public class Utils {

    /*
     * to hide keyboard
     */
    @SuppressLint("ClickableViewAccessibility")
    public static void setHideKeyboard(final Context context, View view) {

        try {
            //Set up touch listener for non-text box views to hide keyboard.
            if (!(view instanceof EditText || view instanceof ScrollView)) {

                view.setOnTouchListener((v, event) -> {
                    InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return false;
                });
            }

            //If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {

                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                    View innerView = ((ViewGroup) view).getChildAt(i);

                    setHideKeyboard(context, innerView);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /*
     * get current activity
     */
    public static boolean getCurrentActivity(Context context, String packageActivity) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am != null ? am.getRunningTasks(1).get(0).topActivity : null;

        String currClass = (cn != null ? cn.getClassName() : null);
        if (currClass != null) {
            if (currClass.equals(packageActivity)) {
                return true;
            }
        }
        return false;
    }
}
