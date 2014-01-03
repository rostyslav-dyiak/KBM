package com.ubs.kbm.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ubs.kbm.domain.NewsDataPost;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private final static String DATABASE_NAME = "vkGroupTemplate.db";
	private static final int DATABASE_VERSION =6;
	private Dao<NewsDataPost, Integer> dao;
	private RuntimeExceptionDao<NewsDataPost, Integer> commentsDao;
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, NewsDataPost.class);
		} catch (SQLException e) {
			Log.d(DatabaseHelper.class.getName(), "Create table exception");
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVesion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, NewsDataPost.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			Log.d(DatabaseHelper.class.getName(), "Update table exception");
			e.printStackTrace();
		}
	}

	public Dao<NewsDataPost, Integer> getNewsPostsDao() throws SQLException {
		if (dao == null) {
			dao = getDao(NewsDataPost.class);
		}
		return dao;
	}

	public RuntimeExceptionDao<NewsDataPost, Integer> getCommentsNewsPostsDao() {
		if (commentsDao == null) {
			commentsDao = getRuntimeExceptionDao(NewsDataPost.class);
		}
		return commentsDao;
	}


	@Override
	public void close() {
		super.close();
		commentsDao = null;
	}
}
