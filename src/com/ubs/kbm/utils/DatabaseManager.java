package com.ubs.kbm.utils;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.ubs.kbm.dao.DatabaseHelper;

public class DatabaseManager {
	private DatabaseHelper databaseHelper;

	public DatabaseHelper getHelper(Context context) {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(context,
					DatabaseHelper.class);
		}
		return databaseHelper;
	}

	// releases the helper once usages has ended
	public void releaseHelper(DatabaseHelper helper) {
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}
}
