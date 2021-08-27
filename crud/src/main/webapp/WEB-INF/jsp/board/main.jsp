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
게시판 메인화면입니다.
<table>
		<th> 번호 </th>
		<th> 제목 </th>
		<th> 내용 </th>
		<th> 작성자 </th>
		<th> 작성일 </th>
		<!-- conArr이라는 ArrayList를 받아 각 인덱스에 con라는 이름을 붙여 사용. -->
		<c:forEach var = "con" items = "${ conArr }">
			<tr>
				<td> ${ con.getId() } </td>
				<td> ${ con.getTitle() } </td>
				<td> ${ con.getContent() } </td>
				<td> ${ con.getUserId() } </td>
				<td> ${ con.getDate() } </td>
				<td> 
					<!-- 수정 기능 -->
					<form action="/board/update" method = "post">
						<input type="hidden" id="userId" name="userId" value= ${ con.getUserId() }>
						<input type="hidden" id="id" name="id" value= ${ con.getId() }>
						<input type="submit" value="수정">
					</form>
				</td>
				<td> 
					<!-- 삭제 기능 -->
					<form action="/board/delete" method="post">
						<input type="hidden" id="userId" name="userId" value= ${ con.getUserId() }>
						<input type="hidden" id="id" name="id" value= ${ con.getId() }>
						<input type="submit" value="삭제">
					</form>
				</td>
			</tr>
		</c:forEach>
		
		<tr>
			<td> 
				<!-- 추가 --> 
				<form action="/board/register" method="post">
					<input type="submit" value="새로 작성">
				</form>
			</td>
		</tr>
	</table>
</body>
</html>