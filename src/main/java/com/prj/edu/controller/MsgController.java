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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prj.edu.dto.MsgDTO;
import com.prj.edu.service.MsgService;

   @Controller
   public class MsgController {
      
      Logger logger = LoggerFactory.getLogger(this.getClass());
      
      @Autowired MsgService service;
      
      @RequestMapping("/msgsend.go")
      public String receiveHistory(Model model, HttpSession session) {
         String loginId = (String) session.getAttribute("loginId");
         int msgcount = service.msgcnt(loginId);
         logger.info("안 읽은 메세지 수 : "+msgcount);
         logger.info("loginId : "+ loginId);
         model.addAttribute("num",msgcount);
         return "receiveHistory";
      }
      
      
      @RequestMapping("/msgdetail.go")
      public String msgdetail(Model model, HttpSession session, @RequestParam String msg_idx) {
         logger.info(msg_idx+" 번 쪽지 상세보기 요청");
         service.open(msg_idx);
         MsgDTO dto = service.msgdetail(msg_idx);
         String name = (String) session.getAttribute("loginId");
         if(name.equals(dto.getSender_id())) {
            logger.info(dto.getSender_id());
            model.addAttribute("msg", "hidden");
         }else {
            logger.info("다름");
            model.addAttribute("msg", "nothidden");
         }
         model.addAttribute("dto", dto);
         return "msgdetail";
      }
      
      
      @RequestMapping("/sendHistory.go")
      public String sendHistory(Model model) {
         return "sendHistory";
      }
      
      
      @RequestMapping("/msgsendlist.ajax")
      @ResponseBody
      public HashMap<String, Object> msgsendlist(HttpSession session, @RequestParam HashMap<String, String> params) {
         logger.info("리스트 요청 : {}",params);
         String loginId = (String) session.getAttribute("LoginId");
         logger.info("로그인 아이디 : "+loginId);
         return service.msgsendlist(params);
      }
      
      
      @RequestMapping("/msgreceivelist.ajax")
      @ResponseBody
      public HashMap<String, Object> msgreceivelist(Model model, @RequestParam HashMap<String, String> params) {
         logger.info("리스트 요청 : {}",params);
         return service.msgreceivelist(params);
      }
      
      
      @RequestMapping("/senddelete.ajax")
      @ResponseBody
      public HashMap<String, Object> senddelete(HttpSession session,
            @RequestParam(value="delList[]") ArrayList<String> delList){
      HashMap<String, Object> map = new HashMap<String, Object>();
      logger.info("삭제 요청 : "+delList);
         int cnt = service.senddelete(delList);
         map.put("msg", delList.size()+" 개 중 "+cnt+" 개 삭제 완료");   
      return map;
      }
      
      
      @RequestMapping("/receivedelete.ajax")
      @ResponseBody
      public HashMap<String, Object> receivedelete(HttpSession session,
            @RequestParam(value="delList[]") ArrayList<String> delList){
      HashMap<String, Object> map = new HashMap<String, Object>();
      logger.info("삭제 요청 : "+delList);
         int cnt = service.receivedelete(delList);
         map.put("msg", delList.size()+" 개 중 "+cnt+" 개 삭제 완료");
      return map;
      }
      
      
      @RequestMapping("/send.go")
      public String sendForm(Model model, HttpSession ssesion, @RequestParam String msg_idx) {
         logger.info(msg_idx+" 에게 쪽지 보내기");
         MsgDTO dto = service.sendForm(msg_idx);
         model.addAttribute("dto", dto);
         return "send";
      }
      
      
      @RequestMapping(value = "/send.do")
      public String send(HttpSession session, Model model,
            @RequestParam HashMap<String, String> params) {
         String page = "receiveHistory";
         logger.info("params : {}", params);
         if(service.send(params) == true) {
            page = "receiveHistory";
         }
         return page;
      }
      
      
   }