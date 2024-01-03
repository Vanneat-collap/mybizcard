package com.vannet.mybizcard_rebuild.tx;

import com.webcash.sws.network.tx.TxMessage;

import org.json.JSONException;


public class TX_MYCD_MBL_P007_REQ extends TxMessage {

    public static final String TXNO= "MYCD_MBL_P007";	// 전문 구분 코드

    private TX_MYCD_MBL_P007_REQ_DATA mTxKeyData;		// 전문명세 필드

    public TX_MYCD_MBL_P007_REQ() throws Exception{

        mTxKeyData = new TX_MYCD_MBL_P007_REQ_DATA();
        super.initSendMessage();

    }

    /**
     * 사용자아이디
     * @param value
     * @throws JSONException
     */
    public void setUSER_ID(String value) throws JSONException{
        mSendMessage.put(mTxKeyData.USER_ID, value);
    }


    /**
     * 사용자비밀번호
     * @param value
     * @throws JSONException
     */
    public void setUSER_PW(String value) throws JSONException{
        mSendMessage.put(mTxKeyData.USER_PW, value);
    }

    /**
     * 세션아이디
     * @param value
     * @throws JSONException
     */
    public void setSESSION_ID(String value) throws JSONException{
        mSendMessage.put(mTxKeyData.SESSION_ID, value);
    }

    /**
     * 토큰
     * @param value
     * @throws JSONException
     */
    public void setTOKEN(String value) throws JSONException{
        mSendMessage.put(mTxKeyData.TOKEN, value);
    }

    /**
     * 암호화구분
     * @param value
     * @throws JSONException
    */
    public void setENC_GB(String value) throws JSONException{
        mSendMessage.put(mTxKeyData.ENC_GB, value);
    }

    /**
     * 모바일코드
     * @param value
     * @throws JSONException
     */
    public void setMOBL_CD(String value) throws JSONException{
        mSendMessage.put(mTxKeyData.MOBL_CD, value);
    }

    /**
     * 전문 Data 필드 설정
     */
    private class TX_MYCD_MBL_P007_REQ_DATA {
        private String USER_ID	    =	"USER_ID";		//사용자아이디
        private String USER_PW	    =	"USER_PW";		//사용자비밀번호
        private String SESSION_ID	=	"SESSION_ID";	//세션아이디
        private String TOKEN	    =	"TOKEN";		//토큰
        private String ENC_GB	    =	"ENC_GB";		//암호화구분
        private String MOBL_CD	    =	"MOBL_CD";		//모바일코드
    }
}
