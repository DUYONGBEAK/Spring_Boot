<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
게시판 작성 또는 게시판 수정 페이지 입니다.</br>	
<% String type = (String) request.getAttribute("type"); //insert or update %>

<% if (type.equals("update")) { %>
	<form action = "/board/updateServlet" method = "post">
		<input type = "hidden" id = "id" name = "id" value = ${ id } ><br>
		<input type = "text" id ="title" name = "title" value = ${ title } placeholder = "input your title"><br>
		<textarea id = "content" name = "content" placeholder = "input your content">${ content }</textarea><br>
		<input type = "submit" value = "수정" >
	</form>
<% }
   else { %>
<form action = "/board/insertServlet" method = "post">
		<input type = "hidden" id = "id" name = "id" value = ${ id } ><br>
		<input type = "text" id ="title" name = "title" placeholder = "input your title"><br>
		<textarea id = "content" name = "content" placeholder = "input your content"></textarea><br>
		<input type = "submit" value = "등록하기">
	</form>
<% } %>
</body>
</html>