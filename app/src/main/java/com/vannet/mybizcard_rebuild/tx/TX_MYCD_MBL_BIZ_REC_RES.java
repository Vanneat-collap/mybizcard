package com.vannet.mybizcard_rebuild.tx;

import com.webcash.sws.network.tx.TxMessage;

import org.json.JSONException;

public class TX_MYCD_MBL_BIZ_REC_RES extends TxMessage {

    private TX_MYCD_MBL_BIZ_REC_RES_DATA mTxKeyData;
    private class TX_MYCD_MBL_BIZ_REC_RES_DATA{
        private String BIZ_NO = "BIZ_NO";
        private String BIZ_NM = "BIZ_NM";
    }

    /*
     * init
     */
    public TX_MYCD_MBL_BIZ_REC_RES(Object obj) throws Exception{
        mTxKeyData = new TX_MYCD_MBL_BIZ_REC_RES_DATA();
        super.initRecvMessage(obj);
    }

    /*
     * 사업자번호
     */
    public String getBIZ_NO() throws JSONException {
        return getString(mTxKeyData.BIZ_NO);
    }

    /*
     * 사업자명
     */
    public String getBIZ_NM() throws JSONException{
        return getString(mTxKeyData.BIZ_NM);
    }

}
