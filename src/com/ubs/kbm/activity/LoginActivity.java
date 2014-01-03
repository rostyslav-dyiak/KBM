package com.ubs.kbm.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.perm.kate.api.Auth;
import com.ubs.kbm.R;
import com.ubs.kbm.utils.VKHepler;
import com.ubs.kbm.utils.vk.Account;

public class LoginActivity extends RoboActivity {
	@InjectView(R.id.login_web_view)
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		String url = Auth.getUrl(VKHepler.VK_APPLICATION_ID, Auth.getSettings());
		webView.setWebViewClient(new VkontakteWebViewClient());
		webView.loadUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	class VkontakteWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			parseUrl(url);
		}
	}

	private void parseUrl(String url) {
		try {
			if (url == null)
				return;
			if (url.startsWith(Auth.redirect_url)) {
				if (!url.contains("error=")) {
					String[] auth = Auth.parseRedirectUrl(url);
					Account account = new Account(auth[0],
							Long.parseLong(auth[1]));
					account.save(getApplicationContext());
				}
				finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
