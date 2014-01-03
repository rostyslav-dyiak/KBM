package com.ubs.kbm.domain;

import java.util.ArrayList;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "news")
public class NewsDataPost {
	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField
	public String text;
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	public ArrayList<String> imageUrls;
	@DatabaseField
	public Date publishDate;
	@DatabaseField
	public String likesCount;
	@DatabaseField
	public String commentsCount;
	@DatabaseField
	public String repostsCount;
	@DatabaseField(foreign = true)
	public VkVideo video;
	@DatabaseField(foreign = true)
	public VkAudio audio;

	public NewsDataPost() {
		video = new VkVideo();
		audio = new VkAudio();
	}

	public NewsDataPost(String text, ArrayList<String> imageUrl,
			String soundUrl, Date publishDate) {
		this.text = text;
		this.imageUrls = imageUrl;
		this.publishDate = publishDate;
	}

	@Override
	public String toString() {
		return "NewsDataPost [id=" + id + ", text=" + text + ", imageUrls="
				+ imageUrls + ", publishDate=" + publishDate + ", likes="
				+ likesCount + ", video=" + video + ", audio=" + audio + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((audio == null) ? 0 : audio.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((video == null) ? 0 : video.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsDataPost other = (NewsDataPost) obj;
		// TODO: KASTUL
		if ((imageUrls != null) && (other.imageUrls != null))
			if ((imageUrls.size() != 0) && (other.imageUrls.size() != 0))
				if (imageUrls.get(0).equals(other.imageUrls.get(0))) {
					return true;
				}
		if (audio == null) {
			if (other.audio != null)
				return false;
		} else if (!audio.equals(other.audio))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (video == null) {
			if (other.video != null)
				return false;
		} else if (!video.equals(other.video))
			return false;
		return true;
	}

}
