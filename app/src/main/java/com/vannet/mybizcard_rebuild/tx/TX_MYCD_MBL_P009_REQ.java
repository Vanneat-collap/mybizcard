package com.vannet.mybizcard_rebuild.tx;

import org.json.JSONException;

/**
 *  Create by Huo Chhunleng on 21/Oct/2019.
 */

public class TX_MYCD_MBL_P009_REQ extends TX_MYCD_MBL_P007_REQ {

    public static final String TXNO= "MYCD_MBL_P009";	// 전문 구분 코드

    private Key key;		// 전문명세 필드

    public TX_MYCD_MBL_P009_REQ() throws Exception{
        key = new Key();
        super.initSendMessage();
    }

    public void setUSE_INTT_ID(String value) throws JSONException{
        mSendMessage.put(key.USE_INTT_ID, value);
    }

    public void setNATION_CD(String value) throws JSONException{
        mSendMessage.put(key.NATION_CD, value);
    }

    /**
     * 전문 Data 필드 설정
     */
    private class Key {
        private String USE_INTT_ID	=	"USE_INTT_ID";		//모바일코드
        private String NATION_CD    =   "NATION_CD";
    }
}
