package com.vannet.mybizcard_rebuild.tx;

/**
 *  Create by Huo Chhunleng on 21/Oct/2019.
 */

public class TX_MYCD_MBL_P009_RES extends TX_MYCD_MBL_P007_RES {

    private Key key;		// 전문명세 필드

    public TX_MYCD_MBL_P009_RES(Object obj) throws Exception{
        super(obj);
        key = new Key();
    }

    public TX_MYCD_MBL_USE_INTT_REC getUSE_INTT_REC() throws Exception {
        return new TX_MYCD_MBL_USE_INTT_REC(getRecord(key.USE_INTT_REC));
    }

    /**
     * 전문 Data 필드 설정
     */
    private class Key {
        private String USE_INTT_REC	=	"USE_INTT_REC";
    }
}
