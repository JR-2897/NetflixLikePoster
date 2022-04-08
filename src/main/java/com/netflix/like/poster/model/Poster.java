package com.netflix.like.poster.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("poster")
public class Poster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private List<String> listUrlImage;
	private Integer idMedia;

	public Poster(Integer id, List<String> listUrlImage, Integer idMedia) {
		super();
		this.id = id;
		this.listUrlImage = listUrlImage;
		this.idMedia = idMedia;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getListUrlImage() {
		return listUrlImage;
	}

	public void setListUrlImage(List<String> listUrlImage) {
		this.listUrlImage = listUrlImage;
	}

	public Integer getIdMedia() {
		return idMedia;
	}

	public void setIdMedia(Integer idMedia) {
		this.idMedia = idMedia;
	}

}
