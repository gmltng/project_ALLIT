package com.prj.edu.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.prj.edu.dto.BoardDTO;

public interface RecruitDAO {

	int write(HashMap<String, String> params);

	int allCount(String recruit_category);

	ArrayList<BoardDTO> list(int cnt, int offset, String recruit_category, String mb_id);

	void upHit(String idx);

	BoardDTO recruitDetail(String idx);

	int closeYn();

	int update(HashMap<String, String> params);

	int allCount1();

	ArrayList<BoardDTO> list1(int cnt, int offset, String mb_id);

	ArrayList<BoardDTO> hotList();

	int updateDate();

	BoardDTO writeList(String id);

	String findCateId(String id);

	int delete(String idx);

	int allCount2(String id);

	ArrayList<BoardDTO> recruitHistoryList(int cnt, int offset, String id);

//	ArrayList<BoardDTO> writeList(String id);

}
