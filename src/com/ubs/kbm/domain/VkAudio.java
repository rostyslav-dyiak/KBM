package com.ubs.kbm.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "audio")
public class VkAudio {
	@DatabaseField(generatedId = true)
	public int id;
	@DatabaseField
	public String artist;
	@DatabaseField
	public String title;
	@DatabaseField
	public long duration;
	@DatabaseField
	public String url;

	public VkAudio() {
	}

	public VkAudio(String artist, String title, long duration, String url) {
		this.artist = artist;
		this.title = title;
		this.duration = duration;
		this.url = url;
	}

	@Override
	public String toString() {
		return "VkAudio [id=" + id + ", artist=" + artist + ", title=" + title
				+ ", duration=" + duration + ", url=" + url + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		VkAudio other = (VkAudio) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
