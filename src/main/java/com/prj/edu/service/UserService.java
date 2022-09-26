package com.prj.edu.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.prj.edu.dao.BoardDAO;
import com.prj.edu.dao.QnaDAO;
import com.prj.edu.dao.RecruitDAO;
import com.prj.edu.dao.UserDAO;
import com.prj.edu.dto.BoardDTO;
import com.prj.edu.dto.EduDTO;
import com.prj.edu.dto.QnaDTO;
import com.prj.edu.dto.UserDTO;

@Service
public class UserService {
	@Autowired UserDAO dao;
	@Autowired QnaDAO qnadao;
	@Autowired BoardDAO boarddao;
	@Autowired RecruitDAO recruitdao;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public String login(String mb_id, String mb_pass) {
		logger.info("로그인이 잘 들어왔나?");
		return dao.login(mb_id,mb_pass);
	}

	public HashMap<String, Object> join(HashMap<String, Object> params) {
		logger.info("회원가입 이동이 되나?");
		HashMap<String, Object> map = new HashMap<String, Object>();
		int row = dao.join(params);
		boolean msg = row>0?true:false;
		map.put("msg", msg);
		return map;
	}

	public String idsearch(String mb_email, String mb_tel) {
		logger.info("아이디 찾기가 잘 되는가?");
		return dao.idsearch(mb_email, mb_tel);
	}

	public String pwsearch(String mb_id, String mb_email, String mb_tel) {
		logger.info("비밀번호 찾기가 잘 되는가?");
		return dao.pwsearch(mb_id,mb_email,mb_tel);
	}

	public int newpass(String mb_pass1) {
		logger.info("비밀번호 변경이 잘 되는가?");
		return dao.newpass(mb_pass1);
	}

	public HashMap<String, Object> overlay(String chkId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String ID = dao.overlay(chkId);
		boolean msg = ID==null?false:true;
		map.put("msg",msg);
		return map;
	}

   public int cnt(String mb_id, int i) {
	      logger.info("정지된 회원해제?");
	      return dao.cnt(mb_id,i);
	   }

	public HashMap<String, Object> list(HashMap<String, String> params) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("일반 회원 페이지");
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
		ArrayList<UserDTO> list = dao.list(cnt, offset);

