package com.webcash.sws.pref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * MemoryPreferenceDelegator
 * <br><br>
 * - App 실행 중 저장이 필요한 Data 관리 Delegator <br>
 * - App 가 종료되면 데이터가 삭제된다. <br>
 *
 * @author Webcash Smart
 * @since 2017. 7. 20.
 **/
public class MemoryPreferenceDelegator {

	/** MemoryPreferenceDelegator SingleInstance */
	private static MemoryPreferenceDelegator mInstance;

	/** MemoryPreferenceDelegator 데이터를 저장하고 있는 HashMap */
	private static HashMap<String, Object> mMemoyPrefMap;

	/**
	 * SingleInstance 생성.
	 * @return MemoryPreferenceDelegator
	 */
	public static MemoryPreferenceDelegator getInstance() {
		if (mInstance == null) {
			mInstance = new MemoryPreferenceDelegator();
		}
		return mInstance;
	}

	/**
	 * 생성자
	 */
	private MemoryPreferenceDelegator() {
		mMemoyPrefMap = new HashMap<String, Object>();
	}

	/**
	 * Key 존재 여부 리턴 <br>
	 * @param key Key
	 * @return Key 존재 여부
	 */
	public boolean contains(String key) {
		return mMemoyPrefMap.containsKey(key);
	}

	/**
	 * Key 에 해당하는 String Value 리턴 <br>
	 * @param key data 구분 Key
	 * @return key 에 해당하는 String Value
	 */
	public String get(String key) {
		if (mMemoyPrefMap.containsKey(key)) {
			return (String)mMemoyPrefMap.get(key);
		}
		return "";
	}

	/**
	 * Key 에 해당하는 Object Value 리턴 <br>
	 * @param key data 구분 Key
	 * @return key 에 해당하는 Object Value
	 */
	public Object getObject(String key) {
		if (mMemoyPrefMap.containsKey(key)) {
			return mMemoyPrefMap.get(key);
		}
		return null;
	}

	/**
	 * Key 에 해당하는 Int Value 리턴 <br>
	 * @param key data 구분 Key
	 * @return key 에 해당하는 Int Value
	 */
	public int getInt(String key) {
		if (mMemoyPrefMap.containsKey(key)) {
			return (int)mMemoyPrefMap.get(key);
		}
		return 0;
	}

	/**
	 *  Key 에 해당하는 JSONArray Value 리턴 <br>
	 * @param key data 구분 Key
	 * @return key 에 해당하는 JSONArray Value
	 */
	public JSONArray getJsonArray(String key) {
		if (mMemoyPrefMap.containsKey(key)) {
			return (JSONArray)mMemoyPrefMap.get(key);
		}
		return null;
	}

	/**
	 *  Key 에 해당하는 JSONObject Value 리턴<br>
	 * @param key - data 구분 Key
	 * @return key 에 해당하는 JSONObject Value
	 */
	public JSONObject getJsonObject(String key) {
		if (mMemoyPrefMap.containsKey(key)) {
			return (JSONObject)mMemoyPrefMap.get(key);
		}
		return null;
	}

	/**
	 * Memory 에 Object data 저장 <br>
	 * @param key data 구분 Key
	 * @param value 저장할 Object Value
	 */
	public void put(String key, Object value) {
		mMemoyPrefMap.put(key, value);
	}

	/**
	 * Memory 에 JSONArray data 저장 <br>
	 * @param key data 구분 Key
	 * @param value 저장할 JSONArray Value
	 */
	public void put(String key, JSONArray value) {
		mMemoyPrefMap.put(key, value);
	}

	/**
	 * Memory 에 JSONObject data 저장 <br>
	 * @param key data 구분 Key
	 * @param value 저장할 JSONObject Value
	 */
	public void put(String key, JSONObject value) {
		mMemoyPrefMap.put(key, value);
	}

	/**
	 *  해당 Key data 삭제 <br>
	 * @param key 삭제할 Data Key
	 */
	public void remove(String key) {
		mMemoyPrefMap.remove(key);
	}

	/**
	 * removeAll
	 * <br><br>
	 *  모든 데이터 Clear
	 */
	public void removeAll() {
		mMemoyPrefMap.clear();
	}
}