package com.prj.edu.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prj.edu.dao.CartDAO;
import com.prj.edu.dto.BoardDTO;
import com.prj.edu.dto.CartDTO;


@Service
public class CartService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired CartDAO dao;
	
	public HashMap<String, Object> list(HashMap<String, String> params, String id) {
	      HashMap<String, Object> map = new HashMap<String, Object>();
	      
	      int cnt = Integer.parseInt(params.get("cnt"));
	      int page = Integer.parseInt(params.get("page"));
	      logger.info("보여줄 페이지 : " + page);
	      
	      int allCnt = dao.allCount(id);
	      
	      logger.info("allCnt:" + allCnt);
	      int pages = allCnt%cnt > 0 ? (allCnt/cnt)+1 : (allCnt/cnt);
	      //총갯수(allCnt) / 페이지당 보여줄 갯수(cnt) = 생성 가능한 페이지(pages)
	      //464            5                        = 93pages (마지막페이지 cnt=4)
	      //464/5=92.8나옴.   
	      
	      logger.info("pages : " + pages);
	      //currPage = pages
	      //currPage가 pages보다 크면 currPage를 pages로 맞춰준다?
	      if(page > pages) {
	         page = pages;
	      }
	      
	      map.put("pages", pages);      //만들 수 있는 최대 페이지 수
	      map.put("currPage", page); //현재 페이지
	   
	      
	      int offset = (page-1) * cnt;
	      logger.info("offset : " + offset);
	      // page  :  (cnt)     =    offset
	      // 1         0~4            0      
	      // 2         5~9            5
	      // 3         10~14         10
	      // 4         15~19         15
	      // 5         20   ~24         20 
	      // 6          25   ~29         25
	      //1씩 증가하면 5씩 증가?
	      
    	  ArrayList<BoardDTO> cartList = dao.cartList(cnt, offset, id);
    	  map.put("cartList", cartList);
	      
	      return map;
	   }


	public void cartDel(String cart_idx) {
		logger.info("찜삭제");
		
		int row = dao.cartDel(cart_idx);
		logger.info("삭제된 데이터수 " + row);
		
	}


	public void addcart(CartDTO cart) {
		logger.info("찜하기");
		dao.addcart(cart);
	}


	public String checkcart(CartDTO cart) {
		logger.info("찜체크");
		return dao.checkcart(cart);
		
	} 


}

	


	


