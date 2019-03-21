<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
	<%@ include file="/jsp/headhead.jsp" %>
	<!-- <title>会员注册</title> -->
<body>
	<!-- 这里被抽取了“菜单栏”和“导航条” -->
	<!-- 静态包含是代码过来再运行，动态包含是运行或再把结果过来 -->
	<!-- 动态包含的话，那边的包还要导一遍！！所以还是用静态包含 -->
	<%@ include file="/jsp/head.jsp" %>
<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/image/regist_bg.jpg');">


	<div class="row"> 
		<div class="col-md-2"></div>
		
		<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
			<font>修改密码</font>USER CHANGEPWD
			<form class="form-horizontal" style="margin-top:5px;" method="post" action="${pageContext.request.contextPath }/user">
				<input type="hidden" name="method" value="changePwd">
				<input type="hidden" name="uid" value="<%= request.getParameter("uid") %>">
				<div class="form-group" >
				    <label for="inputPassword3" class="col-sm-2 control-label">旧密码</label>
				    <span><font color="red">${msg }</font></span>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" id="username" placeholder="请输入旧密码" name="password">
				    </div>
				</div>
				<div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">新密码</label>
				    <div class="col-sm-6">
				    	<input type="password" class="form-control" id="repassword" placeholder="请输入新密码" name="repassword" onblur="valid_password()" >
				    </div>
				    <font id="t2" ></font>
				</div>
				<div class="form-group">
				    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
				    <div class="col-sm-6">
				    	<input type="password" class="form-control" id="rerepassword" placeholder="请输入确认密码" onblur="valid_repassword()" >
				    </div>
				    <font id="t3" ></font>
			    </div>
				<div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				    	<input type="submit"  width="100" value="修改密码" name="submit" border="0"
				    	onclick="return valid_checkAll()"
					    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
					    height:35px;width:100px;color:white;">
				    </div>
				</div>
			</form>
		</div><!-- col-md-8：结束 -->
		
		<div class="col-md-2"></div>
	</div>

</div>
<%@ include file="/jsp/foot.jsp" %>
<script type="text/javascript">
	function valid(id, pattern) {
		var txt = document.getElementById(id);
		var value = txt.value;
		value = value.trim();
		if (!pattern.test(value)) {
			return false;
		} else {
			return value;
		}
	}
	function valid_password() {
		var pPattern = /^[a-zA-Z0-9_-]{4,20}$/;
		var value = valid("repassword", pPattern);
		if (!value) {
			document.getElementById("t2").innerHTML = "密码必须4到20位（字母，数字，下划线，减号）";
			return false;
		} else {
			document.getElementById("t2").innerHTML = "√";
			return true;
		}
	}
	function valid_repassword() {
		var p = document.getElementById("repassword").value;
		var rep = document.getElementById("rerepassword").value;
		if (p == rep) {
			document.getElementById("t3").innerHTML = "√";
			return true;
		} else {
			document.getElementById("t3").innerHTML = "两次输入密码不一致！";
			return false;
		}
	}
	function valid_checkAll() {
		var f2 = valid_password();
		var f3 = valid_repassword();
		if (f2 && f3) {
			return true;
		}
		return false;
	}
</script>
</body>
</html>