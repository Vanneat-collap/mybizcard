package com.vannet.mybizcard_rebuild.tx;

import com.webcash.sws.network.tx.TxMessage;

import org.json.JSONException;

public class TX_MYCD_MBL_USE_INTT_REC extends TxMessage {

    private Key key;
    private class Key{
        private String JOIN_USE_INTT_ID = "JOIN_USE_INTT_ID";
        private String JOIN_BSNN_NM     = "JOIN_BSNN_NM";
    }

    /*
     * init
     */
    public TX_MYCD_MBL_USE_INTT_REC(Object obj) throws Exception{
        key = new Key();
        super.initRecvMessage(obj);
    }

    /*
     * 사업자번호
     */
    public String getJOIN_USE_INTT_ID() throws JSONException {
        return getString(key.JOIN_USE_INTT_ID);
    }

    /*
     * 사업자명
     */
    public String getJOIN_BSNN_NM() throws JSONException{
        return getString(key.JOIN_BSNN_NM);
    }

}
