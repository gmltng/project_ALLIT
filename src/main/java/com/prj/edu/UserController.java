package com.prj.edu;

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

import com.prj.edu.dto.UserDTO;
import com.prj.edu.service.RecruitService;
import com.prj.edu.service.UserService;

@Controller
public class UserController {
   @Autowired UserService service;
   @Autowired RecruitService r_service;
   
   Logger logger = LoggerFactory.getLogger(this.getClass());
   @RequestMapping(value = "/")
   public String home(Model model) {
      logger.info("사이트 정상 진입");
      return "main";
   }

   @RequestMapping(value = "/login.go")
   public String login( Model model) {
      logger.info("비회원 로그인 페이지");
      return "login";
   }
   @RequestMapping(value = "/joinForm.go")
   public String joinForm1( Model model) {
      logger.info("비회원 회원가입 페이지");
      return "joinForm";
   }

   @RequestMapping(value = "/joinForm")
   public String joinForm(Model model) {
      logger.info("회원가입 페이지 들어갔는지");
      return "joinForm";
   }


   @RequestMapping(value = "/msdetail.go")
   public String msdetail(Model model) {
      logger.info("Q&A 답변 페이지");
      return "msqnaDetail";
   }
   
   @RequestMapping(value = "/qnaHistory.go")
   public String qnaHistory(Model model, HttpSession session) {
      logger.info("작성한 Q&A 페이지");
      String id = (String) session.getAttribute("loginId");
      String cateId = service.findCateId(id);
      logger.info(cateId);
      
      String page = "qnaHistoryEdu";
      if(cateId.equals("일반회원")) {
    	  page = "qnaHistory";
      }
      
      return page;
   }
   
   @RequestMapping(value = "/main.go")
	public String main(Model model) {
	  logger.info("메인페이지 이동");
	  return "main";
	}
   
   @RequestMapping(value = "/vslogin.go")
   public String vslogin(HttpSession session, Model model) { // 정현민 수정
      String id = (String) session.getAttribute("loginId");
      String page ="";
      
		if(id == null) { // 정현민 수정 (비로그인 접근 막는 코드) 
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("url", "/login.go");
			page = "alert";
		} else { // 정현민 수정 (id 값이 null 일때 마이페이지로 접근하면 아래 서비스요청에서 에러 발생하기 때문에 else 사용)
			logger.info("ID의 값 : "+id);
			int num = service.userCategory(id);
			page = service.loginok(id,num);
			logger.info("로그인 카테고리 비교");
		}
      
      return page;
   }

   @RequestMapping(value = "/login")
   public String login(Model model, HttpServletRequest request) throws Exception {
      logger.info("로그인 페이지 입장");
      String mb_id = request.getParameter("mb_id");
      String mb_pass = request.getParameter("mb_pw");
      logger.info(mb_id+"/"+mb_pass);
      String loginId = service.login(mb_id,mb_pass);
      UserDTO dto = service.userdto(loginId);
      logger.info("로그인 아이디 : "+loginId);
      HttpSession session = request.getSession();
      session.setAttribute("loginId", loginId);
      String page = "alert";
      if(loginId != null) {
         model.addAttribute("url", "/main.go");
         model.addAttribute("msg", loginId+"님 환영합니다.");
         if(dto.getMb_status()>0) {
            if(service.realtime(loginId)>0) {
               model.addAttribute("msg", "정지가 해제되었습니다.");
               model.addAttribute("url", "/login.go");
               service.cnt(loginId,0);
            }else {
               model.addAttribute("msg", "정지된 회원입니다. "+service.stopdate(loginId)+" 까지");
               model.addAttribute("url", "/login.go");
               session.removeAttribute("loginId");
            }
         }else if(dto.getCategory_idx()==5) {
            model.addAttribute("msg", "탈퇴된 회원입니다.");
            model.addAttribute("url", "/login.go");
            session.removeAttribute("loginId");
         }
      }else{
         model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요.");
         model.addAttribute("url", "/login.go");
      }
      return page;
   }
   
   @RequestMapping(value = "/edujoin.go")
   public String eduJoin( Model model) {
      logger.info("교육기관 회원가입 페이지");
      return "eduJoin";
   }
   
   @RequestMapping(value = "/join.go")
   public String write(MultipartFile[] photo_name, @RequestParam HashMap<String, String> params, 
         HttpServletRequest session,  Model model){
       
      logger.info("사업자등록증" + photo_name);
      logger.info("param : {}",params);
      
      service.EduJoin(params);
      int sum = service.joEdu(photo_name, params);
      String msg = "교육기관 회원가입에 실패하였습니다.";
      if(sum>0) {
         msg = "교육기관 회원가입에 성공하였습니다.";
      }
      model.addAttribute("msg", msg);
      model.addAttribute("url", "/main.go");
      return "alert";
   }

