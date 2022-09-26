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
import org.springframework.web.multipart.MultipartFile;

import com.prj.edu.dto.BoardDTO;
import com.prj.edu.service.RecruitService;

@Controller
public class RecruitController {
	
	// GDJ 3조 정현민
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired RecruitService service;
	
	// 리스트 이동
	@RequestMapping(value = "/recruitList.go", method = RequestMethod.GET)
	public String recruitList(Model model, HttpServletRequest request, HttpSession session) {
		logger.info("리스트 요청!");
		
		// field(교육분야) 의 값을 가져오는 기능
		// field 값 : all, 웹개발 (Front-end), 웹개발 (Back-end), 앱개발 (Kotlin), 앱개발 (Swift)
		// 적성검사 결과가 field 에 담기며, 해당 데이터와 모집공고.jsp 의 field 를 비교해 올바른 경로로 안내함
		String field = request.getParameter("field"); 
		logger.info("교육분야 : " + field);  
		
		// 모집공고의 모든 글들의 오늘날짜(recruit_date)를 최신으로 업데이트하는 기능 > 마감여부와 마감일(d-day)의 정확한 계산을 위해서
		service.updateDate();
		logger.info("날짜 업데이트 요청");
		
		// 모집마감일이 오늘날짜보다 전이라면 recruit_close는 0, 아니면 1로 업데이트 하는 기능
		service.closeYn();
		logger.info("모집여부 검사 요청");
		
		// 로그인 세션 확인
		String cateId = (String) session.getAttribute("loginId");
		
		String page = "recruitList";
		
		// 비로그인 회원 접근을 막는 코드 (새로고침해도 alert 창이 반복되지 않는다.)
		if(cateId == null) {
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("url", "/login.go");
			page = "alert";
		}
		
		// 현재 로그인 되어있는 아이디의 등급('일반회원', '교육기관 회원', '매니저 회원', '관리자')을 찾아오는 기능
		// 글쓰기 버튼 노출 여부를 결정해야 하기 때문에 필요
		String category = service.findCateId(cateId);
		logger.info(category);
		
		// 조회수 기준 상위 4개의 공고글의 데이터를 가져오는 기능
		// 마감된 공고글은 hotList 에서 제외함
		// 위에서 날짜 업데이트, 마감여부 검사를 미리 마쳤기 때문에 최신화된 핫리스트 유지 가능
		ArrayList<BoardDTO> list = service.hotList();
		logger.info("list size : " + list.size());
		
		model.addAttribute("field",field);
		model.addAttribute("hotList",list);
		model.addAttribute("category",category);
		
		return page;
	}

	@RequestMapping("/recruitList.ajax")
	@ResponseBody
    public HashMap<String, Object> recruitAjax(
    		@RequestParam HashMap<String, String> params, HttpSession session) {
		params.put("mb_id", (String) session.getAttribute("loginId"));
      	logger.info("리스트 요청 : {}",params);
      	return service.list(params);
	}
	
	@RequestMapping(value = "/recruit/Write.go")
	public String writeGo(Model model,HttpSession session) {
		logger.info("모집공고 글쓰기 페이지 이동");
		
		String id = (String)session.getAttribute("loginId");
		
		
		
		logger.info("로그인된 아이디 : " + id);
		
		// 로그인된 아이디의 데이터를 글쓰기 페이지에 넣어주는 기능
		// 교육기관명, 담당자 ID, 연락처 등의 고정데이터
		BoardDTO dto = service.writeList(id);
		model.addAttribute("writeList",dto);
		return "recruitWrite";
	}
	
