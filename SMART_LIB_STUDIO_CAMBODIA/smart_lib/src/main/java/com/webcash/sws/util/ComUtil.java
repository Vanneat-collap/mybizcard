package com.webcash.sws.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

/**
 * 공통 util
 * <br/><br/>
 * - 기기 정보 관련 정보를 제공
 *
 * @author Webcash Smart
 * @since 2017. 7. 20.
 */
public class ComUtil {

	/**
	 * 네트워크 연결상태를 반환
	 * <br/><br/>
	 * - 메서드 호출 전 퍼미션 권환 획득 필요 <br>
	 *     : android.permission.ACCESS_NETWORK_STATE <br>
	 * @param ctx Context
	 * @return false : 네트워크 연결안됨, true : 네트워크 연결됨.
	 */
	public static boolean getNetworkStatus(Context ctx) {
		ConnectivityManager Connectivity = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = Connectivity.getActiveNetworkInfo(); //Internet connection status
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
}