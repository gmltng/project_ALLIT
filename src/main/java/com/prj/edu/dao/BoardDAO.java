package com.prj.edu.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.prj.edu.dto.BoardDTO;
import com.prj.edu.dto.CmtDTO;
import com.prj.edu.dto.MsgDTO;
import com.prj.edu.dto.ReportDTO;

public interface BoardDAO {

	int allCount(String board_category);

	ArrayList<BoardDTO> list(int cnt, int offset, String board_category);

	BoardDTO boarddetail(String board_idx);

	int write(HashMap<String, String> params);

	int board_update(HashMap<String, String> params);

	void board_hits(String board_idx);

	String writerId(Object mb_id);

	ArrayList<CmtDTO> cmtlist(String board_idx);

	int cmtwrite(HashMap<String, String> params);

	void cmtdel(String cmt_idx, String loginId);

	ArrayList<ReportDTO> report_reason(HashMap<String, String> params);

	int board_del(String board_idx);

	int cmt_del(String board_idx);

	int report_del(String board_idx);

	String chkId(String board_idx);

	ArrayList<MsgDTO> sm_msg_send(HashMap<String, String> params);

	String board_cateId(String board_cateloginId);

	//0630추가됨
	int reportCount();// 김동훈 추가

	ArrayList<BoardDTO> reportList(int cnt, int offset);// 김동훈 추가

	void blind(String board_idx, String blindYn); // 김동훈 추가

	String chk_blind(String board_idx); // 김동훈 추가

	String cateId(String id); // 김동훈 추가

//	void report_state(String report_idx, String report_state);// 김동훈 추가
	/*
	 * 0701 유저에서 날라옴 void report_state(String report_idx, String report_state);//
	 * 김동훈 추가
	 */
	
	//성민 보드히스토리 추가

	//성민 보드히스토리 추가
	ArrayList<BoardDTO> boardHistoryajax(int cnt, int offset, String name);

	int bht_allCount(String name);

	BoardDTO boarddetail_clean(String board_idx);
	
	
	   //성민

	//String rpt_boardchk(String report_board_idx);

}
	
