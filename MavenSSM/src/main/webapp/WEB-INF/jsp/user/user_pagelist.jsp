<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="User" class="cn.ssm.entity.User" scope="request"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分页查询</title>
<link rel="stylesheet" type="text/css"
	href="css/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="css/icon.css">
<link rel="stylesheet" type="text/css" href="css/list.css">
<style type="text/css">
.demo-rtl .portal-column-left {
	padding-left: 10px;
	padding-right: 10px;
}

.demo-rtl .portal-column-right {
	padding-left: 10px;
	padding-right: 0;
}

.red {
	color: #ff0000;
	padding-left: 10px;
	font-size: 12px;
}

.inputs {
	border: 1px solid #333;
	width: 173px;
	float: left;
}

#pagefenye ul {
	padding: 4px 0;
	margin: 0;
	overflow: hidden;
	margin-top: 10px;
}

#pagefenye ul li {
	display: inline;
	padding: 5px;
	margin: 0px 5px;
}

#pagefenye ul li a {
	color: #428bca;
	text-decoration: none;
}

#pagefenye .active {
	background-color: #3BAAE3;
	padding: 5px 10px;
	color: #fff;
}
</style>
<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.portal.js"></script>
<script type="text/javascript">
$(function(){
	$("#btnSelect").click(function(){
		$("#form").submit();
	});
});
</script>
</head>
<body>
	<form:form commandName="User" action="toPageList" method="post"
		id="form">
		<div>
			<div class="easyui-accordion" style="width: 99%;">
				<div title="查询条件" data-options="iconCls:'icon-ok'"
					style="overflow: auto; padding: 10px;">
					<table>
						<tr>
							<td>用户名：</td>
							<td><form:input path="name" /></td>
						</tr>
						<tr>
							<td>年龄：</td>
							<td><form:input path="age" /></td>
						</tr>
						<tr>
							<td align="center"><a href="#" class="easyui-linkbutton"
								id="btnSelect" data-options="iconCls:'icon-search'">查 询</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form:form>
	<div class="easyui-accordion" style="width: 99%;">
		<div title="用户列表" data-options="iconCls:'icon-ok'"
			style="overflow: auto; padding: 10px;">
			<table style="line-height: 25px;" id="tbJiaoYi">
				<thead id="biaoTi">
					<tr align="center">
						<th>编号</th>
						<th>姓名</th>
						<th>年龄</th>
						<th>编辑</th>
						<th>删除</th>
					</tr>
				</thead>
				<tbody id="lieBiao">
					<c:forEach var="user" items="${page.list}">
						<tr align="center">
							<td>${user.id}</td>
							<td>${user.name}</td>
							<td>${user.age}</td>
							<td><a href="toEdit?id=${entity.id}"
								class="easyui-linkbutton" id="btnUpdate"
								data-options="iconCls:'icon-edit'">修 改</a></td>
							<td><a onclick="del(${entity.id})" class="easyui-linkbutton"
								id="btnDelete" data-options="iconCls:'icon-remove'">删 除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<div style="text-align: center;" id="pagefenye">
				<ul class="gridtable" style="list-style: none;" id="pagenation_ul">
					<c:if test="${!page.isFirstPage&&page.total!=0}">
						<li><a href="toPageList?pageNum=1&pageSize=${page.pageSize}">首页</a></li>
					</c:if>
					<c:if test="${page.hasPreviousPage}">
						<li><a
							href="toPageList?pageNum=${page.prePage}&pageSize=${page.pageSize}">前一页</a></li>
					</c:if>
					<c:forEach items="${page.navigatepageNums}" var="nav"
						varStatus="status">
						<c:if test="${nav == page.pageNum}">
							<li style="font-weight: bold;" class="pagenation_li${nav}">${nav}</li>
						</c:if>
						<c:if test="${nav != page.pageNum}">
							<li class="pagenation_li${nav}"><a
								href="toPageList?pageNum=${nav}&pageSize=${page.pageSize}">${nav}</a></li>
						</c:if>
					</c:forEach>
					<c:if test="${page.hasNextPage}">
						<li><a
							href="toPageList?pageNum=${page.nextPage}&pageSize=${page.pageSize}">下一页</a></li>
					</c:if>
					<c:if test="${!page.isLastPage}">
						<li><a
							href="toPageList?pageNum=${page.pages}&pageSize=${page.pageSize}">末页</a></li>
					</c:if>
				</ul>
				<script>
					$(".pagenation_li${page.pageNum}").addClass("active");
					$(function() {
						$("#pagefenye a").click(function() {
							var href = $(this).attr("href");
							var formStr = $("#form").serialize();
							window.location.href = href + "&" + formStr;
							return false;
						});
					});
				</script>
			</div>
		</div>
	</div>
</body>
</html>