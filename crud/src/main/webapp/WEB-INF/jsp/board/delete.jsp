<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
정말 삭제하시겠습니까?
<form action = "/board/deleteServlet" method = "post">
<input type = "hidden" id = "id" name = "id" value = ${ id } >
<input type = "submit" value = "예">
<button type="button" onclick="location.href='/board/main'">아니오</button>
</form>

</body>
</html>