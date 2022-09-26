<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
   href="http://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
   rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
   src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type="text/javascript"
   src="resources/js/jquery.twbsPagination.js"></script>
<!-- 페이징 처리 -->
</head>
<body>
   <style>
* {
   margin: 0;
   padding: 0;
}

body {
   font-family: sans-serif;
}

li {
   list-style: none;
}

a {
   text-decoration: none;
}

img {
   border: 0;
}

* {
   box-sizing: border-box;
   /* 길이 계산을 편하게 하기위함. box-sizing에 관한 설명은 아래 링크를 참고해주세요 */
}

body {
   margin: 0;
   font-family: sans-serif;
}

a {
   text-decoration: none; /* 기본 밑줄 제거 */
   color: #888;
}

a:hover {
   text-decoration: underline; /* 마우스 커서가 올라갔을 때 밑줄 생성*/
}

#page {
	max-width: 1200px;
	width: 100%;
	margin: 0 auto;
}

#leftnav {
	position:absolute;
	max-width: 170px;
	width: 100%;
	height: 550px;
	background-color: #E6EDF5;
	font-size: 18px;
	text-align: center;
}

#leftnav #leftli li {
	margin: 30px 0;
	padding: 8px;
    color: black;
}

#leftnav #leftli li a {
    color: rgb(100, 100, 100);
}

#table {
   position:relative;
   top: 40px;
   left: 16%;
}

.info, th, td {
	border: 1px solid gray;
	text-align: center;
	padding: 5px 10px;
}

.info {
	width: 80%;
}

th {
	background-color: rgb(244, 244, 244);
}

td {
	padding: 5 10px;
}

#pagePerNum {
	margin: 10px 0 10px 0;
}

.container {
	margin-top:10px;
	position:absolute;
	right: 10%;
}


</style>
<jsp:include page="./commons/smnav.jsp"/>
</head>
<body>
<div id="page">
   <form action="blackList" method="post">
		<div id="leftnav">
			<ul id="leftli">
				<li style="font-weight: bold; font-size: 20px">관리자 페이지</li>
				<li><a href="/vslogin.go">회원정보조회</a></li>
				<li><a href="/msdetail.go">Q&A 답변</a></li>
				<li><a  href="/reportList.go">회원신고관리</a></li>
				<li style="background-color: #6ba8dd"><a style="color: #fff" href="/blackList.go">정지회원관리</a></li>
			</ul>
		</div>
		
		
	<div id="table">
	<p>관리자페이지 > 정지회원관리</p>	
      <select id="pagePerNum">
         <option value="5">5개씩</option>
         <option value="10">10개씩</option>
         <option value="15">15개씩</option>
         <option value="20">20개씩</option>
      </select>
      <table class="info">
         <thead>
            <tr>
               <th>정지번호</th>
               <th>ID</th>
               <th>이름</th>
               <th>정지 기간</th>
               <th>사유</th>
               <th>담당관리자</th>
            </tr>
         </thead>

         <tbody id="list">

         </tbody>
   </table>
   <div class="container">
      <nav arial-label="Page navigation" style="text-align: center">
         <ul class="pagination" id="pagination"></ul>
      </nav>
   </div>
   </form>
   </div>
</div>
</body>

<script>
   var currPage = 1;

   listCall(currPage);
   //페이징 처리
   $('#pagePerNum').on('change', function() {
      console.log("currPage: " + currPage);
      //페이지당 보여줄 수 변경시 계산된 페이지 적용이 안된다.(플러그인의 문제)
      //페이지당 보여줄 수 변경시 기존 페이지 요소를 없애고 다시 만들어 준다.
      $("#pagination").twbsPagination('destroy');
      listCall(currPage);

   });

   //리스트 call 과정
   function listCall(page) {

      var pagePerNum = $('#pagePerNum').val();
      console.log("param page : " + page);

      $.ajax({
         type : 'GET',
         url : 'blackList.ajax',
         data : {
            cnt : pagePerNum,
            page : page,
            
         },
         dataType : 'JSON',
         success : function(data) {
            console.log(data);
            drawList(data.list,data.currPage);
            currPage = data.currPage;
            $("#pagination").twbsPagination({
               startPage : data.currPage,
               totalPages : data.pages,
               visiblePages : 5,
               onPageClick : function(e, page) {   
                  console.log(page);
                  currPage = page;
                  listCall(page);
               }
            });

         },
         error : function(e) {
            console.log(e);//
         }
      });
   }
   function drawList(list,start) {
      
      var content = '';
      //console.log(start);
      var num = ((start-1) * 5)+1;
      console.log(num);
      list.forEach(function(item) {
         //var date = new Date(item.report_date);
         console.log(item);
         content += '<tr>';
         content += '<td>'+(num++)+'</td>';
         content += '<td><a href="user/detail.go?mb_id=' + item.mb_id+ '">' + item.mb_id+ '</a></td>';
         content += '<td>' + item.mb_name + '</td>';
         content += '<td>' + item.stop_start+ ' ~ ' + item.stop_end + '</td>';
         content += '<td>' + item.stop_reason + '</td>'; 
         content += '<td>' + item.stop_manager + '</td>';
         
         content += '</tr>';
      });
      $('#list').empty();
      $('#list').append(content);
   }
   function msg() {
      alert("현재 화면입니다.");
   }
</script>

</html>