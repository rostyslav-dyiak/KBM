package com.ubs.kbm.adapters;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.ubs.kbm.R;
import com.ubs.kbm.domain.NewsDataPost;

public class NewsListAdapter extends ArrayAdapter<NewsDataPost> {
	private List<NewsDataPost> values;
	private Context context;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private LayoutInflater inflater;

	static class ViewHolder {
		public ImageView imageView;
		public TextView fullText;
		public TextView dateOfPublication;
		public TextView likes;
		public TextView commentsCount;
		public TextView repostCount;
		public TableLayout grid;
	}

	public NewsListAdapter(Context context, List<NewsDataPost> values) {
		super(context, R.layout.news_item, values);
		this.values = values;
		this.context = context;
		inflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View rootView = convertView;
		if (rootView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			rootView = inflater.inflate(R.layout.news_item, parent, false);
			holder = new ViewHolder();
			holder.fullText = (TextView) rootView
					.findViewById(R.id.news_item_header);
			holder.dateOfPublication = (TextView) rootView
					.findViewById(R.id.date_of_publications1);
			holder.likes = (TextView) rootView.findViewById(R.id.likes_count);
			holder.commentsCount = (TextView) rootView
					.findViewById(R.id.commets_count);
			holder.repostCount = (TextView) rootView
					.findViewById(R.id.reposts_count);
			holder.grid = (TableLayout) rootView.findViewById(R.id.images_grid);
			rootView.setTag(holder);

		} else {

			holder = (ViewHolder) rootView.getTag();
			deleteAllRowsInTableLayout(holder);
		}
		initViewHolder(holder, position);

		return rootView;
	}

	/**
	 * 
	 * @param holder
	 */
	private void deleteAllRowsInTableLayout(ViewHolder holder) {
		for (int i = 0; i < holder.grid.getChildCount(); i++) {
			View child = holder.grid.getChildAt(i);
			if (child instanceof TableRow)
				((ViewGroup) child).removeAllViews();
		}
	}

	/**
	 * Fill viewHolder fields with values.
	 * 
	 * @param holder
	 *            to initialize
	 * @param position
	 *            of newsItem
	 */
	private void initViewHolder(ViewHolder holder, int position) {
		NewsDataPost newsItem = values.get(position);
		if (!newsItem.text.equals("") || newsItem.text != null) {
			holder.fullText.setText(newsItem.text);
		} else {
			holder.fullText.setVisibility(View.GONE);
		}
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM HH:mm:ss",
				new Locale("uk_UA", "UA"));
		holder.dateOfPublication.setText(format.format(newsItem.publishDate));
		holder.likes.setText(newsItem.likesCount);
		holder.commentsCount.setText(newsItem.commentsCount);
		holder.repostCount.setText(newsItem.repostsCount);
		if ((newsItem.audio.url != null) && (!newsItem.audio.url.equals(""))) {
			holder.grid.setVisibility(View.VISIBLE);
			initAudioInHolder(holder, newsItem);
		}
		if (newsItem.imageUrls.size() > 0) {
			holder.grid.setVisibility(View.VISIBLE);
			initImagesInHolder(holder, newsItem);
		}
		if ((newsItem.video.videoUrl != null)
				&& (!newsItem.video.videoUrl.equals(""))) {
			holder.grid.setVisibility(View.VISIBLE);
			initVideoInHolder(holder, newsItem);
		}

	}

	/**
	 * Initialize all images in post, and add them to Table layout
	 * 
	 * @param holder
	 *            where TableRow are added
	 * @param newsItem
	 *            from which is information getting
	 */
	private void initImagesInHolder(ViewHolder holder,
			final NewsDataPost newsItem) {
		int size = newsItem.imageUrls.size();
		for (int i = 0; i < size; i = i + 2) {
			TableRow row = (TableRow) inflater.inflate(
					R.layout.images_grid_layout, null);
			row.setGravity(Gravity.CENTER_HORIZONTAL);
			if (i + 1 < size) {
				ImageView firstImage = (ImageView) row.getChildAt(0);
				ImageView secondImage = (ImageView) row.getChildAt(1);
				imageLoader.displayImage(newsItem.imageUrls.get(i), firstImage,
						options);
				imageLoader.displayImage(newsItem.imageUrls.get(i + 1),
						secondImage, options);
			} else {
				ImageView firstImage = (ImageView) row.getChildAt(0);
				TableRow.LayoutParams params = new TableRow.LayoutParams();
				params.span = 2;
				row.setLayoutParams(params);
				imageLoader.displayImage(newsItem.imageUrls.get(i), firstImage,
						options);
			}
			holder.grid.addView(row);
		}

	}

	/**
	 * Initialize audio in post, and add them to Table layout
	 * 
	 * @param holder
	 *            where TableRow are added
	 * @param newsItem
	 *            from which is information getting
	 */
	private void initAudioInHolder(ViewHolder holder,
			final NewsDataPost newsItem) {
		TableRow row = (TableRow) inflater.inflate(R.layout.audio_layout, null);
		TextView artist = (TextView) row.findViewById(R.id.audio_artist);
		TextView title = (TextView) row.findViewById(R.id.audio_title);
		TextView duration = (TextView) row.findViewById(R.id.audio_duration);
		ImageView firstImage = (ImageView) row.findViewById(R.id.audio_image);
		firstImage.setImageResource(R.drawable.play);
		firstImage.setTag(R.drawable.play);
		artist.setText(newsItem.audio.artist);
		title.setText(newsItem.audio.title);
		duration.setText(String.valueOf(newsItem.audio.duration));
		final MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(newsItem.audio.url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		firstImage.setOnClickListener(new OnClickListener() {
			int songPosition = 0;

			@Override
			public void onClick(View v) {
				ImageView image = (ImageView) v;
				int resource = (Integer) image.getTag();
				if (!mp.isPlaying()) {
					mp.prepareAsync();
					mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						public void onPrepared(MediaPlayer mp) {
							mp.seekTo(songPosition);
							mp.start();
						}
					});
				} else {
					songPosition = mp.getCurrentPosition();
					mp.pause();
				}
				if (resource == R.drawable.play) {
					image.setImageResource(R.drawable.pause);
					image.setTag(R.drawable.pause);

				} else if (resource == R.drawable.pause) {
					image.setImageResource(R.drawable.play);
					image.setTag(R.drawable.play);

				}

			}
		});
		holder.grid.addView(row);
	}

	/**
	 * Initialize video in post, and add them to Table layout
	 * 
	 * @param holder
	 *            where TableRow are added
	 * @param newsItem
	 *            from which is information getting
	 */
	private void initVideoInHolder(ViewHolder holder,
			final NewsDataPost newsItem) {

		TableRow row = (TableRow) inflater.inflate(R.layout.video_layout, null);

		ImageView firstImage = (ImageView) row.findViewById(R.id.video_image);

		TextView tv = (TextView) row.findViewById(R.id.video_title);
		tv.setText(newsItem.video.videoTitle);
		firstImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(newsItem.video.videoUrl));
				context.startActivity(intent);
			}
		});
		DisplayImageOptions videoImageOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader.displayImage(newsItem.video.videoImage, firstImage,
				videoImageOptions);
		holder.grid.addView(row);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}
