<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>작성한 게시글 페이지</title>
<link
   href="http://netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
   rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
   src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type="text/javascript"
   src="resources/js/jquery.twbsPagination.js"></script>
<!-- 페이징 처리 -->
<style>
* {
   margin: 0;
   padding: 0;
   box-sizing: box;
   font-family: sans-serif;
}

body {
   font-family: sans-serif;
}

li {
   list-style: none;
}

img {
   border: 0;
}

body {
   margin: 0;
}

a {
   text-decoration: none; /* 기본 밑줄 제거 */
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

.info, th, td{
	border: 1px solid gray;
	text-align: center;
	padding: 5px 10px;
}

th {
	background-color: rgb(244, 244, 244);
}

td {
	text-align: center;
}

#button1 {
	margin-top: 10px;
	background-color: #2980b9;
	color: #fff;
	border: 1px solid #2980b9;
	border-radius: 5px;
	padding: 3px 10px;
	font-size: 14px;
}

#pagePerNum {
	margin-top: 15px;
	margin-bottom: 15px;
}

.container {
	position: relative;
	right: 100px;
}
</style>
</head>
<body>
<jsp:include page="./commons/smnav.jsp" />
	<div id="page">	
	   <div id="leftnav">
	   		<ul id="leftli">
	           <li style="font-weight: bold; font-size: 20px">마이페이지</li>
	           <li><a href="/userInfo.go?mb_id=${id}">개인정보 조회</a></li>
	           <li><a href="/cstList.go">상담신청 내역</a></li>
	           <li><a href="/cartList.go">찜한 공고</a></li>
	           <li><a href="/msgsend.go">쪽지함</a></li>
	           <li style="background-color: #6ba8dd"><a style="color: #fff" href="/boardHistory.go">작성한 게시글</a></li>
	           <li><a href="/qnaHistory.go">작성한 Q&A</a></li>
	        </ul>
	   	</div>
		<div id="table">
			<p>마이페이지 > 작성한 게시글</p>
			<select id="pagePerNum">
		         <option value="5">5개씩</option>
		         <option value="10">10개씩</option>
		         <option value="15">15개씩</option>
		         <option value="20">20개씩</option>
	      	</select>
	      
	     	<table class="info">
		         <thead>
		            <tr>
		               <th>번호</th>
		               <th width="630">게시글제목</th>
		               <th width="200">작성일</th>
		               <th>조회수</th>
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
         </div>
      </div>
</body>
<script>
var msg = "${msg}";

if(msg != ""){
   alert(msg);
}

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
      url : '/boardHistory.ajax',
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
	   var date = new Date(item.board_date);
	  
      console.log(item);
      content += '<tr>';
      content += '<td>'+(num++)+'</td>';
      content += '<td><a href="boarddetail.go?board_idx='+item.board_idx+'">'+item.board_title+'</a></td>';
      content += '<td>'+date.toLocaleDateString("ko-KR").replace(/\.$/, '')+'</td>';
      content += '<td>'+item.board_hits+'</td>';
      content += '</tr>';
   });
   $('#list').empty();
   $('#list').append(content);
}

/* $('#all').click(function(){
	
	var $chk = $('input[type="checkbox"]');	//체크박스를 모두 가져옴
	
	if($(this).is(":checked")){
		$chk.prop("checked",true);	
	}else{
		$chk.prop("checked",false);
	}
	
});



function boardHisdel(){
	var idx = "${sessionScope.loginId}";
	var chkarr=[];
	//check 된 체크박스의 값을 가져온다.
	$('#list input[type="checkbox"]:checked').each(function(idx,item){
		chkarr.push($(this).val());
	});
	
	console.log(chkarr);
	
	//체크된 박스들 아작스로 컨트롤에 보내기
	$.ajax({
		type:'get',
		url:'/boardHisdel.ajax',
		data:{boarddelList:chkarr,
				idx : idx,	
		},
		dataType:'JSON',
		success:function(data){
			console.log(data);
			if(data.login){
				location.href='/boardHistory.go';
				listCall(currPage);
				alert(data.msg);
			}else{
				alert("로그인이 필요한 서비스 입니다.");
				
			}
		},
		error:function(e){
			console.log(e);
		}
	});
	
} */

</script>
</html>