package com.ubs.kbm;


import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class KBMApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPoolSize(4).build();
		ImageLoader.getInstance().init(config);
	}
}
