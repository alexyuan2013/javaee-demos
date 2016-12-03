<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:useBean id="User" class="cn.ssm.entity.User" scope="request"></jsp:useBean>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>修改用户页面</title>
</head>
<body>
	<form:form action="editUser" commandName="User">
		<table>
			<tr>
				<td colspan="2" align="center">编辑用户</td>
			</tr>
			<tr>
				<td>用户名：</td>
				<td><form:input path="name" value="${oldUser.name}" /> <form:hidden
						path="id" value="${oldUser.id}" /></td>
			</tr>
			<tr>
				<td>年龄：</td>
				<td><form:input path="age" value="${oldUser.age}" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="登 录" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>