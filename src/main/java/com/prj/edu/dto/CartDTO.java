package com.prj.edu.dto;

import java.util.Date;

public class CartDTO {
	
	private int cart_idx;
	private String mb_id;
	private int recruit_idx;
	private Date cart_date;
	
	private String recruit_title;
	private Date recruit_period_sta;
	private Date recruit_period_end;
	private int recruit_close;
	private String edu_name;
	
	private int category_idx;
	
	public int getCategory_idx() {
		return category_idx;
	}
	public void setCategory_idx(int category_idx) {
		this.category_idx = category_idx;
	}
	public String getEdu_name() {
		return edu_name;
	}
	public void setEdu_name(String edu_name) {
		this.edu_name = edu_name;
	}
	public Date getRecruit_period_sta() {
		return recruit_period_sta;
	}
	public void setRecruit_period_sta(Date recruit_period_sta) {
		this.recruit_period_sta = recruit_period_sta;
	}
	public int getRecruit_close() {
		return recruit_close;
	}
	public void setRecruit_close(int recruit_close) {
		this.recruit_close = recruit_close;
	}
	public String getRecruit_title() {
		return recruit_title;
	}
	public void setRecruit_title(String recruit_title) {
		this.recruit_title = recruit_title;
	}
	
	public Date getRecruit_period_end() {
		return recruit_period_end;
	}
	public void setRecruit_period_end(Date recruit_period_end) {
		this.recruit_period_end = recruit_period_end;
	}
	public int getCart_idx() {
		return cart_idx;
	}
	public void setCart_idx(int cart_idx) {
		this.cart_idx = cart_idx;
	}
	public String getMb_id() {
		return mb_id;
	}
	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}
	public int getRecruit_idx() {
		return recruit_idx;
	}
	public void setRecruit_idx(int recruit_idx) {
		this.recruit_idx = recruit_idx;
	}
	public Date getCart_date() {
		return cart_date;
	}
	public void setCart_date(Date cart_date) {
		this.cart_date = cart_date;
	}

}