   @RequestMapping(value = "/idFind.go")
   public String idFind( Model model) {
      logger.info("아이디 찾기 페이지");
      return "idFind";
   }

   @RequestMapping(value = "/idsearch")
   public String idsearch(Model model, HttpServletRequest request) {
      String mb_email = request.getParameter("mb_email");
      String mb_tel = request.getParameter("mb_tel");
      logger.info("아이디 찾기 페이지 설정");
      logger.info(mb_email+"/"+mb_tel);
      if(service.idsearch(mb_email,mb_tel)!=null) { 
         model.addAttribute("msg","당신의 아이디는 : "+service.idsearch(mb_email,mb_tel)+"입니다.");
      }else {
         model.addAttribute("msg","올바른 정보를 입력해주세요.");
      }
      return "login";
   }

   @RequestMapping(value = "/pwFind.go")
   public String pwFind( Model model) {
      logger.info("비밀번호 찾기 페이지");
      return "pwFind";
   }

   @RequestMapping(value = "/pwsearch")
   public String pwsearch(Model model, HttpServletRequest request) {
      String mb_id = request.getParameter("mb_id");
      String mb_email = request.getParameter("mb_email");
      String mb_tel = request.getParameter("mb_tel");
      logger.info("비밀번호 찾기 페이지 설정");
      logger.info(mb_id+"/"+mb_email+"/"+mb_tel);
      logger.info(service.pwsearch(mb_id,mb_email,mb_tel));
      String path="login";
      if(service.pwsearch(mb_id,mb_email,mb_tel)!=null) { 
         model.addAttribute("msg","회원님의 비밀번호는 "+service.pwsearch(mb_id,mb_email,mb_tel)+" 입니다.");
      }else {
         model.addAttribute("msg","올바른 정보를 입력해주세요.");
      }
      return path;
   }
   @RequestMapping(value = "/pwequl.ajax")
   @ResponseBody
   public HashMap<String, Object> pwequl(@RequestParam String pass, @RequestParam String pw) {
      logger.info("비밀번호 일치 하는지?");
      logger.info(pass,pw);
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("equl",pass.equals(pw));
      return map;
   }

   @RequestMapping("/overlay.ajax")
   @ResponseBody
   public HashMap<String, Object> overaly(@RequestParam String chkId) {
      logger.info("아이디 중복 체크 : "+chkId);
      return service.overlay(chkId);
   }


   @RequestMapping(value = "/newPass")
   public String newPass(Model model, HttpServletRequest request) {
      String mb_pass1 = request.getParameter("mb_pass1");
      String mb_pass2 = request.getParameter("mb_pass2");
      logger.info("비밀번호 변경 페이지 설정");
      logger.info(mb_pass1+"/"+mb_pass2);
      if(mb_pass1.equals(mb_pass2)) {
         service.newpass(mb_pass1);
         model.addAttribute("msg","비밀번호가 변경되었습니다.");
      }else {
         model.addAttribute("msg","비밀번호가 일치하지 않습니다.");
      }
      return "login";
   }



   @RequestMapping("/join.ajax")
   @ResponseBody
   public HashMap<String, Object> join(@RequestParam HashMap<String, Object> params){
      logger.info("회원가입: "+params);
      HashMap<String, Object> map = new HashMap<String, Object>();
      service.join(params); 
      map.put("msg", 1);
      return map;
   }

   @RequestMapping("/mslist.ajax")
   @ResponseBody
   public HashMap<String, Object> mslist(@RequestParam HashMap<String, String> params) {
      logger.info("리스트 요청!!! : {}",params);
      return service.mslist(params);
   }

   @RequestMapping(value = "/userList.go")
   public String userList( Model model) {
      logger.info("로그인후 페이지");
      return "userList";
   }

   @RequestMapping(value = "/eduList.go")
   public String eduList( Model model) {
      logger.info("로그인후 페이지");
      return "eduList";
   }

   //리스트 아작스 요청
   @RequestMapping("user/list.ajax")
   @ResponseBody
   public HashMap<String, Object> list1(
         @RequestParam HashMap<String, String> params
         ) {
      logger.info("일반회원 리스트 요청!!! : {}",params);
      return service.list(params);
   }
   //리스트 아작스 요청
   @RequestMapping("edu/list.ajax")
   @ResponseBody
   public HashMap<String, Object> list2(@RequestParam HashMap<String, String> params) {
      logger.info("교육기관회원 리스트 요청!!! : {}",params);
      return service.edulist(params);
   }

