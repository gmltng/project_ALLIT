package com.prj.edu.dto;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CstDTO {

		private int cons_idx;
		private Date cons_date;
		private String edu_name;
		private String recruit_title;
		private boolean cons_open;
		private boolean cons_delete_mb;
		private boolean cons_delete_edu;
		private String mb_id;
		private String mb_name;
		private String mb_email;
		private Date mb_birth;
		private String mb_tel;
		private String mb_major;
		private String mb_edu;
		private String cons_content;
		private int recruit_idx;
		private String recruit_field;
		public int getCons_idx() {
			return cons_idx;
		}
		public void setCons_idx(int cons_idx) {
			this.cons_idx = cons_idx;
		}
		public Date getCons_date() {
			return cons_date;
		}
		public void setCons_date(Date cons_date) {
			this.cons_date = cons_date;
		}
		public String getEdu_name() {
			return edu_name;
		}
		public void setEdu_name(String edu_name) {
			this.edu_name = edu_name;
		}
		public String getRecruit_title() {
			return recruit_title;
		}
		public void setRecruit_title(String recruit_title) {
			this.recruit_title = recruit_title;
		}
		public boolean isCons_open() {
			return cons_open;
		}
		public void setCons_open(boolean cons_open) {
			this.cons_open = cons_open;
		}
		public boolean isCons_delete_mb() {
			return cons_delete_mb;
		}
		public void setCons_delete_mb(boolean cons_delete_mb) {
			this.cons_delete_mb = cons_delete_mb;
		}
		public boolean isCons_delete_edu() {
			return cons_delete_edu;
		}
		public void setCons_delete_edu(boolean cons_delete_edu) {
			this.cons_delete_edu = cons_delete_edu;
		}
		public String getMb_id() {
			return mb_id;
		}
		public void setMb_id(String mb_id) {
			this.mb_id = mb_id;
		}
		public String getMb_name() {
			return mb_name;
		}
		public void setMb_name(String mb_name) {
			this.mb_name = mb_name;
		}
		public String getMb_email() {
			return mb_email;
		}
		public void setMb_email(String mb_email) {
			this.mb_email = mb_email;
		}
		public Date getMb_birth() {
			return mb_birth;
		}
		public void setMb_birth(Date mb_birth) {
			this.mb_birth = mb_birth;
		}
		public String getMb_tel() {
			return mb_tel;
		}
		public void setMb_tel(String mb_tel) {
			this.mb_tel = mb_tel;
		}
		public String getMb_major() {
			return mb_major;
		}
		public void setMb_major(String mb_major) {
			this.mb_major = mb_major;
		}
		public String getMb_edu() {
			return mb_edu;
		}
		public void setMb_edu(String mb_edu) {
			this.mb_edu = mb_edu;
		}
		public String getCons_content() {
			return cons_content;
		}
		public void setCons_content(String cons_content) {
			this.cons_content = cons_content;
		}
		public int getRecruit_idx() {
			return recruit_idx;
		}
		public void setRecruit_idx(int recruit_idx) {
			this.recruit_idx = recruit_idx;
		}
		public String getRecruit_field() {
			return recruit_field;
		}
		public void setRecruit_field(String recruit_field) {
			this.recruit_field = recruit_field;
		}
		
		

	
	
	
}
