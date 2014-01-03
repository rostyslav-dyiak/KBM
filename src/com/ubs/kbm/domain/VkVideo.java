package com.ubs.kbm.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "video")
public class VkVideo {
	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField
	public String videoUrl;
	@DatabaseField
	public String videoImage;
	@DatabaseField
	public String videoTitle;

	public VkVideo() {
	}

	public VkVideo(String videoUrl, String videoImage, String videoTitle) {
		super();
		this.videoUrl = videoUrl;
		this.videoImage = videoImage;
		this.videoTitle = videoTitle;
	}

	@Override
	public String toString() {
		return "VkVideo [id=" + id + ", videoUrl=" + videoUrl + ", videoImage="
				+ videoImage + ", videoTitle=" + videoTitle + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((videoTitle == null) ? 0 : videoTitle.hashCode());
		result = prime * result
				+ ((videoUrl == null) ? 0 : videoUrl.hashCode());
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
		VkVideo other = (VkVideo) obj;
		if (videoTitle == null) {
			if (other.videoTitle != null)
				return false;
		} else if (!videoTitle.equals(other.videoTitle))
			return false;
		if (videoUrl == null) {
			if (other.videoUrl != null)
				return false;
		} else if (!videoUrl.equals(other.videoUrl))
			return false;
		return true;
	}
}
