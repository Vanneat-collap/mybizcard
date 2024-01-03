package com.vannet.mybizcard_rebuild.tran;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.webkit.CookieManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.vannet.mybizcard_rebuild.LoginActivity;
import com.vannet.mybizcard_rebuild.MainActivity;
import com.vannet.mybizcard_rebuild.R;
import com.vannet.mybizcard_rebuild.conf.Conf;
import com.vannet.mybizcard_rebuild.constant.Constants;
import com.vannet.mybizcard_rebuild.dialog.DlgAlert;
import com.vannet.mybizcard_rebuild.language.LanguageHelper;
import com.vannet.mybizcard_rebuild.language.LocaleHelper;
import com.vannet.mybizcard_rebuild.util.Utils;
import com.webcash.sws.log.DevLog;
import com.webcash.sws.network.VolleyNetwork;
import com.webcash.sws.network.internal.NetworkErrorCode;
import com.webcash.sws.network.internal.OnNetworkListener;
import com.webcash.sws.network.tx.JSONHelper;
import com.webcash.sws.pref.MemoryPreferenceDelegator;
import com.webcash.sws.pref.PreferenceDelegator;
import com.webcash.sws.util.ComUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by user on 2016-01-05.
 * 전문 통신 공통 Class
 */
public class ComTran implements OnNetworkListener {

    private Context mContext;                           // Activity Context
    private OnComTranListener mTranListener;            // UI단 통신 결과 Listener
    private VolleyNetwork mVolleyNetwork;               // 통신 Class
    private ComLoading mLoading;
    private String errmsg;
    private String errcd;

    public ComTran(Context context , OnComTranListener listener) {
        mContext = context;
        mTranListener = listener;
        mLoading = new ComLoading(context);
        mVolleyNetwork = new VolleyNetwork(mContext , this);
        mVolleyNetwork.setVolleyTimeout(60 * 1000 * 5);
    }

    public void requestData(String tranCd, HashMap<String, Object> requestData) throws JSONException {
        requestData(tranCd, requestData, true);
    }

    /*
     * MG 전문 호출
     * @param tranCd
     */
    public void requestData(String tranCd , String url) {

        ConnectivityManager connectManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //+ 인터넷 연결 상태
        if((connectManager != null ? connectManager.getActiveNetworkInfo() : null) == null) {
            //+네트워크 연결 오류
            onErrorData(tranCd, NetworkErrorCode.TRNS_ERRCD_INTERNET , null);
            return;
        }
        mVolleyNetwork.requestVolleyNetwork(tranCd, false, url, false);
    }

