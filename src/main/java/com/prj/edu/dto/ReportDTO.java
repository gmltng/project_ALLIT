package com.prj.edu.dto;

import java.sql.Date;

public class ReportDTO {

	private int report_idx;
	private String report_reason;
	private Date report_date;
	private String report_state;
	private String report_manager;
	private String mb_id;
	private int board_idx;
	
	
	
	
	public int getReport_idx() {
		return report_idx;
	}
	public void setReport_idx(int report_idx) {
		this.report_idx = report_idx;
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
	public String getReport_manager() {
		return report_manager;
	}
	public void setReport_manager(String report_manager) {
		this.report_manager = report_manager;
	}
	public String getMb_id() {
		return mb_id;
	}
	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	
}
