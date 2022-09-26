<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<style>
input[readonly] {border:1px solid black; background-color:#dfdfdf;}
   /* 출처 GDJ48 개발자 : 동 복 킴   시작 */
   .modal-bg{display:none;position:fixed;top:0;right:0;bottom:0;left:0;width:100%;height:100%;z-index:100;background:rgba(0,0,0,0.7);}
   .modal{display:none;position:absolute;top:calc(50% - 90px);left:calc(50% - 180px);background-color:#fff;border-radius:10px;width:360px;height:300px; z-index:999;padding:20px 20px 30px 20px;box-sizing:border-box;}
   .modal > #modal-close{float:right;margin-bottom:10px;font-size:16px;font-style:normal;color:#0070c0;cursor:pointer;}
   .modal > form{margin-top:20px;clear:both;}
   .modal button[type="submit"]{margin:0 auto;padding:10px;font-weight:700;cursor:pointer;border:none;border-radius:5px;color:#fff;background-color:#0070c0;}


   .cmt_modal-bg{display:none;position:fixed;top:0;right:0;bottom:0;left:0;width:100%;height:100%;z-index:100;background:rgba(0,0,0,0.7);}
   .cmt_modal{display:none;position:absolute;top:calc(50% - 90px);left:calc(50% - 180px);background-color:#fff;border-radius:10px;width:360px;height:400px; z-index:999;padding:20px 20px 30px 20px;box-sizing:border-box;}
   .cmt_modal > #cmt_modal-close{float:right;margin-bottom:10px;font-size:16px;font-style:normal;color:#0070c0;cursor:pointer;}
   .cmt_modal > form{margin-top:20px;clear:both;}
   .cmt_modal button[type="submit"]{margin:0 auto;padding:10px;font-weight:700;cursor:pointer;border:none;border-radius:5px;color:#fff;background-color:#0070c0;}
   /* 출처 GDJ48 개발자 : 동 복 킴   끝 */
   
   h3 {
   font-size: 24px;
   color: #303030;
   font-weight: bold;
   text-align: center;
   margin-top: 30px;
   margin-bottom: 30px;
   }
   
   table{
   width:1200px;
   margin: 0 auto;
   padding: 5px;
   border: 1px solid gray;
   border-collapse: collapse;
   }
   
   th {
   text-align: center;
   background-color: rgb(244, 244, 244);
   }
   
   #bottom {
   width: 1200px;
   margin: 0 auto;
   margin-top: 30px;
   }
   
   #button-set {
   text-align: center;
   margin-top: 20px;
   }
   
   #back,
   #update,
   #board_del {
   background-color: #2980b9;
   color: #fff;
   border: 1px solid #2980b9;
   border-radius: 5px;
   padding: 3px 10px;
   font-size: 14px;
   }
   
   #cmt-box {
      background-color: #2980b9;
   color: #fff;
   border: 1px solid #2980b9;
   border-radius: 5px;
   padding: 3px 10px;
   font-size: 12px;
   }
   
   #report-box {
      display: inline-block;
      margin-left: 1250px;
      margin-top: 10px;
      border: 1px solid black;
      padding: 5px 10px;
    background: #e26853;
   border: 1px solid #e26853;
   border-radius: 5px;
   font-size: 14px;
   }
   
   #report-box1 {
    color: #fff;
    text-decoration: none;
   }
   
   #cmtList {
    margin-top: 10px;
   }
   
   #cmt_chkedu {
    margin-top: 10px;
   }
   
   #cmt_chkedu,
   #cmt_chkedu td {
      border:none;
   }
   
   #button1 {
   	background-color: #2980b9;
	color: #fff;
	border: 1px solid #2980b9;
	border-radius: 5px;
	padding: 3px 10px;
	font-size: 12px;
	margin-left: 1%;
   }
   
   #blind {
   	margin-top: 3%;
   }
   