    /*
     *  전문송신 - JSON Data
     * @param tranCd
     * @param tran_req_data
     */
    @SuppressWarnings("deprecation")
    public void requestData(String tranCd, HashMap<String, Object> tran_req_data, boolean isdialog) throws JSONException {

        String bizURL = "https://webank-dev.appplay.co.kr/CardAPI.do";

        if(isdialog) {
            mLoading.showProgressDialog();
        }

        // check network connection
        if(ComUtil.getNetworkStatus(mContext)) {

            JSONObject jsonObj =  (JSONObject) JSONHelper.toJSON(tran_req_data);

            JSONObject jobjectInput = new JSONObject();
            jobjectInput.put(ComTranCode.CNTS_CRTS_KEY_CODE  , "");
            jobjectInput.put(ComTranCode.KEY_TRAN_CODE  , tranCd);
            jobjectInput.put(ComTranCode.LNGG_DSNC  , LanguageHelper.initConvertLanguageUpperCase(LocaleHelper.getLanguage(mContext)));
            jobjectInput.put(ComTranCode.KEY_REQ_DATA   , jsonObj);

            DevLog.devLog("nryoo", "jsonInput::     " + jobjectInput.toString(1));

            //+header
            HashMap<String, String> headers = new HashMap<>();
            headers.put("charset", "UTF-8");
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            headers.put("User-Agent", Conf.mUserAgent);

            DevLog.devLog("requestData ::",  "Headers:: " + headers.toString());
            mVolleyNetwork.setComHeaders(headers);

            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("JSONData", URLEncoder.encode(jobjectInput.toString(), "UTF-8"));
                mVolleyNetwork.requestVolleyNetwork(tranCd, true, bizURL, params, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //+네트워크 연결 오류
            onErrorData(tranCd, NetworkErrorCode.TRNS_ERRCD_INTERNET, null);
        }
    }

    @Override
    public void onNetworkResponse(final String tranCode, Object object) {

        //+ 전문 응답부
        if(mTranListener == null){
            return;
        }

        JSONObject  jsonOutput;
        JSONArray   jarrayResData   = null;

        try {

            jsonOutput = new JSONObject(URLDecoder.decode(object.toString().replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8"));
            DevLog.devLog("nryoo", "jsonOutput::" +tranCode+ jsonOutput.toString(1));

            if(tranCode.equals("MG_DATA")){
                if(!jsonOutput.isNull(ComTranCode.KEY_RES_DATA)) {
                    jsonOutput = jsonOutput.getJSONObject(ComTranCode.KEY_RES_DATA);
                    if(!jsonOutput.isNull(ComTranCode.KEY_TRAN_RES_DATA)) {
                        jarrayResData = jsonOutput.getJSONArray(ComTranCode.KEY_TRAN_RES_DATA);
                    }
                } else {
                    onErrorData(tranCode, NetworkErrorCode.TRNS_ERRCD_PAGEERR, jsonOutput);
                    return;
                }
            } else {

                    /* 응답 값 */
                    if (!jsonOutput.isNull(ComTranCode.KEY_RSLT_CD)) {
                        //* 오류발생 전문 코드값 처리
                        String resultErrorCd = jsonOutput.getString(ComTranCode.KEY_RSLT_CD);
                        if (!resultErrorCd.equals("0000")) {
//                            if (!tranCode.equals(TX_MYCD_MBL_R001_REQ.TXNO)) {
//                                onErrorData(tranCode, resultErrorCd, jsonOutput);
//                                return;
//                            } else {
//                                mTranListener.onTranError(tranCode, jsonOutput);
//                                mLoading.dismissProgressDialog();
//                                return;
//                            }

                        }
                    }

                    if (!jsonOutput.isNull(ComTranCode.KEY_RES_DATA)) {
                        jarrayResData = new JSONArray();
                        jarrayResData.put(jsonOutput.getJSONObject(ComTranCode.KEY_RES_DATA));
                    }

            }


            //+ 통신 Dialog Dismiss
            mLoading.dismissProgressDialog();

            if (tranCode.equals("")) {
                /*
                 * vita: updated on [15-11-2018]
                 * biplepay json respond
                 * real: https://www.bizplay.co.kr/bp_mall_gate_job.jct?SVC_GB=BPAY
                 * test: http://sportal.dev.weplatform.co.kr:19990/bp_mall_gate_job.jct?SVC_GB=BPAY
                 */
                mTranListener.onTranResponse(tranCode, jsonOutput);
            }else {
                mTranListener.onTranResponse(tranCode, jarrayResData);
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
            onErrorData(tranCode, NetworkErrorCode.TRNS_ERRCD_PASER, null);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            onErrorData(tranCode, NetworkErrorCode.TRNS_ERRCD_PASER, null);
        }
        catch (Exception e) {
            e.printStackTrace();
            onErrorData(tranCode, NetworkErrorCode.APP_ERRCD_UNKNOWN, null);
        }
    }

    @Override
    public void onNetworkError(String tranCode, Object object) {

        String errorStr;
        //+ 전문 오류
        if(object instanceof VolleyError){
            VolleyError error = (VolleyError)object;
            DevLog.devLog("nryoo", "onNetworkError VolleyError::" + error.toString());
            errorStr = error.toString();
        }else         {
            DevLog.devLog("nryoo", "onNetworkError jsonOutput::" + object.toString());
            errorStr =  object.toString();
        }
        onErrorData(tranCode, NetworkErrorCode.TRNS_ERRCD_PAGEERR, errorStr);
    }


    /*
     * 통신중 에러 처리
     * @param tranCode
     * @param errcd
     * @param error
     */
    private void onErrorData(final String tranCode, final String err, Object error) {

        try {
            mLoading.dismissProgressDialog();

            errmsg = "";
            errcd  = err;

            if (Utils.getCurrentActivity(mContext, "com.my.bizcard.activity.IntroActivity") || Utils.getCurrentActivity(mContext, "com.my.bizcard.activity.user.LoginActivity")) {
                /* check for special character errcd */
                if (errcd == null || errcd.isEmpty() || errcd.contains("!#$%&'()*+,-./:;<=>?@[]^_`{|}~")) {
                    errcd = "5510";
                }
            }

            if (errcd.equals(NetworkErrorCode.TRNS_ERRCD_INTERNET)) {
                errmsg = errmsg + mContext.getString(R.string.COM_TRAN_1);
            } else if (errcd.equals(NetworkErrorCode.TRNS_ERRCD_MAKE)
                    || errcd.equals(NetworkErrorCode.TRNS_ERRCD_PASER)
                    || errcd.equals(NetworkErrorCode.APP_ERRCD_UNKNOWN)) {
                // 공통에서 전문발송 데이타를 만드는중에러
                // 전문결과 받은후 공통에서 json 파싱중 에러 (전문 형식 잘못됨)
                errmsg = errmsg + mContext.getString(R.string.COM_TRAN_2);
            } else if (errcd.equals(NetworkErrorCode.TRNS_ERRCD_PAGEERR)) {
                errmsg = errmsg + mContext.getString(R.string.COM_TRAN_3);
            } else if (errcd.equals("M_2001") || errcd.equals("M_2002")) {
                if (error instanceof JSONObject) {
                    JSONObject jsonObj = (JSONObject) error;
                    if (jsonObj.has(ComTranCode.KEY_RSLT_MSG)) {

                        try {
                            errmsg = jsonObj.getString(ComTranCode.KEY_RSLT_MSG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    errmsg = errmsg + error.toString();
                }

            } else {

                if (error instanceof JSONObject) {
                    JSONObject jsonObj = (JSONObject) error;
                    if (jsonObj.has(ComTranCode.KEY_RSLT_MSG)) {

                        try {
                            errmsg = jsonObj.getString(ComTranCode.KEY_RSLT_MSG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    errmsg = errmsg + error.toString();
                }
            }

            final String ErrorTranCode;
            final Object objError = error;
            ErrorTranCode = tranCode;

            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!((Activity) mContext).isFinishing()) {
                        DlgAlert.DlgAlertOk(mContext, mContext.getString(R.string.DlG_ALERT_TITER), errmsg, mContext.getString(R.string.DLG_ALERT_OK), null);
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  통신 처리 UI단 전달 Listener
     */
    public interface OnComTranListener {
        void onTranResponse(String tranCode, Object object);
        void onTranError(String tranCode, Object object);
    }

    private class ComTranCode {

        //+요청
        static final String KEY_REQ_DATA 				= "REQ_DATA";	// 전문 요청 DATA
        static final String KEY_TRAN_CODE 				= "TRAN_NO";	// 전문 요청 CODE
        static final String CNTS_CRTS_KEY_CODE 			= "CNTS_CRTS_KEY";	// 전문 요청 CODE
        static final String LNGG_DSNC                   = "LNGG_DSNC";  // 공통부에 LNGG_DSNC항목 추가

        //+응답
        static final String KEY_RSLT_CD 				= "RSLT_CD";	// 결과코드
        static final String KEY_RSLT_MSG				= "RSLT_MSG";	// 결과메세지
        static final String KEY_RES_DATA 				= "RESP_DATA";	// 응답데이타
        static final String KEY_TRAN_RES_DATA 			= "_tran_res_data";	// 정상 응답 전문
    }



    /**
     * sync 세션
     */
    public void setCookie(String url) {
        mVolleyNetwork.syncSessionWithWebView(url);
    }

    /**
     * get JSESSIONID
     */
    private String getJSessionId(String url) {
        String mValue = "";
        try {
            String mCookie = CookieManager.getInstance().getCookie(url);
            DevLog.eLog(">>>>", "getJSessionId: " + mCookie);
            if (mCookie != null) {
                StringTokenizer mCookieString = new StringTokenizer(mCookie, ";");

                while (mCookieString.hasMoreTokens()) {
                    String mJSessionID = mCookieString.nextToken().trim();
                    if (mJSessionID.contains("JSESSIONID")) {
                        mValue = mJSessionID;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return mValue;
    }

}