   @RequestMapping(value = "user/detail.go")
   public String userDetail(Model model, HttpSession session, @RequestParam String mb_id) {
      logger.info("상세보기 요청 : " + mb_id);   
      UserDTO dto = service.userDetail(mb_id);
      model.addAttribute("dto", dto);
      return "userDetail";
   }
   @RequestMapping(value = "edu/detail.go")
   public String eduDetail(Model model, HttpSession session, @RequestParam String mb_id) {
      logger.info("상세보기 요청 : " + mb_id);   
      UserDTO dto = service.eduDetail(mb_id);
      model.addAttribute("dto", dto);
      return "eduDetail";
   }

   @RequestMapping(value = "/userInfo.go")
   public String userInfo(Model model, HttpSession session, @RequestParam String mb_id) {

      String id = (String) session.getAttribute("loginId");
      UserDTO dto = service.userInfo(id);
      model.addAttribute("dto", dto);
      return "userInfo";
   }

   @RequestMapping(value = "/delete")
   public String delete(Model model, HttpSession session) {
     String mb_id = (String)session.getAttribute("loginId");         
     logger.info("탈퇴 요청" + mb_id);
     service.delete(mb_id);
     model.addAttribute("msg","탈퇴 완료하였습니다.");
     model.addAttribute("url","/");
     session.removeAttribute("loginId");
     return "alert";
   }

   @RequestMapping(value = "/userupdate.go")
   public String usUpdate(Model model, HttpSession session) {
      String id = (String) session.getAttribute("loginId");
      UserDTO dto = service.userInfo(id);
      model.addAttribute("dto", dto);
      return "userUpdate";
   }

   @RequestMapping(value = "/userupdate.do")
	public String userupdate(Model model, HttpSession session, @RequestParam HashMap<String, Object> params) {
	  logger.info("수정하기 폼 도착 완료");
	  String id = (String) session.getAttribute("loginId");
	  String url="/userInfo.go?mb_id="+id;
	  service.userupdate(params);
	  model.addAttribute("msg","수정을 완료하였습니다.");
	  model.addAttribute("url",url);
	  return "alert";
	}

   @RequestMapping(value = "/reportList.go")
   public String reportList( Model model) {
      logger.info("신고 회원 리스트 페이지");
      return "reportList";
   }

   //리스트 아작스 요청
   @RequestMapping("reportList.ajax")
   @ResponseBody
   public HashMap<String, Object> list3(@RequestParam HashMap<String, String> params) {
      logger.info("교육기관회원 리스트 요청!!! : {}",params);
      return service.reportList(params);
   }
   
   @RequestMapping(value = "/blackList.go")
      public String blackList( Model model) {
         logger.info("정지 회원 리스트 페이지");
         return "blackList";
      }
   
   @RequestMapping("blackList.ajax")
   @ResponseBody
   public HashMap<String, Object> blackList(@RequestParam HashMap<String, String> params) {
      logger.info("교육기관회원 리스트 요청!!! : {}",params);
      return service.blackList(params);
   }
   
   @RequestMapping("/usqnalist.ajax")
   @ResponseBody
   public HashMap<String, Object> usqnalist(HttpSession session, @RequestParam HashMap<String, String> params) {
      logger.info("작성한 Q&A 요청 : {}",params);
      String name = (String) session.getAttribute("loginId");
      return service.usqnalist(params,name);
   }
   
   @RequestMapping(value = "/blind.do")
   public String blind( Model model, @RequestParam String board_idx, HttpServletRequest request) {
      logger.info("블라이드 처리 요청");
      
      logger.info("받아온 게시글 번호 : " + board_idx);
      //logger.info("받아온 게시글 번호 : " + report_idx);
      String blindYn = request.getParameter("blind");
      String report_state = request.getParameter("report_state");
      logger.info("블라인드 : " + blindYn);
      logger.info("처리상태 : " + report_state);
      //service.blind(board_idx, blindYn,report_idx,report_state);
      service.blind(board_idx, blindYn);
      
      return "redirect:/reportList.go";
   }   
   
   //성민 내가 쓴 게시글 이동
   @RequestMapping(value = "/boardHistory.go")
   public String boardHistory(Model model) {
      logger.info("작성한 게시글 이동");
      return "boardHistory";
   }
   
   //성민 
   @RequestMapping("/boardHistory.ajax")
   @ResponseBody
   public HashMap<String, Object> boardHistoryajax(HttpSession session, @RequestParam HashMap<String, String> params) {
      logger.info("작성한 게시글 요청 : {}",params);
      String name = (String) session.getAttribute("loginId");
      return service.boardHistoryajax(params,name);
   }
   
