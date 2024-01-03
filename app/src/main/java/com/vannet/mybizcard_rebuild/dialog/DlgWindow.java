package com.vannet.mybizcard_rebuild.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DlgWindow extends Dialog {

    public DlgWindow(Context context, View layout){
        super(context);

        super.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setCanceledOnTouchOutside(false);
        super.setContentView(layout);
        super.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.show();

    }
}
