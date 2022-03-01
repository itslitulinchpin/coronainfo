package com.example.Entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Youtuber {
	public String id;
	public String image;
	public String tag;
	public String kor_name;
	
	public Youtuber() {
		
	}
	
	public Youtuber(String id, String image, String tag, String kor_name) {
		this.id = id;
		this.image = image;
		this.tag = tag;
		this.kor_name = kor_name;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getKor_name() {
		return kor_name;
	}

	public void setKor_name(String kor_name) {
		this.kor_name = kor_name;
	}

	
	
	
}