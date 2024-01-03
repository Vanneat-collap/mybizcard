package com.webcash.sws.log;

import android.util.Log;

import com.android.volley.BuildConfig;

/**
 * DevLog
 * <br><br>
 * 공통으로 사용되는 개발용 로그 <br>
 *
 * @author Webcash Smart
 * @since 2017.07
 **/
public class DevLog {

	/** 개발 / 운영 여부 */
	public static boolean DEBUG = true;
	/** 기본 태그 String */
	private static final String DEFAULT_TAG = "DEV_LOG";

	/**
	 * Debug 사용 유무
	 * <br><br>
	 * @param use DEBUG 유무 true : 로그 출력, false : 로그 미출력
     */
	public static void setDebug(boolean use) {
		DEBUG = use;
	}

	/**
	 * devLog
	 * <br><br>
	 * 개발버전만 로그를 출력, TAG : DEV_LOG, Priority : VERBOSE <br>
	 * @param value	: log value
	 */
	public static void devLog(String value) {

		// 배포용일 경우 로그 출력 하지않음.
		if (!DEBUG) {
			return;
		}

		largeLog( DEFAULT_TAG, value);
	}

	/**
	 * devLog
	 * <br><br>
	 * 개발버전만 로그 출력, Priority : VERBOSE <br>
	 * @param tag : tag
	 * @param value : log value
	 */
	public static void devLog(String tag, String value) {

		// 배포용일 경우 로그 출력 하지않음.
		if (!DEBUG) {
			return;
		}

		largeLog( tag, value);
	}

	/**
	 * eLog
	 * <br><br>
	 * - 개발버전만 로그 출력 ,TAG : class name, Priority : ERROR <br>
	 * - Tag 는 전달한 클래스의 이름 출력 <br>
	 * @param cls : class
	 * @param value : log value
	 */
	public static void eLog(Class cls, String value) {

		// 배포용일 경우 로그 출력 하지않음.
		if (!DEBUG) {
			return;
		}

		Log.e(cls.getSimpleName(), value);
	}

	/**
	 * eLog
	 * <br><br>
	 * 개발버전만 로그 출력, Priority : ERROR <br>
	 * @param tag : tag
	 * @param value : log value
	 */
	public static void eLog(String tag, String value) {

		// 배포용일 경우 로그 출력 하지않음.
		if (!DEBUG) {
			return;
		}

		Log.e(tag, value);
	}
	/**
	 * e
	 * <br><br>
	 * 개발버전만 로그 출력, TAG : DEV_LOG, Priority : ERROR <br>
	 * @param value : log value
	 */
	public static void e(Exception value) {
		// 배포용일 경우 로그 출력 하지않음.
		if (!DEBUG) {
			return;
		}

		Log.e(DEFAULT_TAG, value.getMessage());
	}

	/**
	 * wLog
	 * <br><br>
	 * 개발버전만 로그 출력, Priority : WARN <br>
	 * @param tag : tag
	 * @param value : log value
	 */
	public static void wLog(String tag, String value) {

		// 배포용일 경우 로그 출력 하지않음.
		if (!DEBUG) {
			return;
		}

		Log.w(tag, value);
	}
	/**
	 * dLog
	 * <br><br>
	 * 개발버전만 로그 출력, Priority : DEBUG <br>
	 * @param tag : tag
	 * @param value : log value
	 */
	public static void dLog(String tag, String value) {

		// 배포용일 경우 로그 출력 하지않음.
		if (!DEBUG) {
			return;
		}
		Log.d(tag, value);
	}
	/**
	 * iLog
	 * <br><br>
	 * 개발버전만 로그 출력, Priority : INFO <br>
	 * @param tag : tag
	 * @param value : log value
	 */
	public static void iLog(String tag, String value) {
		// 배포용일 경우 로그 출력 하지않음.
		if (!DEBUG) {
			return;
		}
		Log.i(tag, value);
	}

	/**
	 * largeLog
	 * <br><br>
	 * log 프린트 / 3000자가 넘는 log도 프린트, Priority : VERBOSE <br>
	 * @param tag	: TAG
	 * @param value	: log value
	 */
	public static void largeLog(String tag, String value) {
		// 3000자가 넘을 경우 3000자까지만 프린트하고 나머지 값은 재귀함수를 호출한다.
		int maxLogSize = 3000;
		for(int i = 0; i <= value.length() / maxLogSize; i++) {
			int start = i * maxLogSize;
			int end = (i+1) * maxLogSize;
			end = Math.min(end, value.length());
			Log.v(tag, value.substring(start, end));
		}
	}
}