</style>
</head>
<body>
   <jsp:include page="./commons/smnav.jsp" />
   <h3>상세보기</h3>
   <table class="board">
      <tr>
         <th>글번호</th>
         <td>${dto.board_idx}</td>
      </tr>
      <tr>
         <th>제목</th>
         <td>${dto.board_title}</td>
      </tr>
      <tr>
         <th>작성일</th>
         <td>${dto.board_date}</td>
      </tr>
      <tr>
         <th>작성자</th>
         <td><a href="#" class="cmt_senderid"><i class="xi-trash"></i>${dto.mb_id}</a></td>
      </tr>
      <tr>
         <th>조회수</th>
         <td>${dto.board_hits}</td>
      </tr>
      <tr>
         <th>내용</th>
         <td>${dto.board_content}</td>
      </tr>
   </table>
        
     <!-- <input type="button" value="신고하기" onclick="location.href='boardreport.do?'" />  -->
     <div id="report-box"><a id="report-box1" href="#" class="report_modal-open"><i class="xi-trash"></i>신고</a></div>
     
   <!-- 출처 GDJ48 개발자 : 동 복 킴-->
   <div class="modal-bg"></div>
   <div class="modal">
      <i id="modal-close" class="close">X</i>
      <p><i class="xi-info-o"></i> <strong>신고 사유</strong>를 선택해주세요.</p>
      <form action="report_reason.do" method="post">
         <input type="hidden" name="report_board_idx" value="${dto.board_idx}">
         
         <input type="hidden" name="report_state" value="미처리">
         <input type="radio" name="report_reason" value="욕설" required><p>욕설</p>
         <input type="radio" name="report_reason" value="개인정보 유출 위험" required><p>개인정보 유출 위험</p>
         <input type="radio" name="report_reason" value="음란/유해" required><p>음란/유해</p>
         <input type="radio" name="report_reason" value="허위광고" required><p>허위광고</p>
         <input type="radio" name="report_reason" value="도배" required><p>도배</p>
         <div><button type="submit"><i class="xi-check-circle"></i> 확인</button></div>
      </form>
   </div>
   
   <!-- 쪽지 보내기 모달 -->
   <div class="cmt_modal-bg"></div>
   <div class="cmt_modal">
      <i id="cmt_modal-close" class="close">X</i>
      <p><i class="xi-info-o"></i> <strong>쪽지를 </strong>입력해주세요</p>
      <form action="smsend_cmt.do" method="post">
         <input type="text" name="sm_msg_receiver" value="${dto.mb_id}" readonly>
         <input type="hidden" name="sm_msg_board_idx" value="${dto.board_idx}"/>
         <!-- <input type="text" name="sm_msg" value="" required> -->
         <textarea name="sm_msg"></textarea>
         
         <div><button type="submit"><i class="xi-check-circle"></i> 확인</button></div>
      </form>
   </div>
   
   <div id="bottom">
   댓글목록 : ${cmtsize}
   <table id="cmtList">
      <c:forEach items="${cmtlist}" var="cmt">
         <tr>
            <td><a href="#" class="cmt_senderid" ><i class="xi-trash"></i>${cmt.mb_id}</a></td>
            <td>${cmt.cmt_content}</td>
            <td>${cmt.cmt_date}</td>
            <c:if test="${sessionScope.loginId eq cmt.mb_id}">
            <td id="cmtName1">
               <a href="cmt_del.do?board_idx=${dto.board_idx}&cmt_idx=${cmt.cmt_idx}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
            </td>
            </c:if>
               
         </tr>
      </c:forEach>
   </table>

   <form action="cmt_write.do" method="post">
      <input type="hidden" name="board_idx" value="${dto.board_idx}" />
      <table id="cmt_chkedu">
         <tr>
            <td><input type="text" name="cmt_content" maxlength="250" required/></td>
            <td><button id="cmt-box" type="submit" >댓글입력</button></td>
         </tr>
     </table>
   </form>
   <div id="button-set">
   <input id="back" type="button" value="돌아가기" onclick="location.href='boardlist.go?board_category=${dto.board_category}'" />
    <c:if test="${sessionScope.loginId eq dto.mb_id}">
        <input id="update" type="button" value="수정" onclick="location.href='boardUpdate.go?board_idx=${dto.board_idx}'" />
     </c:if>
     <c:if test="${sessionScope.loginId eq dto.mb_id}">
       <input id="board_del" type="button" value="삭제" onclick="location.href='board_del.do?board_idx=${dto.board_idx}&board_category=${board_category}'" />
     </c:if>
   </div>
   
   <!-- 0630추가됨 -->
         <%-- <tr id="blind">
            <c:if test = "${cateId ne  }"
            <td><input class="blind_button" type="radio" name="blind" value="do" <c:if test="${dto.board_blind eq 'true'}">checked</c:if>/>숨김 처리</td>
            <td><input class="blind_button" type="radio" name="blind" value="cancel" <c:if test="${dto.board_blind eq 'false'}">checked</c:if>/>숨김 해제</td>
           </tr> --%>
            <!-- 여기까지 -->
      <form action="blind.do" method="post">
         <div id="blind">
            <c:if test="${cateId ne '일반회원' and cateId ne '교육기관 회원'}">
            <%-- <c:if test="${cateId ne '교육기관 회원'}"> --%>
               <label><input class="blind_button" type="radio" name="blind" value="do"
                  <c:if test="${dto.board_blind eq 'true'}">checked</c:if> /> 숨김 처리</label>
               <label><input class="blind_button" type="radio" name="blind" value="cancel"
                  <c:if test="${dto.board_blind eq 'false'}">checked</c:if> /> 숨김 해제</label>
               <input type="hidden" name="board_idx" value="${dto.board_idx}" />
               <input id="button1" type="submit" id="report_save" value="저장" />
            </c:if>   
            <%-- </c:if> --%>
         </div>
      </form>



   <!-- 김동훈 -->
            <%-- <input type="hidden" name="report_idx" value="${dto.report_idx}" />

            <select name="report_state" id="changeTest" name="changeTest"
               onchange="">
               <option value="미처리"
                  <c:if test="${dto.report_state.equals('미처리')}">selected</c:if>>미처리</option>
               <option value="처리중"
                  <c:if test="${dto.report_state.equals('처리중')}">selected</c:if>>처리중</option>
               <option value="처리완료"
                  <c:if test="${dto.report_state.equals('처리완료')}">selected</c:if>>처리완료</option>
            </select> --%>
   <!-- ㅇㄱㄲㅉ -->
   </div>
