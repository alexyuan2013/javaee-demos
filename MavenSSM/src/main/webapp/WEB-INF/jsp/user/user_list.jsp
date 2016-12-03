<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>用户信息列表</title>
</head>
<body>${loginUser}
	<br />
	<table>
		<tr>
			<td>编号</td>
			<td>姓名</td>
			<td>年龄</td>
			<td>编辑</td>
			<td>删除</td>
		</tr>
		<c:forEach var="user" items="${userlst}">
			<tr>
				<td>${user.id}</td>
				<td>${user.name}</td>
				<td>${user.age}</td>
				<td><a href="toEditUser?id=${user.id}">点击修改</a></td>
				<td><a href="toDelUser?id=${user.id}">点击删除</a></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<a href="toAddUser">新增用户</a>
</body>
</html>