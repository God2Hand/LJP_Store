<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
	<%@ include file="/jsp/headhead.jsp" %>
	<!-- <title>会员登录</title> -->
<body>
	<!-- 这里被抽取了“菜单栏”和“导航条” -->
	<!-- 静态包含是代码过来再运行，动态包含是运行或再把结果过来 -->
	<!-- 动态包含的话，那边的包还要导一遍！！所以还是用静态包含 -->
	<%@ include file="/jsp/head.jsp" %>
<div class="container"  style="width:100%;height:460px;background:#FF2C4C url('${pageContext.request.contextPath}/images/loginbg.jpg') no-repeat;">

	<div class="row"> 
	
		<div class="col-md-7">
			<!--<img src="${pageContext.request.contextPath}/image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
		</div>
		
		<div class="col-md-5">
		<div style="width:440px;border:1px solid #E7E7E7;padding:20px 0 20px 30px;border-radius:5px;margin-top:60px;background:#fff;">
			<font>会员登录</font>USER LOGIN ${msg }
			<div>&nbsp;</div>
			<form class="form-horizontal" action="${pageContext.request.contextPath }/user" method="post">
				<input type="hidden" name="method" value="login">
	 			<div class="form-group">
		   			<label for="username" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-6">
						<input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username" >
					</div>
	  			</div>
	   			<div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
				    <div class="col-sm-6">
				    	<input type="password" class="form-control" id="inputPassword3" placeholder="请输入密码" name="password">
				    </div>
	  			</div>
	   			<div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
				    <div class="col-sm-3">
				    	<input type="text" name="identifyingCode" class="form-control" id="identifyingCode" placeholder="请输入验证码">
				    </div>
				    <div class="col-sm-3">
				    	<img src="${pageContext.request.contextPath}/ic" id="imgID" />
				    </div>
			 	</div>
				<div class="form-group">
				   <div class="col-sm-offset-2 col-sm-10">
				     <div class="checkbox">
					 	<!-- <label>
					         <input type="checkbox"> 自动登录
					    </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
					    <label>
					         <input type="checkbox" name="savename" value="yes" > 记住用户名
					    </label>
					    &nbsp;&nbsp;
			    		<a href="javascript:void(0);" onclick="ic()">看不清，换一张</a>
				     </div>
				   </div>
			 	</div>
			 	<div class="form-group">
				   <div class="col-sm-offset-2 col-sm-10">
					   <input type="submit"  width="100" value="登录" name="submit" border="0"
					   style="background: url('${pageContext.request.contextPath}/images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
					   height:35px;width:100px;color:white;">
				   </div>
			  	</div>
			</form>
		</div>			
		</div>
		
	</div>

</div>	
<%@ include file="/jsp/foot.jsp" %>
</body>
<script type="text/javascript">
	var val = "${cookie.saveName.value}";
	document.getElementById("username").value = decodeURI(val);
	
	function ic() {
		//获取图片
		var image = document.getElementById("imgID");
		image.src = "${pageContext.request.contextPath}/ic?time=" + new Date().getTime();
			//这里加上时间戳，使得每一次访问都是一次不同的请求，以防缓存使得图片不更新
	}
</script>
</html>