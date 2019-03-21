<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<%@ include file="/jsp/headhead.jsp" %>
	<!-- <title>信息提示</title> -->
<body>
	<!-- 这里被抽取了“菜单栏”和“导航条” -->
	<!-- 静态包含是代码过来再运行，动态包含是运行或再把结果过来 -->
	<!-- 动态包含的话，那边的包还要导一遍！！所以还是用静态包含 -->
	<%@ include file="/jsp/head.jsp" %>
<div class="container-fluid">

	<!-- 信息展示 -->
	<div class="container-fluid">
		<div class="container-field" >
			<h1>${msg }</h1>
		</div>
	</div>
	
</div>
<%@ include file="/jsp/foot.jsp" %>
</body>
</html>