package com.prj.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
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

import com.prj.edu.dto.CstDTO;
import com.prj.edu.service.CstService;


@Controller
public class CstController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired CstService service;
	
//	public int consCnt(Model model) {
//		int cnt = service.consCnt();
//		model.addAttribute("cnt", cnt);
//		
//		return 0;
//	}
	
	// 회원 분류에 따라 해당 리스트 내역을 보여주기 위한 코드
	@RequestMapping(value = "/cstList.go", method = RequestMethod.GET) 
		public String cstList(Model model,HttpSession session,HttpServletRequest request) {
			
			logger.info("어떤 회원의 상담 신청 리스트로 이동할까??"); 
			
			String page = "login";
			
			if(session.getAttribute("loginId") != null) {
			String loginId = (String) session.getAttribute("loginId");
			
			int num = service.userCategory(loginId); // 회원의 분류코드 받아오기
			
			logger.info("로그인 아이디: "+loginId+"  |  "+"회원번호: "+num);
			
			
			 if(num == 1){ 
				 page = "redirect:/cstListMb.go";
				 int mbCnt = service.mbCnt(loginId);
				 logger.info("1. 상담 신청 미열람 갯수는?? "+mbCnt);
				 //session = request.getSession();
				 session.setAttribute("mbCnt", mbCnt);
				 int cnt = (int) session.getAttribute("mbCnt");
				 logger.info("1. 미열람 리스트 갯수가 세션에 담겼니?? "+cnt);
				 
			 } else if(num ==2){
				 page = "redirect:/cstListEdu.go";				 
				 int eduCnt = service.eduCnt(loginId);
				 logger.info("1. 상담 신청 미열람 갯수는?? "+eduCnt);
				 //session = request.getSession();
				 session.setAttribute("eduCnt", eduCnt);
				 int cnt = (int) session.getAttribute("eduCnt");
				 logger.info("1. 미열람 리스트 갯수가 세션에 담겼니?? "+cnt);
			 	}
			}
			 else {
				 model.addAttribute("msg","로그인이 필요한 서비스입니다.");
			 }
			return page;
		}
	
	
	// 일반회원 => 상담 신청 리스트로 이동
	 @RequestMapping(value = "/cstListMb.go", method = RequestMethod.GET) 
		 public String cstListMb(Model model,HttpSession session) {
		 	
		 	logger.info("일반회원 상담 신청 리스트로 이동");
			String page = "login";
			
			if(session.getAttribute("loginId") != null){
				page="cstListMb";

			}else {
				page = "login";
				model.addAttribute("msg","로그인이 필요한 서비스입니다.");
			}
			return page; 
	 	}
	
	 
	 // 일반회원 => 상담신청 내역 불러오기
	@RequestMapping("/cstListMb.ajax")
	@ResponseBody 
		public HashMap<String, Object> mbList( @RequestParam HashMap<String, String> params, HttpSession session){ 
			
			logger.info("일반회원 상담 신청 리스트 ajax");
			String page = "login";
		
			
			if(session.getAttribute("loginId") != null){ 
				logger.info("list 요청"+params);
				String loginId = (String) session.getAttribute("loginId");
				int mbCnt = service.mbCnt(loginId);
				 session.setAttribute("mbCnt", mbCnt);
				 int cnt = (int) session.getAttribute("mbCnt");
				 logger.info("2. 미열람 리스트 갯수가 세션에 담겼니?? "+cnt);
				
			}else {
				
			}
			return service.mbList(session,params); 
		} 	
		
	
	// 교육기관  => 상담 신청 리스트로 이동
	 @RequestMapping(value = "/cstListEdu.go", method = RequestMethod.GET) 
		 public String cstListEdu(Model model,HttpSession session) {
			 
		 	logger.info("교육기관 상담 신청 리스트로 이동");
			String page = "login";
			
			if(session.getAttribute("loginId") != null){
				page="cstListEdu";
			}else {
				page = "login";
				model.addAttribute("msg","로그인이 필요한 서비스입니다.");
			}
			return page;
	 	}
	
	 

	 // 교육기관 => 상담신청 내역 불러오기
 	@RequestMapping("/cstListEdu.ajax")
 	@ResponseBody 
 		public HashMap<String, Object> eduList( @RequestParam HashMap<String, String> params, HttpSession session){ 
			
 			logger.info("교육기관 회원 상담 신청 리스트 ajax");
			String page = "login";
			
			if(session.getAttribute("loginId") != null){ 
				logger.info("list 요청"+params);
				String loginId = (String) session.getAttribute("loginId");
				int eduCnt = service.eduCnt(loginId);
				session.setAttribute("eduCnt", eduCnt);
				int cnt = (int) session.getAttribute("eduCnt");
				logger.info("2. 미열람 리스트 갯수가 세션에 담겼니?? "+cnt);
			}else {
				
			}
			return service.eduList(session,params); 
		} 	
	
		/*
		 * // 상담 신청하기 페이지 이동 (일반회원만 글쓰기 가능)
		 * 
		 * @RequestMapping(value = "/cstWrite.go") public String
		 * cstWriteForm(HttpSession session,Model model) {
		 * 
		 * logger.info("상담 신청하기 페이지 이동"); String page = "main"; String loginId =
		 * (String) session.getAttribute("loginId"); int num =
		 * service.userCategory(loginId); // 회원 번호를 가져온다.
		 * 
		 * logger.info("로그인 아이디: "+loginId+", "+"회원번호: "+num);
		 * 
		 * if(num == 1){ // 일반회원만 글쓰기가 가능하게 page = "redirect:/cstInfo.go"; }else { page
		 * = "redirect:/recruitList.go"; model.addAttribute("msg","글쓰기 권한이 없습니다."); }
		 * return page; }
		 */
	 
	 
		// 상담 신청 회원정보와 과정명 불러오기
		@RequestMapping(value = "/cstInfo.go")
			public String cstInfo(Model model,HttpSession session,@RequestParam String idx, @RequestParam String field) {
			
				logger.info("모집공고 번호? "+idx);
				String page = "login";
				logger.info("상담 신청 회원 정보 불러오기 요청");
				
				if(session.getAttribute("loginId") != null){
					CstDTO dto = service.cstInfo(session,idx);
					if(dto != null) {					
						model.addAttribute("dto",dto);
						model.addAttribute("field",field);
						page = "cstWrite";
					}
				}
				return page;
			}
	 
	
	// 상담 신청하기 (recruit_idx 연결해야 함)
	@RequestMapping(value ="/cstWrite.do")
		public String cstWrite(@RequestParam HashMap<String, String> params,HttpSession session, Model model){ 
			

			logger.info("상담 신청하기");
			logger.info("param : {}",params);
		
			int result = service.cstWrite(params,session);
			
			String page = "";
			if (result > 0) {
				model.addAttribute("msg", "상담신청이 완료되었습니다.");
				model.addAttribute("url", "/recruitList.go?field=all");
				page = "alert";
			}
			
//			model.addAttribute("msg","상담 신청이 완료되었습니다.");
			
			return page;
		}
	
	
		/*
		 * @RequestMapping(value = "/cstInfo.go") public String cstInfo(Model
		 * model,HttpSession sessiont) {  String page =
		 * "cstList"; logger.info("상담 신청 회원 정보 불러오기 요청");
		 * if(session.getAttribute("loginId") != null){ CstDTO dto =
		 * service.cstInfo(session); if(dto != null) {
		 * model.addAttribute("dto",dto); page = "cstWrite"; } }else {
		 * model.addAttribute("msg","로그인이 필요한 서비스입니다."); } return page; }
		 */
	
	
 	// 상담 신청 내역 상세보기 이동
	@RequestMapping(value = "cstDetail.do")
		public String cstDetail(Model model,HttpSession session, @RequestParam String cons_idx) {
		
			String page = "redirect:/cstList.go";
			logger.info("상세보기 요청: "+cons_idx);
			String loginId = (String) session.getAttribute("loginId");
			int num = service.userCategory(loginId);
			logger.info("로그인 아이디: "+loginId+", "+"회원번호: "+num);
		
			if(session.getAttribute("loginId") != null){
	 
				 if(num == 2){ // 교육기관 회원이 들어갔을 경우에만 열람으로 바꿔준다.
					 page = "redirect:/cstListEdu.go";
					 service.open(cons_idx);
				 }else {
					 page ="redirect:/cstListMb.go";
				 }
				 
				CstDTO dto = service.cstDetail(cons_idx,session);
				
				if(dto != null) {					
					model.addAttribute("dto",dto);
					page = "cstDetail";
				}	
			}else {
				model.addAttribute("msg","로그인이 필요한 서비스입니다.");
			}
			return page;
			}

	// 일반회원 => 상담 신청 내역 삭제
	@RequestMapping("/mbDel.ajax")
	@ResponseBody
		public HashMap<String, Object> mbDel(HttpSession session, @RequestParam(value="delList[]") ArrayList<String> delList){
	
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info("삭제 요청 : "+delList);
				
			int cnt = service.mbDel(delList);
			map.put("msg",cnt+"개의 상담 신청 내역이 삭제 되었습니다.");
			
			 String page = "cstListMb";
			 map.put("page", page);
	
			return map;
	}
			
		
	// 교육기관 => 상담 신청 내역 삭제
	@RequestMapping(value = "/eduDel.ajax")
	@ResponseBody
		public HashMap<String, Object> eduDel(HttpSession session,
				@RequestParam(value="delList[]") ArrayList<String> delList) {
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info("삭제: "+delList);
			
			int cnt = service.eduDel(delList);
			map.put("msg",cnt+"개의 상담 신청 내역이 삭제 되었습니다.");
			
			 String page = "cstListEdu";
			 map.put("page", page);
	
			return map;	
		}
	
	
	// 교육기관 => 상담 신청서 미열람 갯수 알림 띄우기
	/*
	 * @RequestMapping(value = "/count.do") public String count(Model
	 * model,HttpSession session) {
	 * 
	 * String page = "cstList"; logger.info("상담 신청 회원 정보 불러오기 요청");
	 * if(session.getAttribute("loginId") != null){ CstDTO dto =
	 * service.cstIn(session); if(dto != null) { model.addAttribute("dto",dto); page
	 * = "cstWrite"; } }else { model.addAttribute("msg","로그인이 필요한 서비스입니다."); }
	 * return page; }
	 */
	

}
