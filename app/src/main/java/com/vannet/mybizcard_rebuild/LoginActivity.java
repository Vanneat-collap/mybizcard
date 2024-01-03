package com.vannet.mybizcard_rebuild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.vannet.mybizcard_rebuild.constant.Constants;
import com.vannet.mybizcard_rebuild.dialog.DlgAlert;
import com.vannet.mybizcard_rebuild.tran.ComLoading;
import com.vannet.mybizcard_rebuild.tran.ComTran;
import com.vannet.mybizcard_rebuild.tx.TX_MYCD_MBL_P009_REQ;
import com.vannet.mybizcard_rebuild.tx.TX_MYCD_MBL_P009_RES;
import com.vannet.mybizcard_rebuild.util.PasswordTransformation;
import com.vannet.mybizcard_rebuild.util.Utils;
import com.webcash.sws.log.DevLog;
import com.webcash.sws.pref.MemoryPreferenceDelegator;
import com.webcash.sws.pref.PreferenceDelegator;
import com.webcash.sws.secure.XorCrypto;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,ComTran.OnComTranListener {

    private ImageView mClearId;
    private EditText mUserID, mUserPW;
    private ComTran mComTran;
    private ComLoading mLoading;
    private CheckBox mCheckRemember;
    private CheckBox mCheckPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
         * set keyboard hide when touch anywhere
         */
        Utils.setHideKeyboard(getApplicationContext(), findViewById(R.id.ll_main_login));

        mComTran = new ComTran(this, this);
        mLoading = new ComLoading(this);

        /*
         * initView
         */
        initView();


    }
    /*
     * init view activity
     */
    private void initView(){
        Button mBtnLogin  = findViewById(R.id.btn_login);

        mUserID           = findViewById(R.id.et_id);
        mUserPW           = findViewById(R.id.et_pwd);
        mCheckRemember    = findViewById(R.id.chk_remember_me);
        mClearId          = findViewById(R.id.iv_clear_id);
        mCheckPassword    = findViewById(R.id.switch_visibility);

        /*
         * set button listener
         */
        mBtnLogin.setOnClickListener(this);
        mClearId.setOnClickListener(this);

        mUserID.addTextChangedListener(mIDTextWatcher);
        mUserID.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !mUserID.getText().toString().equals("")) {
                mClearId.setVisibility(View.VISIBLE);
            }
            mCheckPassword.setVisibility(View.GONE); /* hide eye image button */
        });

        /*
         * set masking password to edit text
         */
        mUserPW.setTransformationMethod(new PasswordTransformation());
        mUserPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s == null || "".equals(s.toString())) {
                    mCheckPassword.setVisibility(View.GONE);
                    return;
                }
                mCheckPassword.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*
         * set show / hide password
         */
        mCheckPassword.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                //password is visible
                mUserPW.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                mUserPW.setSelection(mUserPW.getText().length());
            } else {
                //password gets hided
                mUserPW.setTransformationMethod(PasswordTransformation.getInstance());
                mUserPW.setSelection(mUserPW.getText().length());
            }
        });


        /*
         * set eye image : show / hide password
         */
        mUserPW.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus && !mUserPW.getText().toString().equals("")){
                mCheckPassword.setVisibility(View.VISIBLE);
            }
            mClearId.setVisibility(View.GONE); /* hide x image button */
        });

    }
    /*
     * request data to server
     */
    private void callLogin() {
        try{
            TX_MYCD_MBL_P009_REQ req = new TX_MYCD_MBL_P009_REQ();
            req.setUSE_INTT_ID("");
            req.setUSER_ID(mUserID.getText().toString().trim());
            req.setENC_GB("0");
            req.setMOBL_CD("1"); // 1:비플법인카드, 4:비플우리카드, 5:NH소호비즈
            req.setUSER_PW(XorCrypto.encoding(mUserPW.getText().toString().trim()));
            req.setSESSION_ID("");
            req.setTOKEN("");
            req.setNATION_CD("840");

            mComTran.requestData(TX_MYCD_MBL_P009_REQ.TXNO, req.getSendMessage(), false);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showEmpty(String errmsg) {
        DlgAlert.DlgAlertOk(LoginActivity.this, getString(R.string.DlG_ALERT_TITER), errmsg, getString(R.string.DLG_ALERT_OK), null);
    }

    TextWatcher mIDTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().equals("")){
                mClearId.setVisibility(View.GONE);
            }else {
                mClearId.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();/*
         * login
         */
        if (id == R.id.btn_login) {
            if (TextUtils.isEmpty(mUserID.getText().toString()) || TextUtils.isEmpty(mUserPW.getText().toString())) {
                showEmpty(getResources().getString(R.string.A005_9));
            } else {
                mLoading.showProgressDialog();
                callLogin();
            }
            /*
             * click -> x button to clear id
             */
        } else if (id == R.id.iv_clear_id) {
            mUserID.setText("");
            mUserID.requestFocus();
        }
    }

    @Override
    public void onTranResponse(String tranCode, Object object) {
        try {
            if(tranCode.equals(TX_MYCD_MBL_P009_REQ.TXNO)) {
                //set cookie 세션
                String bizURL = "https://webank-dev.appplay.co.kr/CardAPI.do";
                mComTran.setCookie(bizURL);
                TX_MYCD_MBL_P009_RES res = new TX_MYCD_MBL_P009_RES(object);
                DevLog.devLog("MYCD_MBL_P009: "+ res);
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onTranError(String tranCode, Object object) {

    }
}