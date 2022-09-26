package com.prj.edu.controller;

import java.util.ArrayList;
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

import com.prj.edu.dto.BoardDTO;
import com.prj.edu.dto.CmtDTO;
import com.prj.edu.dto.MsgDTO;
import com.prj.edu.dto.ReportDTO;
import com.prj.edu.service.BoardService;


@Controller
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired BoardService service;
	

	@RequestMapping(value = "/boardlist.go", method = RequestMethod.GET)
	public String home(Model model, HttpSession session, @RequestParam String board_category) {

		String page = "boardList";
		String board_cateloginId = (String) session.getAttribute("loginId");
		//카테고리 아이디 
		String board_cateId = service.board_cateId(board_cateloginId);
		model.addAttribute("board_cateId", board_cateId);
		//params.put("board_cateId", board_cateId);
		logger.info("board_cateId", board_cateId);
		model.addAttribute("board_category", board_category);
		
		if(board_cateloginId == null) { // 정현민 수정 (비로그인 접근 막는 코드) 
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("url", "/login.go");
			page = "alert";
		}
		
		
		return page;
	}

	//리스트 아작스 요청
	@RequestMapping("/boardlist.ajax")
	@ResponseBody
	public HashMap<String, Object> list1(
			@RequestParam HashMap<String, String> params, HttpSession session, Model model) {
		logger.info("session loginId" + session.getAttribute("loginId"));
		logger.info("params  : {}" + params);
		return service.list(params);
	}
	
	
	//상세보기 이동
	@RequestMapping(value = "/boarddetail.go")
	public String detail(Model model, HttpSession session, @RequestParam String board_idx, String board_category) {
	
		String page = "boardList"; //redirect:/
		logger.info("상세보기 요청 : " + board_idx);
		
		
		//0630추가됨
		String blind = service.chk_blind(board_idx); // 김동훈 추가
	     logger.info(blind); // 김동훈 추가
	      
	      String id = (String) session.getAttribute("loginId"); // 김동훈 추가
	      String cateId = service.cateId(id); // 김동훈 추가
	      logger.info(cateId);
	      //여기까지		

		if(session.getAttribute("loginId") != null) {
			
			String board_cateloginId = (String) session.getAttribute("loginId");
			model.addAttribute("loginId", board_cateloginId);
			//카테고리 아이디 
			String board_cateId = service.board_cateId(board_cateloginId);
			model.addAttribute("board_cateId", board_cateId);
			
			service.board_hits(board_idx); //조회수
			logger.info("test : " + board_idx);// DH
			BoardDTO dto = service.boarddetail(board_idx);
			if(dto != null) {
				model.addAttribute("dto", dto);
				page = "boarddetail";
				
				//댓글 리스트 출력, DTO만 따로 씀 DAO, mapper 동일함.
				ArrayList<CmtDTO> cmtlist =  service.cmt(board_idx);

				
				logger.info("댓글? : " + cmtlist.size());
				model.addAttribute("cmtlist", cmtlist);
				model.addAttribute("cmtsize", cmtlist.size());
				//model.addAttribute("mb_id", loginId);
			}
		}else {
			model.addAttribute("msg", "로그인이 필요한 서비스입니다,");
		}
		model.addAttribute("board_category", board_category);
		
	
		//추가됨
		 if(blind != null && blind.equals("1")) { // 김동훈 추가
	         model.addAttribute("msg", "블라인드 처리된 게시글입니다."); // 김동훈 추가
	         page = "boardList"; // 김동훈 추가
	         if(cateId.equals("매니저 회원") || cateId.equals("관리자")) {
	            page = "boarddetail";
	         }
	      }
		 model.addAttribute("cateId",cateId);
		 //여기까지
		 
		return page;
	}
	
	//수정하기 이동(수정 상세보기이동)
	@RequestMapping(value = "/boardUpdate.go")
	public String board_updatego(@RequestParam String board_idx, Model model, HttpSession session) {
		logger.info("수정 상세보기 요청 완료" + board_idx);
		logger.info("로그인 아이디 확인 : " + session.getAttribute("loginId"));
		
		String loginId = (String) session.getAttribute("loginId");
		
		//db에 sel날려서 찾아와야함.
		String chkId = service.chkId(board_idx);
		logger.info(chkId);
		String page = "redirect:/boarddetail.go?board_idx=" + board_idx;
		
		
		if(session.getAttribute("loginId").equals(chkId)) {
			BoardDTO dto = service.boarddetail(board_idx);
			model.addAttribute("dto", dto);
			page = "boardUpdate";
		}else {
			model.addAttribute("msg", "작성자 본인만 수정 가능합니다.");
		}
		return page;
	}
	
	
	//수정하기
	@RequestMapping(value = "/board_update.do", method = RequestMethod.POST)
	public String board_update (HttpSession session, Model model, 
			@RequestParam HashMap<String, String> params) {
		
		logger.info("params : {} " + params);
		String page = "redirect:/boarddetail.go?board_idx=" +params.get("board_idx");
		logger.info(page);
		
		 service.board_update(params);
		
		return page;
	}
	
	/*
	//리스트로 돌아가기
	@RequestMapping(value = "/list.go", method = RequestMethod.GET)
	public String listgo(Model model) {
	
		return "boardList";
	}
	*/
	
	
	@RequestMapping(value = "/boardWrite.go")
	public String writeForm(HttpSession session, Model model) {
		logger.info("글쓰기 페이지 이동");
		String board_cateloginId = (String) session.getAttribute("loginId");
		//카테고리 아이디 
		String board_cateId = service.board_cateId(board_cateloginId);
		model.addAttribute("board_cateId", board_cateId);
		return "boardWrite";
	}
	
	//글쓰기
	@RequestMapping(value = "/board_write.do")
	public String board_write(Model model, @RequestParam HashMap<String, String> params, HttpSession session) {
		 
		logger.info("session loginId" + session.getAttribute("loginId"));
		
		/*
		 * String board_category = params.get("board_category");
		 * logger.info("카테고리? {}",params.get("board_category"));
		 */
		
		model.addAttribute("board_category", params.get("board_category"));
		
		String board_cateloginId = (String) session.getAttribute("loginId");
		//카테고리 아이디 
		String board_cateId = service.board_cateId(board_cateloginId);
		model.addAttribute("board_cateId", board_cateId);
		
		logger.info("글쓰기 요청");
		logger.info("param : {}",params);
		
		service.write(params);
		

		return "boardList";
	}
	
	//댓글쓰기
	@RequestMapping(value = "/cmt_write.do")
	public String cmt_write(@RequestParam HashMap<String, String> params, HttpSession session) {
		logger.info("params : {} " , params);
		
		String loginId = (String) session.getAttribute("loginId");
		logger.info("게시글번호 : {}", params.get("board_idx"));
		
		params.put("mb_id", loginId);
		
		logger.info("작성자 아이디? : {} " , params.get("mb_id"));
		
		service.cmt_write(params);
		
		return "redirect:/boarddetail.go?board_idx="+params.get("board_idx");
		
	}
	
	@RequestMapping(value = "/cmt_del.do")
	public String cmt_del(@RequestParam String board_idx, @RequestParam String cmt_idx, HttpSession session) {
		
		logger.info("댓글 번호 : {}", cmt_idx);
		String loginId = (String) session.getAttribute("loginId");
		
		//원본 댓글 작성자 아이디를 구해와서 세션과 비교해서 일치할 때만 DB 삭제 진행 하는 로직이 있을 것이고,

		//아니면 무조건 보내서 DB조건에서  거는 로직이 있을 것이다.
		//아이디, cmt_idx
		service.cmtdel(cmt_idx, loginId);
		
		
		
		return "redirect:/boarddetail.go?board_idx="+board_idx;

	}
	

	//신고하기
	@RequestMapping(value = "/report_reason.do")
	public String report_reason(@RequestParam HashMap<String, String> params,  HttpSession session, Model model) {
		
		String page = "redirect:/boarddetail.go?board_idx=" +params.get("report_board_idx");
		String report_board_idx = params.get("report_board_idx");
		//String rpt_boardchk = service.rpt_boardchk(report_board_idx);
		
	
		//logger.info("신고하는 게시글 번호 ", rpt_boardchk);
		//String page = "redirect:/boarddetail.go?board_idx=" +rpt_boardchk;
		
		logger.info("[게시글번호 : {} ", params.get("report_board_idx"));
		//logger.info("게시글 제목 : {} ", params.get("report_board_title")); 성민 주석
		
		//성민 추가

			//String report_board_title = params.get("report_board_title"); 성민 주석
			String loginId = (String) session.getAttribute("loginId");
			params.put("mb_id", loginId);
			params.put("board_idx", report_board_idx);
			//params.put("rp_board_title", report_board_title); 성민 주석
			
			ArrayList<ReportDTO> report_reason =  service.report_reason(params);
			model.addAttribute("report_reason", report_reason);
			page = "redirect:/boarddetail.go?board_idx=" +params.get("report_board_idx");
			
		
		
		//여기까지
		
		
		return page;
	}
	
	
	@RequestMapping (value = "/board_del.do")
	public String board_del(@RequestParam String board_idx, HttpSession session, Model model, @RequestParam String board_category) {
		
		
		String loginId = (String) session.getAttribute("loginId");
		
		model.addAttribute("board_category",board_category);
		//db에 sel날려서 찾아와야함.
		String chkId = service.chkId(board_idx);
		logger.info(chkId);
		
		String page = "boardList";

		if(chkId.equals(loginId)) {
			
			
			
			int cmtrow = service.cmt_del(board_idx);
			logger.info("삭제 댓글 수 : ", cmtrow);
			
			int reportrow = service.report_del(board_idx);
			logger.info("삭제 신고 수 : ", reportrow);
			
			int row = service.board_del(board_idx);
			logger.info("삭제 데이터 수 : ", row);
			
		} else {		
			page = "redirect:/boarddetail.go?board_idx="+board_idx;
		}
		
		/*
		String msg = "삭제에 실패했습니다.";
		if(row > 0 ) {
			msg = "삭제에 성공했습니다.";
		}
		
		
		session.setAttribute("msg", msg);
		*/
		
		return page;
	}
	
	@RequestMapping (value = "/smsend_cmt.do")
	public String smsend_cmt (@RequestParam HashMap<String, String> params, HttpSession session, Model model) {
		
		String loginId = (String) session.getAttribute("loginId");
		String sm_msg_board_idx = params.get("sm_msg_board_idx");
		String sm_msg_receiver = params.get("sm_msg_receiver");
		String sm_msg  = params.get("sm_msg");
		logger.info("params: {} ", params);
		
		
		
		//params.put("board_idx", sm_msg_board_idx);
		params.put("sender_id", loginId);
		params.put("receiver_id", sm_msg_receiver);
		params.put("msg_content", sm_msg);
		
		logger.info("loginId : ", loginId);
		logger.info("받는사람 아이디?",sm_msg_receiver);
		logger.info("보내는 msg?",sm_msg);
		
		
		ArrayList<MsgDTO> sm_msg_send = service.sm_msg_send(params);
		logger.info("sm_msg_send  ", sm_msg_send);
		model.addAttribute("sm_msg_send", sm_msg_send);
		
		
		
		return "redirect:/boarddetail.go?board_idx="+sm_msg_board_idx;
	}
}
