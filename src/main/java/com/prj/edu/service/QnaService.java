package com.prj.edu.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.edu.dao.QnaDAO;
import com.prj.edu.dto.QnaDTO;

@Service
public class QnaService {
   
   Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired QnaDAO dao;
   
public HashMap<String, Object> list(HashMap<String, String> params) {
      
      HashMap<String, Object> map = new HashMap<String, Object>();
      int cnt = Integer.parseInt(params.get("cnt"));
      int page = Integer.parseInt(params.get("page"));
      logger.info("보여줄 페이지 : " + page);
      int allCnt = dao.allCount();
      logger.info("allCnt:" + allCnt);
      int pages = allCnt%cnt > 0 ? (allCnt/cnt)+1 : (allCnt/cnt);
      logger.info("pages : " + pages);

      if(page > pages) {
         page = pages;
      }
      
      map.put("pages", pages);      //만들 수 있는 최대 페이지 수
      map.put("currPage", page); //현재 페이지
   
      
      int offset = (page-1) * cnt;
      logger.info("offset : " + offset);
         
      ArrayList<QnaDTO> list = dao.list(cnt, offset);
      map.put("list", list);
      
      return map;
   }

   public boolean write(HashMap<String, String> params) {

      logger.info("글쓰기 서비스");
      boolean success = false;      
      
      if(dao.write(params)>0) {
         success = true;
      }      
      return success;
   }


   public QnaDTO detail(String qna_idx) {
      QnaDTO dto = null;
      logger.info(qna_idx + "상세보기 서비스 요청");
      dto = dao.detail(qna_idx);
      logger.info("content : " + dto.getQna_content());
      return dto;
   }

   public int answer(HashMap<String, Object> params) {
      logger.info("답변 달기 서비스");
      return dao.answer(params);
   }

   public QnaDTO dbdetail(String qna_idx) {
      logger.info("상세보기2 요청");
      return dao.dbdetail(qna_idx);
   }

public String findCateId(String id) {
	return dao.findCateId(id);
}

   
}