</body>
<script>
/* 
$('.cmt_del > a').on('click', function (e) {
   console.log("클릭잘됨 ");
   if (!confirm("정말 삭제하시겠습니까?")) {
       e.preventDefault();   
    }
   
})
*/
//신고 모달창 스크립트
$(".report_modal-open").on('click',function(e){
   $(".modal").show();
   $(".modal-bg").show();
   //e.preventDefault();
})

 $("#modal-close").on('click',function(e){
   $(".modal").hide();
   $(".modal-bg").hide();
})

$("#report_submit").on('click', function(e){
   alert("신고가 정상적으로 완료됐습니다.");
});
 /*
 $('input[name="blind"]').on('click', function(){
     location.href = "blind.do?board_idx=${dto.board_idx}&blind=" + $(this).val();
  });
 */



/*let changeTest = '${changeTest}';
//value값으로 설정
$("#changeTest").val(changeTest).prop("selected", true);
  */
 
 



/* var cateName = "${cateId}";

if(cateName != "매니저 회원" || cateName != "관리자") {
   $(".blind_button").empty();
} */

//0630 추가됨





//쪽지 모달창 스크립트
var boardcateName = "${board_cateId}";
var board_category= "${board_category}"; 
var loginId = "${loginId}";
$(".cmt_senderid").on('click',function(e){   
   $("input[name='sm_msg_receiver']").val($(this).text());
   
   if(boardcateName == "교육기관 회원"){
      alert('교육기관회원은 쪽지를 보낼 수 없습니다.');
      return false;
   }
   
   if(board_category == '공지사항'){
      alert('관리자에게는 쪽지를 보낼 수 없습니다.');
      return false;
   }
   
   if(loginId == $(this).text()){
      alert('자기 자신에게는 쪽지를 보낼 수 없습니다.');
      return false;
   }
   
   $(".cmt_modal").show();
   $(".cmt_modal-bg").show();
   //e.preventDefault();
});



 $("#cmt_modal-close").on('click',function(e){
   $(".cmt_modal").hide();
   $(".cmt_modal-bg").hide();
})

