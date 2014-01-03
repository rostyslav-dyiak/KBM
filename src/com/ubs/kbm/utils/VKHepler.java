package com.ubs.kbm.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.perm.kate.api.Attachment;
import com.perm.kate.api.WallMessage;
import com.ubs.kbm.domain.NewsDataPost;

public class VKHepler {
	public static final int REQUEST_LOGIN = 1;
	public static final long GROUP_ID = -24419507;
	public static final String VK_APPLICATION_ID = "3992283";

	public static List<NewsDataPost> parseWallMessageToNews(
			List<WallMessage> list) {
		if ((list == null) || (list.size() == 0)) {
			return null;
		}
		List<NewsDataPost> listOfData = new ArrayList<NewsDataPost>();
		for (WallMessage wallMassage : list) {
			if (wallMassage.copy_history != null) {
				wallMassage = wallMassage.copy_history.get(0);
			}
			NewsDataPost data = new NewsDataPost();
			data.text = wallMassage.text;
			ArrayList<String> photoUrls = new ArrayList<String>();
			for (Attachment attachment : wallMassage.attachments) {
				if (attachment.type.equalsIgnoreCase("link")) {
					System.out.println();
				}
				if (attachment.type.equalsIgnoreCase("video")) {
					data.video.videoTitle = attachment.video.title;
					if (!attachment.video.image_big.equals("")) {
						data.video.videoImage = attachment.video.image_big;
					} else if (!attachment.video.image.equals("")) {
						data.video.videoImage = attachment.video.image;
					}
					if (attachment.video != null) {
						data.video.videoUrl = attachment.video.getVideoUrl();
					}
				}
				if (attachment.type.equalsIgnoreCase("audio")) {
					data.audio.url = attachment.audio.url + ".mp3";
					data.audio.title = attachment.audio.title;
					data.audio.artist = attachment.audio.artist;
					data.audio.duration = attachment.audio.duration;
				}
				if (attachment.type.equalsIgnoreCase("photo")) {
					if (!attachment.photo.src_xxbig.equals("")) {
						photoUrls.add(attachment.photo.src_xxbig);
					} else if (!attachment.photo.src_xbig.equals("")) {
						photoUrls.add(attachment.photo.src_xbig);
					} else {
						photoUrls.add(attachment.photo.src_big);
					}
				}
			}
			data.imageUrls = photoUrls;
			data.likesCount = String.valueOf(wallMassage.like_count);
			data.commentsCount = String.valueOf(wallMassage.comment_count);
			data.repostsCount = String.valueOf(wallMassage.reposts_count);
			data.publishDate = new Date(wallMassage.date);
			listOfData.add(data);

		}
		return listOfData;
	}
}