   @RequestMapping(value = "/logout")
   public String logout(Model model, HttpSession session) {
      logger.info("로그아웃 페이지");
      session.removeAttribute("loginId");
      logger.info("{},",session.getAttribute("loginId"));
      String msg = "로그아웃 되었습니다.";
      String page = "alert";
      model.addAttribute("msg",msg);
      model.addAttribute("url","/login.go");
      return page;
   }
   
   @RequestMapping("/user/userStop.do")
   public String userStop(Model model, @RequestParam HashMap<String, Object> params,
         @RequestParam int mb_status, @RequestParam int mb_stopcnt, @RequestParam String mb_id) {
      logger.info("정지회원 확인 : {}",params);
      String msg = "회원 정지에 실패하였습니다.";
      if(mb_status>0) { 
         mb_stopcnt++;
         params.put("mb_stopcnt", mb_stopcnt);
         msg = service.userStop(params);
      }else {
         mb_status=0;
         service.cnt(mb_id,mb_status);
         msg = "회원의 정지를 해제하였습니다.";
      }
      model.addAttribute("msg",msg);
      model.addAttribute("url","/vslogin.go");
      return "alert";      
   }
   
   @RequestMapping("/edu/userStop.do")
   public String eduStop(Model model, @RequestParam HashMap<String, Object> params,
         @RequestParam int mb_status, @RequestParam int mb_stopcnt, @RequestParam String mb_id) {
      logger.info("정지회원 확인 : {}",params);
      String msg = "교육기관 정지에 성공하였습니다.";
      if(mb_status>0) { 
         mb_stopcnt++;
         params.put("mb_stopcnt", mb_stopcnt);
         msg = service.userStop(params);
      }else {
         mb_status=0;
         service.cnt(mb_id,mb_status);
         msg = "교육기관 회원의 정지를 해제하였습니다.";
      }
      model.addAttribute("msg",msg);
      model.addAttribute("url","/vslogin.go");
      return "alert";      
   }
   
   @RequestMapping("/user/manager.do")
   public String manager(Model model,@RequestParam String mb_id, @RequestParam int num) {
      String msg="권한 위임에 실패하였습니다.";
      if(num>0) {
         service.cate(mb_id,3);
         msg="권한 위임에 성공하였습니다.";
      }else {
         service.cate(mb_id,1);
         msg="권한 해제에 성공하였습니다.";
      }
      model.addAttribute("msg",msg);
      model.addAttribute("url","/vslogin.go");
      return "alert";
   }
   
  	// 교육회원 마이페이지 > 작성한 모집공고 리스트 (정현민 추가)
	@RequestMapping(value = "/recruitHistory.go", method = RequestMethod.GET)
	public String recruitHistoryGo(Model model, HttpServletRequest request, HttpSession session) {
		logger.info("리스트 요청!");

		// 모집공고의 모든 글들의 오늘날짜(recruit_date)를 최신으로 업데이트하는 기능 > 마감여부와 마감일(d-day)의 정확한 계산을 위해서
		r_service.updateDate(); 
		logger.info("날짜 업데이트 요청");
		
		// 모집마감일이 오늘날짜보다 전이라면 recruit_close는 0, 아니면 1로 업데이트 하는 기능
		r_service.closeYn();
		logger.info("모집여부 검사 요청");
		
		return "recruitHistory";
	}
	
	// 교육회원 마이페이지 > 작성한 모집공고 리스트 아작스 (정현민 추가)
	@RequestMapping("/recruitHistory.ajax")
	@ResponseBody
   public HashMap<String, Object> recruitHistory(
   		@RequestParam HashMap<String, String> params, HttpSession session) {
     	logger.info("리스트 요청 : {}",params);
     	logger.info(params.get("id"));
     	return service.r_HistoryList(params);
	}
	
	@RequestMapping(value = "/eduInfo.go")
	   public String eduInfo(Model model, HttpSession session, @RequestParam String mb_id) {
	      String id = (String) session.getAttribute("loginId");
	      service.eduInfo(model, id);
	     
	      return "eduInfo";
	}
	
	 @RequestMapping(value = "/eduupdate.go")
	   public String eduUpdate(Model model, HttpSession session) {
	      String id = (String) session.getAttribute("loginId");
	      UserDTO dto = service.eduInfo(id);
	      model.addAttribute("dto", dto);
	      return "eduUpdate";
	   }

	@RequestMapping(value = "/eduupdate.do")
	   public String eduupdate(Model model, HttpSession session, @RequestParam HashMap<String, Object> params) {
	      logger.info("수정하기 폼 도착 완료");
	      String id = (String) session.getAttribute("loginId");
	      String url="/eduInfo.go?mb_id="+id;
	      service.userupdate(params);
	      model.addAttribute("msg","수정을 완료하였습니다.");
	      model.addAttribute("url",url);
	      return "alert";
	   }
   
   
   
   
   
}
