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

import com.prj.edu.dto.QnaDTO;
import com.prj.edu.service.QnaService;


@Controller
public class QnaController {
   Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired QnaService service;

   @RequestMapping(value = "/qna.go", method = RequestMethod.GET)
   public String home(Model model, HttpSession session) {
	    String id = (String)session.getAttribute("loginId");
	   
	    String page = "qnalist";
	    
		if(id == null) {
			model.addAttribute("msg", "로그인이 필요한 서비스입니다.");
			model.addAttribute("url", "/login.go");
			page = "alert";
		}
      return page;
   }
   
   //상세보기 이동
   @RequestMapping(value = "/qnadetail.go")
   public String detail(Model model, HttpSession session, @RequestParam String qna_idx) {
      logger.info("상세보기 요청 : " + qna_idx);   
      QnaDTO dto = service.detail(qna_idx);
      model.addAttribute("dto", dto);
      return "qnadetail";
   }
   
   @RequestMapping(value = "/myqnadetail.go", method = RequestMethod.GET)
   public String myqnadetail(Model model, HttpSession session, @RequestParam String qna_idx) {
      logger.info("상세보기 요청 : " + qna_idx);   
      QnaDTO dto = service.detail(qna_idx);
      
      String id = (String)session.getAttribute("loginId");
      String cateId = service.findCateId(id);
      
      String page = "myqnadetail";
      
      if(cateId.equals("교육기관 회원")) {
    	  page = "myqnadetailEdu";
      }
      
      model.addAttribute("dto", dto);
      return page;
   }


   //글쓰기 페이지 이동
   @RequestMapping(value = "/qnaWrite.go")
   public String qnaWrite(Model model) {
      logger.info("게시글 작성 페이지 ");
      return "qnaWrite";
   }

   //리스트 페이지 이동
   @RequestMapping(value = "/qnaList.go", method = RequestMethod.GET)
   public String qnaList(Model model) {
      logger.info("리스트 페이지 이동");
      return "qnalist";
   }

   //qna 답변 페이지 이동
   @RequestMapping(value = "/answer.go")
   public String qnaanswer(Model model, @RequestParam String qna_idx) {
      logger.info("idx : " + qna_idx);
      QnaDTO dto = service.dbdetail(qna_idx);
      model.addAttribute("dto", dto);
      return "qnaanswer";
   }   



   //리스트 페이징
   @RequestMapping("list.ajax")
   @ResponseBody
   public HashMap<String, Object> list(@RequestParam HashMap<String, String> params) {
      logger.info("리스트 요청!!!!!!!!! : {}",params);
      return service.list(params);
   }


   @RequestMapping(value = "/write.do")
   public String write(Model model, HttpSession session, @RequestParam HashMap<String, String> params) {
      String page = "writeForm";
      String name = (String) session.getAttribute("loginId");
      params.put("mb_id",name);
      params.put("qna_answer_chk","0");
      if(service.write(params) == true) {
         page = "redirect:/qnaList.go";
      }else {
         model.addAttribute("msg", "글쓰기에 실패 했습니다.");
      }
      return page;
   }
   @RequestMapping(value = "/answer.do", method = RequestMethod.POST)
   public String answer(@RequestParam HashMap<String, Object> params) {
      service.answer(params);

      return "redirect:/msdetail.go"; // 매니저 qna 리스트 페이지로 변경 필요
   }
}