/*
var cmtmb_id = "${cmt.mb_id}";
var cmtsize = "${cmtsize}";
//댓글 삭제 자기 자신만 나오게
 if(loginId =! cmtmb_id){
   for (var i = 1; i < cmtsize; i++) {
   $("#cmtName1").hide();      
   
   }
} 
*/


//교육기관 댓글 숨기기
if(boardcateName == "교육기관 회원"){
      $("#cmt_chkedu").hide();
}


//아이디 클릭시 클릭한 아이디(수신자아이디)로 쪽지 보내기
$(".cmt_senderid").on("click", function(){
   $("input[name='sm_msg_receiver']").val($(this).text());
});




/* $(".cmt_senderid").on("click",function(){
   }
 */

 
//게시글 삭제 확인창   
 $('#board_del').on('click', function (e) {
    //console.log("클릭잘됨 ");
    if (!confirm("정말 삭제하시겠습니까?")) {
    e.preventDefault();   
    }
   
})
  
/*
var cateName = "${category}";
var id = "${sessionScope.loginId}";
   
   // 매니저 회원, 관리자는 모든 글에 대해 수정하기, 삭제하기를 할 수 있게끔 하는 기능
  if(cateName == "매니저 회원" || cateName == "관리자") {
     $("#update").show();
     $("#delete").show();
  }
  
  // 본인이 작성한 글에 대해서만 수정하기, 삭제하기 버튼을 보이게하는 기능
  if(id == "${recruit.mb_id}") {
     $("#update").show();
     $("#delete").show();
  }
  
   // 비로그인 회원이 상담신청 버튼을 눌렀을때 경고창과 함께 로그인 창으로 보내는 코드
   // 회원가입을 유도하기 위해 disabled 또는 hide 를 적용하지 않았음
  $("#cst_button").on("click",function(){ 
     if(id == "") {
        alert("로그인이 필요한 서비스입니다.");
        location.href = "/login.go";
     } else {
        location.href="/recruit/…?idx=${recruit.recruit_idx}";
     }
  });
  
   // 공고글 마감 or 본인이 작성한 공고글에 대해 상담신청을 하지 못하게 막는 기능
  if("${recruit.recruit_close}" == "false" || id == "${recruit.mb_id}") {
     $("#cst_button").attr("disabled", true); 
  }
   
   // 삭제요청을 한번 더 묻는 기능
   $("#delete").on("click",function(){
       var result = confirm('정말 삭제하시겠습니까?');
       
       if(result) {
          location.replace('/recruit/delete.do?idx=${recruit.recruit_idx}');
       }
   });
  
  
 */
    

   
    /* var cmtName = "${mb_id}";
    console.log("${mb_id}");
    if(cmtName != "${sessionScope.loginId}") {
    $("#cmtName1").data('hidden',true);    
    }
     */

$("#report_save").on('click', function(){ /* 김동훈 */
   location.href = "blind.do?board_idx=${dto.board_idx}&blind=" + $(".blind_button").val();
});
var msg = "${msg}";
if (msg != "") {
   alert(msg);
      
}
   
</script>
</html>