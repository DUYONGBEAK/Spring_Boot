<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
로그인 페이지 입니다.</br>
  <c:if test="${sessionScope.userId ==null || sessionScope.userId == '' }">
<form action = "/login" method = "post">
		<input type = "text" id = "id" name = "id" placeholder = "input your id"><br>
		<input type = "password" id ="pwd" name = "pwd" placeholder = "input your password"><br>
		<input type = "submit" value = "로그인" >
		<input type = "button" value = "회원가입" onClick="location.href='join'">
	</form>
</c:if>
<c:if test="${sessionScope.userId !=null && sessionScope.userId !='' }">
	<p>${sessionScope.userName }님 환영합니다.</p>
		<button type="button" onclick="location.href='logout'">로그아웃</button>
		<button type="button" onclick="location.href='userUpdate'">회원정보 수정</button>
		<button type="button" onclick="location.href='userDelete'">회원탈퇴</button><br><br>
		<button type="button" onclick="location.href='board/main'">게시판으로 이동하기</button>
</c:if>
</body>
</html>