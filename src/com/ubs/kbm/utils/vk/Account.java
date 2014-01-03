package com.ubs.kbm.utils.vk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Account {
	private String accessToken;
	private long user_id;

	public Account() {
	}

	public Account(String accessToken, long id) {
		this.accessToken = accessToken;
		this.user_id = id;
	}

	public void save(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString("access_token", accessToken);
		editor.putLong("user_id", user_id);
		editor.commit();
	}

	public void restore(Context context) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		accessToken = prefs.getString("access_token", null);
		user_id = prefs.getLong("user_id", 0);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public long getUser_id() {
		return user_id;
	}
	
}
