package com.ubs.kbm.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.ubs.kbm.domain.NewsDataPost;
import com.ubs.kbm.utils.DatabaseManager;

public class NewsDao {
	private DatabaseHelper databaseHelper;
	private Dao<NewsDataPost, Integer> dao;

	public NewsDao(Context context) {
		DatabaseManager dbManager = new DatabaseManager();
		databaseHelper = dbManager.getHelper(context);
		try {
			dao = databaseHelper.getNewsPostsDao();
		} catch (SQLException e) {
			Log.e(NewsDao.class.getName(), "Error while getting dao");
			e.printStackTrace();
		}
	}

	public int addNews(NewsDataPost news) {
		try {
			return dao.create(news);
		} catch (SQLException e) {
			Log.e(NewsDao.class.getName(), "Error while adding news");
			e.printStackTrace();
		}
		return 0;
	}

	public int update(NewsDataPost news) {
		try {
			return dao.update(news);
		} catch (SQLException e) {
			Log.e(NewsDao.class.getName(), "Error while updating news");
			e.printStackTrace();
		}
		return 0;
	}

	public int delete(NewsDataPost news) {
		try {
			return dao.delete(news);
		} catch (SQLException e) {
			Log.e(NewsDao.class.getName(), "Error while deleting news");
			e.printStackTrace();
		}
		return 0;
	}

	public List<NewsDataPost> getAll() {
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			Log.e(NewsDao.class.getName(), "Error while getting all news");
			e.printStackTrace();
		}
		return null;
	}

	public void clearTable() {
		try {
			TableUtils.clearTable(databaseHelper.getConnectionSource(),
					NewsDataPost.class);
		} catch (SQLException e) {
			e.printStackTrace();
			Log.e(NewsDao.class.getName(), "Error while clearing table news"
					+ e);
		}
	}

	public NewsDataPost getLastElement() {
		QueryBuilder<NewsDataPost, Integer> qBuilder = dao.queryBuilder();
		qBuilder.orderBy("id", true);
		qBuilder.limit(Long.valueOf(1));
		try {
			return dao.queryForFirst(qBuilder.prepare());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
