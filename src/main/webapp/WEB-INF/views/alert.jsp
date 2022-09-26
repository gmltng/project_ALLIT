<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>"https://code.jquery.com/jquery-3.6.0.min.js"</script>
<style></style>
</head>
<body>
	<!-- 정현민 추가 7/1 -->
	<!-- 비로그인회원의 비정상적인 접근을 막고, alert 창을 띄어주는 script -->
	<!-- 새로고침 시 model로 받는 msg가 반복되지 않는다는 장점이 있다. -->
	<script type="text/javascript">
		var message = "${msg}";
		var url = "${url}";
		alert(message);
		document.location.href = url;
	</script>
</body>
<script></script>
</html>