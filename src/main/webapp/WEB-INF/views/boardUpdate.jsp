<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="resources/css/common.css" type="text/css">
<style>
input[readonly] {border:1px solid black; background-color:#dfdfdf;}
 table{
   width:1200px;
   margin: 0 auto;
   padding: 5px;
   border: 1px solid gray;
   border-collapse: collapse;
   }
    h3 {
   font-size: 24px;
   color: #303030;
   font-weight: bold;
   text-align: center;
   margin-top: 30px;
   margin-bottom: 30px;
   }
   
   th {
   text-align: center;
   background-color: rgb(244, 244, 244);
   }
   
    #board_write,
    #back {
	background-color: #2980b9;
	color: #fff;
	border: 1px solid #2980b9;
	border-radius: 5px;
	padding: 3px 10px;
	font-size: 14px;
	margin: 10px 5px;  
   }
   
   #button-set {
   	text-align: center;
   }
  
  

</style>
</head>
<body>
<jsp:include page="./commons/smnav.jsp" />
   <%-- <jsp:include page="./commons/loginBox.jsp"/> --%>
   <h3>수정하기</h3>
      <form action="board_update.do" method="post">
         <table class="board">
            <tr>
               <th>제목</th>
               <td>
                  <input type="hidden" name="board_idx" value="${dto.board_idx}"/>
                  <input type="text" name ="board_title" value="${dto.board_title}" maxlength="30" required />
               </td>
            </tr>
            <tr>
               <th>작성일</th>
               <td>
                  <input type="text" name="board_date" value="${dto.board_date}" readonly/>            
               </td>
            </tr>
            <tr>
               <th>작성자</th>
               <td><input type="text" name="mb_id" value="${dto.mb_id}" readonly/></td>
            </tr>
            <tr>
               <th>내용</th>
               <td>
                  <textarea type="text" name ="board_content" value="${dto.board_content}" maxlength="500" required >${dto.board_content}</textarea>
               </td>
            </tr>
         </table>
         <div id="button-set">
	         <input id="board_write" type="submit" value ="저장"/> <%--  onclick="location.href='datail.go?idx=${dto.board_idx}'" --%>
	         <input id="back" type="button" value ="돌아가기" onclick="location.href='boarddetail.go?board_idx=${dto.board_idx}'"/>
         </div>
      </form>
</body>
<script>
/* var msg = "${msg}";
if(msg != "") {
   alert(msg);
} */
</script>
</html>