	@RequestMapping(value = "/recruit/Write.do")
	   public String write(MultipartFile[] r_photo, @RequestParam HashMap<String, String> params, 
	         HttpServletRequest session,  Model model){
	       
	      logger.info("사진" + r_photo);
	      logger.info("글쓰기 요청");
	      logger.info("param : {}",params);
	      
	      service.write(r_photo, params);
	      
	      // field=all 값을 넣어서 redirect 함
	      // 모집공고 리스트로 넘어갔을 때, '전체보기'가 선택되어있어야 하기 때문에
	      return "redirect:/recruitList.go?field=all";
	}
	
//	@RequestMapping(value = "/recruit/detail.do")
//	public String recruitDetail(Model model, @RequestParam String idx, @RequestParam String field, HttpSession session) {
//		logger.info("상세보기 요청" + idx);
//		
//		// 현재 로그인 되어있는 아이디의 등급('일반회원', '교육기관 회원', '매니저 회원', '관리자')을 찾아오는 기능
//		// '일반회원'을 제외한 나머지에게 수정하기, 삭제하기에 접근 권한을 주기 위해 필요
//		// 단, '교육기관 회원'은 본인이 작성한 글에 한해서만 수정하기, 삭제하기 접근 권한 O
//		String id = (String) session.getAttribute("loginId");
//		String category = service.findCateId(id);
//		
//		if(category != "교육기관") 
//		
//		// 상세보기로 들어가면서 마감여부를 한번 더 업데이트하는 기능
//		// 마감여부에 따라 상담신청 블락을 확실하게 하기 위해서
//		service.closeYn(); 
//		
//		// 조회 수 올리기 기능
//		service.upHit(idx);
//		
//		//상세정보 가져오기 기능
//		BoardDTO dto = service.recruitDetail(idx); 
//		
//		model.addAttribute("recruit",dto);
//		model.addAttribute("category",category);
//		model.addAttribute("field",field);
//		
//		return "recruitDetail";
//	}
	
	@RequestMapping(value = "/recruit/detail.do")
	   public String recruitDetail(Model model, @RequestParam String idx, @RequestParam String field, HttpSession session) {
	      logger.info("상세보기 요청" + idx);
	      
	      // 현재 로그인 되어있는 아이디의 등급('일반회원', '교육기관 회원', '매니저 회원', '관리자')을 찾아오는 기능
	      // '일반회원'을 제외한 나머지에게 수정하기, 삭제하기에 접근 권한을 주기 위해 필요
	      // 단, '교육기관 회원'은 본인이 작성한 글에 한해서만 수정하기, 삭제하기 접근 권한 O
	      String id = (String) session.getAttribute("loginId");
	      String category = service.findCateId(id);
	      
	      if(category != "교육기관") 
	      
	      // 상세보기로 들어가면서 마감여부를 한번 더 업데이트하는 기능
	      // 마감여부에 따라 상담신청 블락을 확실하게 하기 위해서
	      service.closeYn(); 
	      
	      // 조회 수 올리기 기능
	      service.upHit(idx);
	      
	      //상세정보 가져오기 기능
	      service.recruitDetail(model, idx); 
	      model.addAttribute("category",category);
	      model.addAttribute("field",field);
	      
	      return "recruitDetail";
	   }
	
	@RequestMapping(value = "/recruit/delete.do")
	public String delete(@RequestParam String idx, HttpSession session) {
		logger.info("삭제 요청 : " + idx);
		
		// 선택한 게시글을 삭제하는 기능
		service.delete(idx);
		
		// field=all 값을 넣어서 redirect 함
		// 모집공고 리스트로 넘어갔을 때, '전체보기'가 선택되어있어야 하기 때문에
		return "redirect:/recruitList.go?field=all";
	}
	
	@RequestMapping(value = "/recruit/update.go")
	   public String updateGo(Model model, @RequestParam String idx, HttpServletRequest session) {
	      logger.info("수정 상세보기 요청" + idx);
	      
	      // 상세정보 가져오기 기능
	      // 교육기관명, 연락처, 주소 등은 수정하지 못하도록 고정(readonly)
	      service.recruitDetail(model, idx);
	   //   model.addAttribute("recruit", dto);
	      
	      return "recruitUpdate";
	   }
	
	@RequestMapping(value = "/recruit/update.do")
	   public String update(MultipartFile[] r_photo, Model model, @RequestParam HashMap<String, String> params, HttpServletRequest session) {
	      int recruit_idx = Integer.parseInt(params.get("recruit_idx"));
	      logger.info("수정 요청");
	      logger.info("param : {}",params);
	      int num = service.update(r_photo, params);
	      if(num>0) {
	         model.addAttribute("msg", "수정에 성공하였습니다.");
	         model.addAttribute("url", "/recruitList.go?field=all");
	      }
	      return "alert";
	}
}
