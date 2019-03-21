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
			<input id="uid" type="hidden" name="uid" value="<%= request.getParameter("uid") %>">
			<font>忘记密码</font>USER FORGETPWD
			<div align="center" >
				我们将向您的邮箱发送您的密码？<br />
				确认忘记请输入验证码：
			</div>
			<div class="form-group">
			    <label for="date" class="col-sm-2 control-label">验证码</label>
			    <div class="col-sm-3">
			    	<input type="text" id="identifyingCode" name="identifyingCode" class="form-control"  >
			    </div>
			    <div class="col-sm-2">
			    	<img src="${pageContext.request.contextPath}/ic" id="imgID" />
		  		</div>
		  		&nbsp;&nbsp;&nbsp;
		    	<a href="javascript:void(0);" onclick="ic()">看不清，换一张</a>
			</div>
			${msg }<br />
			<div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			    	<input type="button"  width="100" value="取消" name="submit" border="0"
			    	onclick="window.location.href='${pageContext.request.contextPath}/'"
				    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">

			    	<input type="button"  width="100" value="发送邮件" name="submit" border="0"
			    	onclick="forgetPwd()"
				    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
			    </div>
			</div>
		</div><!-- col-md-8：结束 -->
		
		<div class="col-md-2"></div>
	</div>

</div>
<%@ include file="/jsp/foot.jsp" %>
</body>
<script type="text/javascript">
	/* 前面还有校验验证码部分没写 */
	function ic() {
		//获取图片
		var image = document.getElementById("imgID");
		image.src = "${pageContext.request.contextPath}/ic?time=" + new Date().getTime();
			//这里加上时间戳，使得每一次访问都是一次不同的请求，以防缓存使得图片不更新
	}
	
	function forgetPwd() {
		var uid = document.getElementById("uid").value;
		var identifyingCode = document.getElementById("identifyingCode").value;
		window.location.href = "${pageContext.request.contextPath}/user?method=forgetPwd&uid="
				+ uid + "&identifyingCode=" + identifyingCode;	
	}
</script>
</html>