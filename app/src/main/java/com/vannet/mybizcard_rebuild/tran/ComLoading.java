package com.vannet.mybizcard_rebuild.tran;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.vannet.mybizcard_rebuild.R;


/*
 * Created by Huo Chhunleng on 25/10/2019.
 */
public class ComLoading extends Dialog{

    public ComLoading(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.comm_loading_activity);
        ImageView imgView = findViewById(R.id.iv_loading);

        AnimationDrawable mFrameAnimation = (AnimationDrawable) imgView.getBackground();
        mFrameAnimation.start();

        if (getWindow() != null){
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setCanceledOnTouchOutside(false);
    }

    public void showProgressDialog() {
        show();
    }

    public void dismissProgressDialog() {
        dismiss();
    }

}
