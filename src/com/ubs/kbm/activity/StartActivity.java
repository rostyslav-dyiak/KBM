package com.ubs.kbm.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ubs.kbm.R;
import com.ubs.kbm.utils.GroupNewsService;
import com.ubs.kbm.utils.vk.Account;

public class StartActivity extends RoboActivity {
	Handler h = null;
	@InjectView(R.id.progress_text)
	TextView progressText;
	@InjectView(R.id.progress_image)
	ImageView progressImage;
	@InjectView(R.id.exit_button1)
	ImageButton exitButton;
	@InjectView(R.id.like_icon)
	ImageButton onButton;
	@InjectView(R.id.off_button)
	ImageButton offButton;
	private Account account = new Account();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		h = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				String stringProgress = progressText.getText().toString();
				Integer percentage = Integer.parseInt(stringProgress.substring(
						0, stringProgress.length() - 1));
				if (percentage < 100) {
					percentage++;
					progressText.setText(percentage.toString() + "%");
					h.sendEmptyMessageDelayed(0, 10);
				} else {
					fadeOutView(progressText);
					fadeOutView(progressImage);
					showView(onButton);
					showView(exitButton);
					showView(offButton);

				}
			}

		};
		h.sendEmptyMessageAtTime(0, 100);

	}

	public void exitButtonClick(View view) {
		finish();
	}

	public void onButtonClick(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("mode", "on");
		account.restore(this);
		String accessToken = account.getAccessToken();
		if ((accessToken == null) || accessToken.equals("")) {
			startActivity(new Intent(this, LoginActivity.class));
		}
		// TODO: CHECK THIS

		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 60);
		Intent intent1 = new Intent(this, GroupNewsService.class);
		PendingIntent pintent = PendingIntent.getService(this, 0, intent1, 0);
		AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				25 * 1000, pintent);*/
		startService(new Intent(getBaseContext(), GroupNewsService.class));
		startActivity(intent);
	}

	public void offButtonClick(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("mode", "off");
		startActivity(intent);
	}

	private void showView(final View v) {
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		v.startAnimation(fadeIn);
		v.setVisibility(View.VISIBLE);

	}

	private void fadeOutView(final View v) {
		Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		fadeOut.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {

				v.setVisibility(View.GONE);
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationStart(Animation animation) {
			}
		});
		v.startAnimation(fadeOut);

	}
}
