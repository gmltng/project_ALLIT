package com.prj.edu.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.prj.edu.dto.QnaDTO;

public interface QnaDAO {


	int allCount();

	ArrayList<QnaDTO> list(int cnt, int offset);

	int write(HashMap<String, String> params);

	QnaDTO detail(String qna_idx);

	int answer(HashMap<String, Object> params);

	QnaDTO dbdetail(String qna_idx);

	int msCount();
	
	ArrayList<QnaDTO> mslist(int cnt, int offset);

	ArrayList<QnaDTO> usqnalist(int cnt, int offset, String name);

	String findCateId(String id);

}
