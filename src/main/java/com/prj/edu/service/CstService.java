package com.prj.edu.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.edu.dao.CstDAO;
import com.prj.edu.dto.CstDTO;

@Service
public class CstService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CstDAO dao;
	/*
	 * // 상담 신청 리스트 불러오기 서비스 및 페이징 처리 public HashMap<String, Object>
	 * list(HttpSession session, HashMap<String, String> params) { HashMap<String,
	 * Object> map = new HashMap<String, Object>();
	 * 
	 * int cnt = Integer.parseInt(params.get("cnt")); int page =
	 * Integer.parseInt(params.get("page")); logger.info("보여줄 페이지: "+page);
	 * 
	 * // 총 갯수(allCnt) / 페이지 당 보여줄 갯수(cnt) = 생성 가능한 페이지(pages) int allCnt =
	 * dao.allCount(); logger.info("allCount: "+allCnt); int pages = allCnt%cnt>0 ?
	 * (allCnt/cnt)+1 : (allCnt/cnt); int currNum = page > pages ? pages : page;
	 * logger.info("pages: "+pages);
	 * 
	 * == int currNum = page > pages ? pages : page; == map.put("currPage",
	 * currNum); if(page> pages) { page = pages; }
	 * 
	 * 
	 * map.put("pages", pages); // 만들 수 있는 최대 페이지 수 map.put("currPage", page); // 현재
	 * 페이지 map.put("currPage", currNum);
	 * 
	 * int offset = (page-1)*cnt; logger.info("offset: "+offset);
	 * 
	 * ArrayList<CstDTO> list =dao.list(cnt,offset,session.getAttribute("loginId"));
	 * map.put("list", list); return map; }
	 */
	

	// 상담 신청 리스트 불러오기 서비스 및 페이징 처리
	public HashMap<String, Object> mbList(HttpSession session, HashMap<String, String> params) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int cnt = Integer.parseInt(params.get("cnt"));
		int page = Integer.parseInt(params.get("page"));
		logger.info("보여줄 페이지: "+page);
		String mb_id = (String) session.getAttribute("loginId");
		
		
		// 총 갯수(allCnt) / 페이지 당 보여줄 갯수(cnt) = 생성 가능한 페이지(pages)
		int mbAll = dao.mbAll(mb_id);
		logger.info("mbAll: "+mbAll);
		int pages = mbAll%cnt>0 ? (mbAll/cnt)+1 : (mbAll/cnt);
		int currNum = page > pages ? pages : page; 
		logger.info("pages: "+pages);
		
		/* == int currNum = page > pages ? pages : page; 
		 * == map.put("currPage", currNum); 
		 * if(page> pages) { page = pages; }
		 */
		
		map.put("pages", pages); // 만들 수 있는 최대 페이지 수
		map.put("currPage", page); // 현재 페이지
		map.put("currPage", currNum); 
		
		int offset = (page-1)*cnt;
		logger.info("offset: "+offset);
		
		ArrayList<CstDTO> mbList =dao.mbList(cnt,offset,session.getAttribute("loginId"));
		map.put("mbList", mbList);
		return map;
	}


	

	public HashMap<String, Object> eduList(HttpSession session,HashMap<String, String> params) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int cnt = Integer.parseInt(params.get("cnt"));
		int page = Integer.parseInt(params.get("page"));
		
		logger.info("보여줄 페이지: "+page);
		String mb_id = (String) session.getAttribute("loginId");
		
		// 총 갯수(allCnt) / 페이지 당 보여줄 갯수(cnt) = 생성 가능한 페이지(pages)
		int eduAll = dao.eduAll(mb_id);
		logger.info("eduAll: "+eduAll);
		int pages = eduAll%cnt>0 ? (eduAll/cnt)+1 : (eduAll);
		int currNum = page > pages ? pages : page; 
		logger.info("pages: "+pages);
		
		/* == int currNum = page > pages ? pages : page; 
		 * == map.put("currPage", currNum); 
		 * if(page> pages) { page = pages; }
		 */
		
		map.put("pages", pages); // 만들 수 있는 최대 페이지 수
		map.put("currPage", page); // 현재 페이지
		map.put("currPage", currNum); 
		
		int offset = (page-1)*cnt;
		logger.info("offset: "+offset);
		
		ArrayList<CstDTO> eduList =dao.eduList(cnt,offset,session.getAttribute("loginId"));
		map.put("eduList", eduList);
		return map;
	}
	
	
	
	
	
	
	

	public CstDTO cstInfo(HttpSession session, String idx) {
		CstDTO dto = null;
		logger.info(" 회원정보 서비스 요청");
		String mb_id = (String) session.getAttribute("loginId");
		logger.info(mb_id+" | "+ idx );
		dto = dao.cstInfo(mb_id,idx);
		return dto;
	}

	public CstDTO cstDetail(String cons_idx, HttpSession session) {
		CstDTO dto = null;
		logger.info(session + " / " + cons_idx + " 상세보기 서비스 요청");
		dto = dao.cstDetail(cons_idx, session.getAttribute("loginId"));
		logger.info("content: " + dto.getCons_content());
		return dto;
	}

	public int cstWrite(HashMap<String, String> params, HttpSession session) {
		logger.info("글쓰기 서비스 요청");
		String mb_id = (String) session.getAttribute("loginId");
		logger.info(mb_id);
		logger.info("param : {}",params);
		params.put("mb_id", mb_id);

		int cnt = dao.cstWrite(params);

		return cnt;
	}

	/*
	 * public int cstDelete(ArrayList<String> delList) { int cnt = 0; for (String
	 * cons_idx : delList) { cnt += dao.cstDelete(cons_idx); } return cnt; }
	 */

	public String cons_open(String cons_open) {

		return dao.cons_open(cons_open);
	}

	public void open(String cons_idx) {
		dao.open(cons_idx);

	}

	public int userCategory(String loginId) {
		return dao.userCategory(loginId);
	}

	public int mbDel(ArrayList<String> delList) {
		int cnt = 0;
		for (String cons_idx : delList) {
			cnt += dao.mbDel(cons_idx);
		}
		logger.info("삭제 요청 서비스");
		logger.info("cnt:" + cnt);
		return cnt;
	}

	public int eduDel(ArrayList<String> delList) {
		int cnt = 0;
		for (String cons_idx : delList) {
			cnt += dao.eduDel(cons_idx);
		}
		logger.info("삭제 요청 서비스");
		logger.info("cnt:" + cnt);
		return cnt;
	}



	public int eduCnt(String loginId) {
		int eduCnt = 0;
		eduCnt += dao.eduCnt(loginId);
		logger.info("eduCnt:" + eduCnt);
		return eduCnt;
	}




	public int mbCnt(String loginId) {
		int mbCnt = 0;
		mbCnt += dao.mbCnt(loginId);
		logger.info("mbCnt:" + mbCnt);
		return mbCnt;
	}









	/*
	 * public String recruit_idx(String loginId) { logger.info("모집공고 번호 서비스");
	 * return dao.recruit_idx(loginId); }
	 */









	/*
	 * public int cstDelete(ArrayList<String> delList) { int cnt = 0; for (String
	 * cons_idx : delList) { cnt += dao.cstDelete(cons_idx); } return cnt; }
	 */

	// 미완성
	/*
	 * public boolean cstWrite(HashMap<String,String> params, Object object) {
	 * logger.info("상담 신청하기 서비스"); HashMap<String, Object> result=new
	 * HashMap<String, Object>(); int
	 * row=dao.cstWrite(params,object.getAttribute("loginId"));
	 * logger.info(params+" / "+object+" 서비스"); boolean cnt=false; if(row>0) {
	 * cnt=true; } result.put("success",cnt);
	 * 
	 * return result; }
	 */

}
