package com.vannet.mybizcard_rebuild.tx;

import com.webcash.sws.network.tx.TxMessage;

import org.json.JSONException;

/**
 * Login response
 */

/*
 * Modified by RET Sokheang
 * On 2018-10-15
 * Description : Add one more field when response (CUST_DSGN_YN)
 */
public class TX_MYCD_MBL_P007_RES extends TxMessage {

    private TX_IBK_MBL_P007_RES_DATA mTxKeyData;		// 전문명세 필드

    public TX_MYCD_MBL_P007_RES(Object obj) throws Exception{
        mTxKeyData = new TX_IBK_MBL_P007_RES_DATA();
        super.initRecvMessage(obj);
    }

    /**
     * 사용자아이디
     */
    public String getUSER_ID() throws JSONException {
        return getString(mTxKeyData.USER_ID);
    }

    /**
     * 사용자명
     */
    public String getUSER_NM() throws JSONException {
        return getString(mTxKeyData.USER_NM);
    }

    /**
     * 사용자이미지경로
     */
    public String getUSER_IMG_PATH() throws JSONException {
        return getString(mTxKeyData.USER_IMG_PATH);
    }

    /**
     * 사업장명
     */
    public String getBSNN_NM() throws JSONException {
        return getString(mTxKeyData.BSNN_NM);
    }

    /**
     * 인증 경로
     */
    public String getCRTC_PATH() throws JSONException {
        return getString(mTxKeyData.CRTC_PATH);
    }

    /**
     * 지출결의사용여부
     */
    public String getAPPR_USE_YN() throws JSONException {
        return getString(mTxKeyData.APPR_USE_YN);
    }

    /**
     * 포탈아이디
     */
    public String getPTL_ID() throws JSONException {
        return getString(mTxKeyData.PTL_ID);
    }

    /**
     * 채널아이디
     */
    public String getCHNL_ID() throws JSONException {
        return getString(mTxKeyData.CHNL_ID);
    }

    /**
     * 이용기관아이디
     */
    public String getUSE_INTT_ID() throws JSONException {
        return getString(mTxKeyData.USE_INTT_ID);
    }

    /*
     * WeAuth 접속사용자의 사업장정보
     */
    public TX_MYCD_MBL_BIZ_REC_RES getBIZ_REC() throws Exception {
        return new TX_MYCD_MBL_BIZ_REC_RES(getRecord(mTxKeyData.BIZ_REC));
    }

    public String getERP_APPR_CD() throws JSONException {
        return getString(mTxKeyData.ERP_APPR_CD);
    }

    public String getCUST_DSGN_YN() throws JSONException {
        return getString(mTxKeyData.CUST_DSGN_YN);
    }

    public String getRCPT_ENTR_EXCP_YN() throws JSONException {
        return getString(mTxKeyData.RCPT_ENTR_EXCP_YN);
    }
    public String getRCPT_MAGR_CHG_YN() throws JSONException {
        return getString(mTxKeyData.RCPT_MAGR_CHG_YN);
    }

    /**
     * ERP 예산조회여부
     * Y : 결의시 ERP 예산조회(버턴표시)
     * @return
     * @throws JSONException
     */
    public String getERP_BGT_YN() throws JSONException {
        return getString(mTxKeyData.ERP_BGT_YN);
    }

    /**
     * 사업자번
     * @return
     * @throws JSONException
     */
    public String getBSNN_NO() throws JSONException {
        return getString(mTxKeyData.BSNN_NO);
    }

    /**
     * 부서명
     * @return
     * @throws JSONException
     */
    public String getDVSN_NM() throws JSONException {
        return getString(mTxKeyData.DVSN_NM);
    }

    /**
     * 휴대폰번호
     * @return
     * @throws JSONException
     */
    public String getCLPH_NO() throws JSONException {
        return getString(mTxKeyData.CLPH_NO);
    }

    /**
     * 이메일
     * @return
     * @throws JSONException
     */
    public String getEML() throws JSONException {
        return getString(mTxKeyData.EML);
    }

    public String getEXTN_DOCU_USE_YN() throws  JSONException{
        return  getString(mTxKeyData.EXTN_DOCU_USE_YN);
    }

    /**
     * 전문 Data 필드 설정
     */
    private class TX_IBK_MBL_P007_RES_DATA {
        private String USER_ID		        =	"USER_ID";				// 사용자아이디
        private String USER_NM			    =	"USER_NM";				// 사용자이미지경로
        private String USER_IMG_PATH	    =	"USER_IMG_PATH";		// 사용자이미지경로
        private String BSNN_NM			    =	"BSNN_NM";				// 사업장명
        private String CRTC_PATH		    =	"CRTC_PATH";			// 인증 경로
        private String APPR_USE_YN		    =	"PPP_APPR_USE_YN";		// 지출결의사용여부
        private String PTL_ID               =   "PTL_ID";               // 포탈아이디
        private String CHNL_ID              =   "CHNL_ID";              // 채널아이디
        private String USE_INTT_ID          =   "USE_INTT_ID";          // 이용기관아이디
        private String BIZ_REC              =   "BIZ_REC";              // 사업장정보
        private String ERP_APPR_CD          =   "ERP_APPR_CD";
        private String CUST_DSGN_YN         =   "CUST_DSGN_YN";         // 거래처지정건수_사용여부
        private String RCPT_ENTR_EXCP_YN    =   "RCPT_ENTR_EXCP_YN";
        private String RCPT_MAGR_CHG_YN     =   "RCPT_MAGR_CHG_YN";     // 영수증담당자 변졍가능여부
        private String ERP_BGT_YN           =   "ERP_BGT_YN";           // [2020.09.22] add :D
        private String BSNN_NO              =   "BSNN_NO";              // [2020.11.22] add
        private String DVSN_NM              =   "DVSN_NM";              //  ..........
        private String CLPH_NO              =   "CLPH_NO";              //  ..........
        private String EML                  =   "EML";                  //  ..........
        private String EXTN_DOCU_USE_YN     =   "EXTN_DOCU_USE_YN";     //  ..........
    }
}
