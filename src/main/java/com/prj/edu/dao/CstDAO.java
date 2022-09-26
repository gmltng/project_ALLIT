package com.prj.edu.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.prj.edu.dto.CstDTO;

public interface CstDAO {

	int mbAll(String mb_id);

	// ArrayList<CstDTO> list(int cnt, int offset, Object object);



	int cstWrite(HashMap<String, String> params);

	CstDTO cstDetail(String cons_idx, Object object);

	int cstDelete(String cons_idx);

	String cons_open(String cons_open);

	void open(String cons_idx);

	int userCategory(String loginId);

	int mbDel(String cons_idx);

	int eduDel(String cons_idx);

	ArrayList<CstDTO> mbList(int cnt, int offset, Object object);

	ArrayList<CstDTO> eduList(int cnt, int offset, Object object);

	String recruit_idx(String loginId);


	CstDTO cstInfo(String mb_id, String idx);

	int eduCnt(String loginId);

	int mbCnt(String loginId);

	int eduAll(String mb_id);


	//String r_idx(String r_id);

	// int cstDelete(String cons_idx);




}
