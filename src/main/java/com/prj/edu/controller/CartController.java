package com.prj.edu.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prj.edu.dto.CartDTO;
import com.prj.edu.service.CartService;
import com.prj.edu.service.RecruitService;

@Controller
public class CartController {
	
		
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired CartService service;
	@Autowired RecruitService r_service;
	
	@RequestMapping(value = "/cartList.go", method = RequestMethod.GET)
	public String cartPage()  {
		logger.info("찜 페이지 이동");		
		return "cartList";
	}
	

	// 찜 목록 보기
	
	@RequestMapping("/cartList.ajax")
	@ResponseBody 
    public HashMap<String, Object> cartAjax(
    		@RequestParam HashMap<String, String> params, HttpSession session) {
		// 1. recruitList.jsp 가 열릴 때, 오늘날짜가 모집마감기간을 넘어갔다면
		// 2. recruit_close 를 0 으로 update 한다.
		String id = (String) session.getAttribute("loginId");
      	logger.info("리스트 요청 : {}",params);
      	return service.list(params, id);
	}
	
	//찜 삭제
	
	@RequestMapping(value = "/deletecart", method = RequestMethod.GET)
	public String cartDelete(HttpSession session, Model model, @RequestParam String cart_idx) {
		
		logger.info("찜 삭제" + cart_idx);
		
		service.cartDel(cart_idx); 
		return "redirect:/cartList.go";
		
	}
	
	
	//찜하기
		
	  @RequestMapping(value = "/addcart", method = RequestMethod.GET)
	  public String addCart(CartDTO cart, HttpSession session,	  
	  @RequestParam int recruit_idx, Model model){
	  
	  logger.info("찜 확인 먼저" + recruit_idx);
	  
	  String mb_id = (String) session.getAttribute("loginId"); 
	  logger.info(mb_id);
	  //로그인한 아이디 확인
	  
	  cart.setMb_id(mb_id); //cartdto에 있는 mb_id를 로그인한 아이디로 넣어줌 
	  	 
	  
	  //로그인 확인 
	  if(mb_id == null) { 
		  logger.info("로그인 안되어 있음"); 
		  String msg="로그인 회원만 이용가능합니다";
		  model.addAttribute("msg",msg);
	  	
	  
	  // 찜되어있는지 확인 (클릭한 recruit_idx + 로그인한 mb_id가 있는 찜idx있는지 확인) 
	  } else if(service.checkcart(cart) == null) { //찜idx가 없으므로 찜 할 수 있음
		  logger.info("찜 가능"); //찜하기 
		  service.addcart(cart); 
		  
	  } else { 
		 String cart_idx = service.checkcart(cart);
		 service.cartDel(cart_idx);
	  }
	  
	  
	  return "redirect:/recruitList.go?field=all";
	  
	  }
	  
	
		
		/*
		 * //찜 여부 조회
		 * 
		 * @RequestMapping(value = "/checkcart", method = RequestMethod.GET) public
		 * String checkCart(CartDTO cart, HttpSession session, @RequestParam int
		 * recruit_idx){
		 * 
		 * logger.info("찜 했는지 확인" + recruit_idx);
		 * 
		 * String mb_id = (String) session.getAttribute("loginId"); logger.info(mb_id);
		 * 
		 * cart.setMb_id(mb_id); service.checkcart(cart);
		 * 
		 * 
		 * return "/addcart";
		 * 
		 * }
		 */

}
