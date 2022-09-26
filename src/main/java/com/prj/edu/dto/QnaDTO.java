package com.prj.edu.dto;

import java.sql.Date;

public class QnaDTO {
	
	private int qna_idx;
	private String qna_title;
	private String qna_content;
	private Date qna_date;
	private String mb_id;
	private boolean qna_answer_chk;
	private String qna_answer_id;
	private Date qna_answer_date;
	private String qna_answer;
	
	public String getQna_answer_id() {
		return qna_answer_id;
	}
	public void setQna_answer_id(String qna_answer_id) {
		this.qna_answer_id = qna_answer_id;
	}
	public Date getQna_answer_date() {
		return qna_answer_date;
	}
	public void setQna_answer_date(Date qna_answer_date) {
		this.qna_answer_date = qna_answer_date;
	}
	public String getQna_answer() {
		return qna_answer;
	}
	public void setQna_answer(String qna_answer) {
		this.qna_answer = qna_answer;
	}
	public int getQna_idx() {
		return qna_idx;
	}
	public void setQna_idx(int qna_idx) {
		this.qna_idx = qna_idx;
	}
	public String getQna_title() {
		return qna_title;
	}
	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	public String getQna_content() {
		return qna_content;
	}
	public void setQna_content(String qna_content) {
		this.qna_content = qna_content;
	}
	public Date getQna_date() {
		return qna_date;
	}
	public void setQna_date(Date qna_date) {
		this.qna_date = qna_date;
	}
	public String getMb_id() {
		return mb_id;
	}
	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}
	public boolean isQna_answer_chk() {
		return qna_answer_chk;
	}
	public void setQna_answer_chk(boolean qna_answer_chk) {
		this.qna_answer_chk = qna_answer_chk;
	}
	
	
	
}
