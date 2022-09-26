package com.prj.edu.dto;

import java.sql.Date;

public class BoardDTO {
	
	private int board_idx;
	   private String board_title;
	   private String board_content;
	   private Date board_date;
	   private int board_hits;
	   private String board_category;
	   private boolean board_blind;
	   private String mb_id;
	   private String report_reason;
	   private Date report_date;
	   private String report_state;
	   private String report_idx; //김동훈
	   
	   
	   public int getBoard_idx() {
	      return board_idx;
	   }
	   public void setBoard_idx(int board_idx) {
	      this.board_idx = board_idx;
	   }
	   public String getBoard_title() {
	      return board_title;
	   }
	   public void setBoard_title(String board_title) {
	      this.board_title = board_title;
	   }
	   public String getBoard_content() {
	      return board_content;
	   }
	   public void setBoard_content(String board_content) {
	      this.board_content = board_content;
	   }
	   public Date getBoard_date() {
	      return board_date;
	   }
	   public void setBoard_date(Date board_date) {
	      this.board_date = board_date;
	   }
	   public int getBoard_hits() {
	      return board_hits;
	   }
	   public void setBoard_hits(int board_hits) {
	      this.board_hits = board_hits;
	   }
	   public String getBoard_category() {
	      return board_category;
	   }
	   public void setBoard_category(String board_category) {
	      this.board_category = board_category;
	   }
	   public boolean isBoard_blind() {
	      return board_blind;
	   }
	   public void setBoard_blind(boolean board_blind) {
	      this.board_blind = board_blind;
	   }
	   public String getMb_id() {
	      return mb_id;
	   }
	   public void setMb_id(String mb_id) {
	      this.mb_id = mb_id;
	   }
	   public String getReport_reason() {
	      return report_reason;
	   }
	   public void setReport_reason(String report_reason) {
	      this.report_reason = report_reason;
	   }
	   public Date getReport_date() {
	      return report_date;
	   }
	   public void setReport_date(Date report_date) {
	      this.report_date = report_date;
	   }
	   public String getReport_state() {
	      return report_state;
	   }
	   public void setReport_state(String report_state) {
	      this.report_state = report_state;
	   }
	   public String getReport_idx() {
	      return report_idx;
	   }
	   public void setReport_idx(String report_idx) {
	      this.report_idx = report_idx;
	   }
   
	private int recruit_idx;
	private String recruit_title;
	private String recruit_content;
	private Date recruit_date;
	private int recruit_hits;
	private Date recruit_period_sta;
	private Date recruit_period_end;
	private Date recruit_start;
	private Date recruit_end;
	private boolean recruit_close;
	private String recruit_link;
	private String recruit_field;
	private String edu_name;
	private String mb_basic_addr;
	private String mb_tel;
	private String dday;
	private int cnt;

	public Date getRecruit_start() {
		return recruit_start;
	}
	public void setRecruit_start(Date recruit_start) {
		this.recruit_start = recruit_start;
	}
	public Date getRecruit_end() {
		return recruit_end;
	}
	public void setRecruit_end(Date recruit_end) {
		this.recruit_end = recruit_end;
	}
	public boolean isRecruit_close() {
		return recruit_close;
	}
	public void setRecruit_close(boolean recruit_close) {
		this.recruit_close = recruit_close;
	}
	public String getRecruit_link() {
		return recruit_link;
	}
	public void setRecruit_link(String recruit_link) {
		this.recruit_link = recruit_link;
	}
	public String getRecruit_field() {
		return recruit_field;
	}
	public void setRecruit_field(String recruit_field) {
		this.recruit_field = recruit_field;
	}
	public String getEdu_name() {
		return edu_name;
	}
	public void setEdu_name(String edu_name) {
		this.edu_name = edu_name;
	}
	public String getMb_basic_addr() {
		return mb_basic_addr;
	}
	public void setMb_basic_addr(String mb_basic_addr) {
		this.mb_basic_addr = mb_basic_addr;
	}
	public String getMb_tel() {
		return mb_tel;
	}
	public void setMb_tel(String mb_tel) {
		this.mb_tel = mb_tel;
	}
	public String getDday() {
		return dday;
	}
	public void setDday(String dday) {
		this.dday = dday;
	}
	public void setRecruit_period_end(Date recruit_period_end) {
		this.recruit_period_end = recruit_period_end;
	}
	public int getRecruit_idx() {
		return recruit_idx;
	}
	public void setRecruit_idx(int recruit_idx) {
		this.recruit_idx = recruit_idx;
	}
	public String getRecruit_title() {
		return recruit_title;
	}
	public void setRecruit_title(String recruit_title) {
		this.recruit_title = recruit_title;
	}
	public String getRecruit_content() {
		return recruit_content;
	}
	public void setRecruit_content(String recruit_content) {
		this.recruit_content = recruit_content;
	}
	public Date getRecruit_date() {
		return recruit_date;
	}
	public void setRecruit_date(Date recruit_date) {
		this.recruit_date = recruit_date;
	}
	public int getRecruit_hits() {
		return recruit_hits;
	}
	public void setRecruit_hits(int recruit_hits) {
		this.recruit_hits = recruit_hits;
	}
	public Date getRecruit_period_sta() {
		return recruit_period_sta;
	}
	public void setRecruit_period_sta(Date recruit_period_sta) {
		this.recruit_period_sta = recruit_period_sta;
	}
	public Date getRecruit_period_end() {
		return recruit_period_end;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}