		map.put("list", list);
		return map;
	}

	   public HashMap<String, Object> edulist(HashMap<String, String> params) {
		      HashMap<String, Object> map = new HashMap<String, Object>();
		      logger.info("교육기관 회원 페이지");
		      int cnt = Integer.parseInt(params.get("cnt"));
		      int page = Integer.parseInt(params.get("page"));
		      logger.info("보여줄 페이지 : " + page);
		      int allCnt = dao.eduCount();
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
		      ArrayList<UserDTO> list = dao.edulist(cnt, offset);
		      logger.info("{} ",list);
		      map.put("list", list);
		      return map;
		   }

	public HashMap<String, Object> mslist(HashMap<String, String> params) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("Q&A 답변 페이지");
		int cnt = Integer.parseInt(params.get("cnt"));
		int page = Integer.parseInt(params.get("page"));
		logger.info("보여줄 페이지 : " + page);
		int msCnt = qnadao.msCount();
		logger.info("msCnt:" + msCnt);
		int pages = msCnt%cnt > 0 ? (msCnt/cnt)+1 : (msCnt/cnt);
		logger.info("pages : " + pages);
		if(page > pages) {
			page = pages;
		} 
		map.put("pages", pages);      //만들 수 있는 최대 페이지 수
		map.put("currPage", page); //현재 페이지
		int offset = (page-1) * cnt;
		logger.info("offset : " + offset);            
		ArrayList<QnaDTO> list = qnadao.mslist(cnt, offset);
		logger.info("{}, ",list);
		map.put("list", list);

		return map;
	}

	public UserDTO userDetail(String mb_id) {
		UserDTO dto = null;
		logger.info(mb_id + "일반회원 상세보기 서비스 요청");
		dto = dao.userDetail(mb_id);

		return dto;
	}

	public UserDTO eduDetail(String mb_id) {
		UserDTO dto = null;
		logger.info(mb_id + "교육기관회원 상세보기 서비스 요청");
		dto = dao.eduDetail(mb_id);

		return dto;
	}

	public UserDTO userInfo(String id) {
		UserDTO dto = null;
		logger.info(id + "개인정보 조회 서비스 요청");
		dto = dao.userInfo(id);

		return dto;
	}
	public void delete(String mb_id) {
		logger.info("탈퇴 서비스 요청");
		dao.delete(mb_id);
	}

	public void eduInfo(Model model,String id) {
        
        logger.info(id + "개인정보 조회 서비스 요청");
       
        
        UserDTO dto = dao.eduInfo(id);
        ArrayList<EduDTO> list = dao.EduPhotoList(id);//photo 정보 가져오기
        
        
        model.addAttribute("dto", dto);
        model.addAttribute("list", list);
        
        logger.info("에듀회원 상세정보");
     }
	
		public UserDTO eduInfo(String id) {
	      UserDTO dto = null;
	      logger.info(id + "개인정보 조회 서비스 요청");
	      dto = dao.eduInfo(id);

	      return dto;
	   }

	public HashMap<String, Object> userupdate(HashMap<String, Object> params) {
		logger.info("{} : 수정하기 확인");
		HashMap<String, Object> map = new HashMap<String, Object>();
		int num = dao.userupdate(params);
		map.put("msg",num);
		return map;
	}

	public int userCategory(String loginId) {
		return dao.userCategory(loginId);
	}

	public HashMap<String, Object> reportList(HashMap<String, String> params) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("신고 회원 리스트 페이지");
		int cnt = Integer.parseInt(params.get("cnt"));
		int page = Integer.parseInt(params.get("page"));
		logger.info("보여줄 페이지 : " + page);
		int rpCnt = boarddao.reportCount();
		logger.info("rpCnt:" + rpCnt);
		int pages = rpCnt%cnt > 0 ? (rpCnt/cnt)+1 : (rpCnt/cnt);
		logger.info("pages : " + pages);
		if(page > pages) {
			page = pages;
		} 
		map.put("pages", pages);      //만들 수 있는 최대 페이지 수
		map.put("currPage", page); //현재 페이지
		int offset = (page-1) * cnt;
		logger.info("offset : " + offset);            
		ArrayList<BoardDTO> list = boarddao.reportList(cnt, offset);
		logger.info("{} ",list);
		map.put("list", list);
		return map;
	}

	public String loginok(String mb_id, int num) {
		String page = "login";
		switch(num) {
		case 1: page = "redirect:/userInfo.go?mb_id="+mb_id;
		break;
		case 2: page = "redirect:/eduInfo.go?mb_id="+mb_id;
		break;
		case 3: page = "userList";
		break;
		case 4: page = "userList";
		break;  
		default : page = "login";
		break;
		}
		return page;
	}

   public HashMap<String, Object> blackList(HashMap<String, String> params) {
	      HashMap<String, Object> map = new HashMap<String, Object>();
	      logger.info("정지 회원 리스트 페이지");
	      int cnt = Integer.parseInt(params.get("cnt"));
	      int page = Integer.parseInt(params.get("page"));
	      logger.info("보여줄 페이지 : " + page);
	      int blCnt = dao.blackCount(); //bl = black
	      logger.info("blCnt:" + blCnt);
	      int pages = blCnt%cnt > 0 ? (blCnt/cnt)+1 : (blCnt/cnt);
	      logger.info("pages : " + pages);
	      if(page > pages) {
	         page = pages;
	      } 
	      map.put("pages", pages);      //만들 수 있는 최대 페이지 수
	      map.put("currPage", page); //현재 페이지
	      int offset = (page-1) * cnt;
	      logger.info("offset : " + offset);            
	      ArrayList<UserDTO> list = dao.blackList(cnt, offset);
	      logger.info("{} ",list);
	      map.put("list", list);
	      return map;
	   }
   
   public HashMap<String, Object> usqnalist(HashMap<String, String> params, String name) {
	      HashMap<String, Object> map = new HashMap<String, Object>();
	      logger.info("작성한 Q&A 확인 페이지");
	      int cnt = Integer.parseInt(params.get("cnt"));
	      int page = Integer.parseInt(params.get("page"));

	      logger.info("로그인 아이디 요청"+name);
	      int allCnt = dao.usallCount(name);

	      int pages = allCnt%cnt > 0 ? (allCnt/cnt)+1 : (allCnt/cnt);
	      if(page > pages) {
	         page = pages;
	      } 
	      map.put("pages", pages);      //만들 수 있는 최대 페이지 수
	      map.put("currPage", page); //현재 페이지
	      int offset = (page-1) * cnt;
	      logger.info("offset : " + offset);            
	      ArrayList<QnaDTO> list = qnadao.usqnalist(cnt, offset, name);

	      map.put("list", list);
	      return map;
	   }

	   public String userStop(HashMap<String, Object> params) {
	      logger.info("정지 서비스 잘 작동하는지");
	      String msg = "수정에 실패하였습니다.";
	      if(dao.userStop(params)>0&&dao.mbStop(params)>0) {
	         msg = "수정에 성공하였습니다.";
	      }
	      return msg;
	   }

	   public String stopdate(String loginId) {
	      return dao.stopdate(loginId);
	   }

	   public UserDTO userdto(String loginId) {
	      return dao.userdto(loginId);
	   }

	   public int cate(String mb_id, int i) {
	      return dao.cate(mb_id,i);
	   }
	   public void blind(String board_idx, String blindYn) {
	         boarddao.blind(board_idx, blindYn);
	         //boarddao.report_state(report_idx, report_state); 0704 전성민 주석
	         //logger.info("업데이트 요청한 report : " + report_idx + " / " + report_state);
	      }
	   //성민
	   public HashMap<String, Object> boardHistoryajax(HashMap<String, String> params, String name) {
	      HashMap<String, Object> map = new HashMap<String, Object>();
	      logger.info("작성한 게시글 서비스 요청");
	      int cnt = Integer.parseInt(params.get("cnt"));
	      int page = Integer.parseInt(params.get("page"));

	      int allCnt = boarddao.bht_allCount(name);
	      int pages = allCnt%cnt > 0 ? (allCnt/cnt)+1 : (allCnt/cnt);
	      if(page > pages) {
	         page = pages;
	      } 
	      map.put("pages", pages);      //만들 수 있는 최대 페이지 수
	      map.put("currPage", page); //현재 페이지
	      int offset = (page-1) * cnt;
	      logger.info("offset : " + offset);            
	      ArrayList<BoardDTO> list = boarddao.boardHistoryajax(cnt, offset, name);

	      map.put("list", list);
	      return map;
	   }

	   public int realtime(String loginId) throws Exception {
	      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	      String nowtime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	      String idtime = dao.stopdate(loginId);
	      Date date = new Date(dateFormat.parse(nowtime).getTime()); 
	      Date today = new Date(dateFormat.parse(idtime).getTime());
	      return date.compareTo(today);
	   }
	   
	   // 정현민 추가
	   public HashMap<String, Object> r_HistoryList(HashMap<String, String> params) {
		   HashMap<String, Object> map = new HashMap<String, Object>();
		      
		   	  String id = params.get("id");
		      int cnt = Integer.parseInt(params.get("cnt"));
		      int page = Integer.parseInt(params.get("page"));
		      logger.info("보여줄 페이지 : " + page);
		      
		      int allCnt = recruitdao.allCount2(id);
		      
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
		      
		    	  ArrayList<BoardDTO> list = recruitdao.recruitHistoryList(cnt, offset, id);
		    	  map.put("list", list);
		      
		      return map;
		   }

	public String findCateId(String id) {
		logger.info("카테아이디 찾기 요청 : " + id);
		return qnadao.findCateId(id);
	}
	
	public HashMap<String, Object> EduJoin(HashMap<String, String> params) {
	      logger.info("edu 회원 가입");
	      HashMap<String, Object> map = new HashMap<String, Object>();
	      int row = dao.EduJoin(params);
	      boolean msg = row>0?true:false;
	      map.put("msg", msg);
	      return map;
	   }

	public int joEdu(MultipartFile[] photo_name, HashMap<String, String> params) {
        EduDTO dto = new EduDTO();
         logger.info("사진 : " + photo_name);
         for(MultipartFile photo:photo_name) {
            String photo_original = photo.getOriginalFilename(); //3-1파일명 추출
            logger.info("photo name: " + photo.getOriginalFilename());
            if(!photo.getOriginalFilename().equals("")) {
               logger.info("업로드 진행");
               String ext = photo_original.substring(photo_original.lastIndexOf(".")).toLowerCase();
               String photo_copy = System.currentTimeMillis() + ext;
               logger.info(photo_original + photo_copy );   
               try {
                  byte[] arr =photo.getBytes();
                  Path path = Paths.get("C:\\STUDY\\Project_ALLIT\\src\\main\\webapp\\resources\\images/" + photo_copy);
                  Files.write(path, arr);
                  logger.info(photo_copy + "save ok");
               } catch (IOException e) {
                     e.printStackTrace();
               }
               dto.setPhoto_name(photo_copy);
               logger.info(photo_copy + "save ok");   
            }  
         }         
          dto.setEdu_name(params.get("edu_name"));
          dto.setRep_name(params.get("rep_name"));
          dto.setMb_id(params.get("mb_id"));
         return dao.EduJoin2(dto);
      }

	   
}
