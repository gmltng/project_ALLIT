package com.prj.edu.dto;

public class EduDTO {
	private int edu_idx;
	private String rep_name;
	private String edu_name;
	private String mb_id;
	private String photo_name;

   public String getPhoto_name() {
      return photo_name;
   }
   public void setPhoto_name(String photo_name) {
      this.photo_name = photo_name;
   }
	
	public int getEdu_idx() {
		return edu_idx;
	}
	public void setEdu_idx(int edu_idx) {
		this.edu_idx = edu_idx;
	}
	public String getRep_name() {
		return rep_name;
	}
	public void setRep_name(String rep_name) {
		this.rep_name = rep_name;
	}
	public String getEdu_name() {
		return edu_name;
	}
	public void setEdu_name(String edu_name) {
		this.edu_name = edu_name;
	}
	public String getMb_id() {
		return mb_id;
	}
	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}
	
	
	
	

}
