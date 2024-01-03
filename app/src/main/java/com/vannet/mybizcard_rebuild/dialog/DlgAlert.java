package com.vannet.mybizcard_rebuild.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vannet.mybizcard_rebuild.R;

public class DlgAlert {
    private static OnDialogListener mlistener;
    private static DlgWindow dialogWindow;

    public enum DIALOG_BTN {
        LEFT_BTN, RIGHT_BTN
    }

    public static DlgWindow DlgAlertOk(Context context, String alert_title, String alert_message, String alert_right, OnDialogListener listener) {
        mlistener = listener;

        View mLayout = View.inflate(context, R.layout.comm_dialog_system, null);
        dialogWindow = new DlgWindow(context, mLayout);
        TextView tv_title    = mLayout.findViewById(R.id.tv_dlg_title);
        TextView tv_message  = mLayout.findViewById(R.id.tv_dlg_message);

        Button mOk     = mLayout.findViewById(R.id.btn_dlg_ok);
        Button mCancel = mLayout.findViewById(R.id.btn_dlg_cancel);

        tv_title.setText(alert_title);
        tv_message.setText(alert_message);

        mCancel.setVisibility(View.GONE);

        mOk.setText(alert_right);
        mOk.setOnClickListener(view -> {
            dialogWindow.dismiss();
            if (mlistener != null){
                mlistener.onClickDlgButton(1, DIALOG_BTN.RIGHT_BTN);
            }
        });

        return dialogWindow;
    }

    public interface OnDialogListener {
        void onClickDlgButton(int dialogIndex, DIALOG_BTN buttonType);
    }
}
