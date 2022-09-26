package com.prj.edu.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.edu.dao.MsgDAO;
import com.prj.edu.dto.MsgDTO;

@Service
public class MsgService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired MsgDAO dao;

	public HashMap<String, Object> msgsendlist(HashMap<String, String> params) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		int cnt = Integer.parseInt(params.get("cnt"));
		int page = Integer.parseInt(params.get("page"));
		String loginid = params.get("id");
		logger.info("보여줄 페이지 : " + page);
		
		int allCnt = dao.sendallCount(loginid);
		logger.info("allCnt:" + allCnt);
		
		int pages = allCnt%cnt > 0 ? (allCnt/cnt)+1 : (allCnt/cnt);
		logger.info("pages : " + pages);
		
		//currPage = pages
		//currPage가 pages보다 크면 currPage를 pages로 맞춰준다?
		if(page > pages) {
			page = pages;
		}
		map.put("pages", pages);		//만들 수 있는 최대 페이지 수
		map.put("currPage", page); //현재 페이지

		int offset = (page-1) * cnt;
		
		logger.info("offset : " + offset);
		ArrayList<MsgDTO> msgsendlist = dao.msgsendlist(cnt, offset, loginid);
		map.put("msgsendlist", msgsendlist);
		return map;
		
		}

	
	public int senddelete(ArrayList<String> delList) {
	      int cnt = 0;
	      for (String msg_idx : delList) {
	         cnt += dao.senddelete(msg_idx);
	      }
	      logger.info("삭제 요청 서비스");
	      logger.info("cnt:"+cnt);
	      return cnt;
		}
	
	
	public int receivedelete(ArrayList<String> delList) {
        int cnt = 0;
        for (String msg_idx : delList) {
           cnt += dao.receivedelete(msg_idx);
           dao.msgopen(msg_idx);
        }
        logger.info("삭제 요청 서비스");
        return cnt;
       }

	
	public MsgDTO msgdetail(String msg_idx) {
		MsgDTO dto = null;
		logger.info(msg_idx+" 번 쪽지 상세보기 요청");
		dto = dao.msgdetail(msg_idx);
		return dto;
	}

	
	public MsgDTO sendForm(String msg_idx) {
		MsgDTO dto = null;
		logger.info("쪽지 보내기 전 서비스");
		dto = dao.sendForm(msg_idx);
		return dto;
	}

	public boolean send(HashMap<String, String> params) {
		logger.info("쪽지 보내기 서비스");
		boolean success = false;
		if(dao.send(params)>0) {
			success = true;
		}
		return success;
		}

	
	public void open(String msg_idx) {
		dao.open(msg_idx);
		}

	
	public HashMap<String, Object> msgreceivelist(HashMap<String, String> params) {
	      HashMap<String, Object> map = new HashMap<String, Object>();
	      int cnt = Integer.parseInt(params.get("cnt"));
	      int page = Integer.parseInt(params.get("page"));
	      logger.info("보여줄 페이지 : " + page);
	      String loginid = params.get("id");
	      int reallCnt = dao.receiveallCount(loginid);
	      int msgcnt = dao.msgcnt(loginid);
	      logger.info("안 읽은 메세지 "+msgcnt+"개");
	      
	      logger.info("allCnt:" + reallCnt);
	      int pages = reallCnt%cnt > 0 ? (reallCnt/cnt)+1 : (reallCnt/cnt);
	      logger.info("pages : " + pages);
	      //currPage = pages
	      //currPage가 pages보다 크면 currPage를 pages로 맞춰준다?
	      if(page > pages) {
	         page = pages;
	      }
	      
	      map.put("pages", pages);      //만들 수 있는 최대 페이지 수
	      map.put("currPage", page);//현재 페이지
	      int offset = (page-1) * cnt;
	      if(offset == -5) {
	         offset = 5;
	      }
	      logger.info("offset : " + offset);
	      ArrayList<MsgDTO> msgreceivelist = dao.msgreceivelist(cnt, offset, loginid);
	      map.put("msgreceivelist", msgreceivelist);
	      
	      return map;
	      }


	   public int msgcnt(String loginId) {
	      logger.info(loginId+"님의 메세지 수");
	      return dao.msgcnt(loginId);
	   }

	
	public HashMap<String, Object> opentest(HashMap<String, Object> params) {
		logger.info("열람여부 확인 서비스");
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<MsgDTO> opentest = dao.opentest(params);
		map.put("opentest", opentest);
		return map;
	}

	
}