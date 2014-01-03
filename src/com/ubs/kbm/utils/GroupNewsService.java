package com.ubs.kbm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.perm.kate.api.Api;
import com.perm.kate.api.WallMessage;
import com.ubs.kbm.R;
import com.ubs.kbm.activity.MainActivity;
import com.ubs.kbm.dao.NewsDao;
import com.ubs.kbm.domain.NewsDataPost;

public class GroupNewsService extends Service {
	private Api api;
	private List<NewsDataPost> list;
	private NewsDao dao;
	private String LOG_TAG = "MYservice";
	private ExecutorService executorService;
	private NotificationManager nm;
	private NewsDataPost firstElementFromDowload;

	@Override
	public void onCreate() {
		super.onCreate();
		executorService = Executors.newFixedThreadPool(3);
		dao = new NewsDao(this);
		Log.d(LOG_TAG, "Service start");
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, final int startId) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				Log.d(LOG_TAG, "Service execute");
				api = new Api(intent.getStringExtra("accessToken"),
						VKHepler.VK_APPLICATION_ID);
				ArrayList<WallMessage> wallList = null;
				try {
					wallList = api.getWallMessages(VKHepler.GROUP_ID, 70, 0,
							"all");
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d(LOG_TAG, "JSONException " + e);
				} catch (Exception e) {
					e.printStackTrace();
					Log.d(LOG_TAG, "Exception " + e);
				}
				list = VKHepler.parseWallMessageToNews(wallList);
				if (list != null) {
					NewsDataPost firstElementsFromDB = dao.getLastElement();
					firstElementFromDowload = list.get(0);
					Log.d(LOG_TAG, "Last element from DB" + firstElementsFromDB);
					Log.d(LOG_TAG, "Last element from Dowload"
							+ firstElementFromDowload);
					Log.d(LOG_TAG, "LIST OF all items DB" + dao.getAll());
					if (!firstElementFromDowload.equals(firstElementsFromDB)) {
						Log.d(LOG_TAG, "clearing table");
						dao.clearTable();
						for (NewsDataPost news : list) {
							dao.addNews(news);
						}
						sendUpdateNotification();
					}
				}
			}
		});

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(LOG_TAG, "Service onDestroy");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void sendUpdateNotification() {
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		Bitmap b = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.logo);
		Bitmap resizedbitmap = Bitmap.createScaledBitmap(b, 50, 50, true);
		Notification notif = new NotificationCompat.Builder(this)
				.setContentIntent(pIntent).setContentTitle("Брутальна новина!")
				.setContentText(firstElementFromDowload.text)
				.setSmallIcon(R.drawable.logo).setAutoCancel(true)
				.setTicker("Брутальна новина!")
				.setWhen(System.currentTimeMillis())
				.setLargeIcon(resizedbitmap)
				.setDefaults(Notification.DEFAULT_ALL).build();
		nm.notify(1, notif);
	}

}
