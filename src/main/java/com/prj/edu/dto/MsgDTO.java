package com.prj.edu.dto;

import java.sql.Date;

public class MsgDTO {

	private int msg_idx;
	private boolean msg_open;
	private String msg_content;
	private Date msg_send_date;
	private Date msg_receive_date;
	private boolean msg_delete_receiver;
	private boolean msg_delete_sender;
	private String sender_id;
	private String receiver_id;
	

	public int getMsg_idx() {
		return msg_idx;
	}
	public void setMsg_idx(int msg_idx) {
		this.msg_idx = msg_idx;
	}
	public boolean isMsg_open() {
		return msg_open;
	}
	public void setMsg_open(boolean msg_open) {
		this.msg_open = msg_open;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public Date getMsg_send_date() {
		return msg_send_date;
	}
	public void setMsg_send_date(Date msg_send_date) {
		this.msg_send_date = msg_send_date;
	}
	public Date getMsg_receive_date() {
		return msg_receive_date;
	}
	public void setMsg_receive_date(Date msg_receive_date) {
		this.msg_receive_date = msg_receive_date;
	}
	public boolean isMsg_delete_receiver() {
		return msg_delete_receiver;
	}
	public void setMsg_delete_receiver(boolean msg_delete_receiver) {
		this.msg_delete_receiver = msg_delete_receiver;
	}
	public boolean isMsg_delete_sender() {
		return msg_delete_sender;
	}
	public void setMsg_delete_sender(boolean msg_delete_sender) {
		this.msg_delete_sender = msg_delete_sender;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	
	
	
	
	
	
}
