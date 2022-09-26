package com.prj.edu.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.prj.edu.dto.MsgDTO;

public interface MsgDAO {

	int sendallCount(String loginid);
	
	int receiveallCount(String loginid);

	ArrayList<MsgDTO> msgsendlist(int cnt, int offset, String loginid);

	int senddelete(String msg_idx);
	
	int receivedelete(String msg_idx);

	MsgDTO msgdetail(String msg_idx);

	MsgDTO sendForm(String msg_idx);

	int send(HashMap<String, String> params);

	void open(String msg_idx);

	ArrayList<MsgDTO> msgreceivelist(int cnt, int offset, String loginid);

	ArrayList<MsgDTO> opentest(HashMap<String, Object> params);

	int msgcnt(String loginid);

	void sendlist(String loginid);
	
	void msgopen(String msg_idx);
	
}
