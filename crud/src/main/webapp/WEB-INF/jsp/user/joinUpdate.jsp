<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
회원가입 또는 회원정보수정 페이지 입니다.</br>	
<% String type = (String) request.getAttribute("type"); //insert or update %>

<% if (type.equals("update")) { %>
	<p>비밀번호를 변경하지 않으시려면 아무것도 입력하지 마십시오</p>
	<form action = "/userUpdateServlet" method = "post">
		<input type = "text" id = "id" name = "id" value = ${ id } readonly><br>
		<input type = "password" id ="pwd" name = "pwd" placeholder = "input your password"><br>
		<input type = "text" id = "name" name = "name" value = ${ name } placeholder = ${ name }><br>
		<input type = "number" id ="age" name = "age" value = ${ age } placeholder = ${ age }><br>
		<input type="radio" name="sex" value="남">남
		<input type="radio" name="sex" value="여" checked>여<br>
		<input type = "submit" value = "수정" >
	</form>
<% }
   else { %>
<form action = "/userJoinServlet" method = "post">
		<input type = "text" id = "id" name = "id" placeholder = "input your id"><br>
		<input type = "password" id ="pwd" name = "pwd" placeholder = "input your password"><br>
		<input type = "text" id = "name" name = "name" placeholder = "input your name"><br>
		<input type = "number" id ="age" name = "age" placeholder = "input your age"><br>
		<input type="radio" name="sex" value="남">남
		<input type="radio" name="sex" value="여" checked>여<br>
		<input type = "submit" value = "가입하기">
	</form>
<% } %>
</body>
</html>