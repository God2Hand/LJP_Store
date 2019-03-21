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
			<font>会员注册</font>USER REGISTER
			<form class="form-horizontal" style="margin-top:5px;" method="post" action="${pageContext.request.contextPath }/user">
				<input type="hidden" name="method" value="regist">
				<div class="form-group">
				    <label for="username" class="col-sm-2 control-label">用户名</label>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username" onblur="valid_username()" >
				    </div>
			    	<font id="t1" ></font>
				</div>
				<div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
				    <div class="col-sm-6">
				    	<input type="password" class="form-control" id="password" placeholder="请输入密码" name="password" onblur="valid_password()" >
				    </div>
				    <font id="t2" ></font>
				</div>
				<div class="form-group">
				    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
				    <div class="col-sm-6">
				    	<input type="password" class="form-control" id="repassword" placeholder="请输入确认密码" onblur="valid_repassword()">
				    </div>
				    <font id="t3" ></font>
				</div>
					<div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
				    <div class="col-sm-6">
				    	<input type="email" class="form-control" id="email" placeholder="Email" name="email" onblur="valid_email()">
				    </div>
				    <font id="t4" ></font>
				</div>
				<div class="form-group">
				    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" id="name" placeholder="请输入姓名" name="name" onblur="valid_name()">
				    </div>
				    <font id="t5" ></font>
				</div>
				<div class="form-group">
				    <label for="usercaption" class="col-sm-2 control-label">手机号</label>
				    <div class="col-sm-6">
				    	<input type="text" class="form-control" id="telephone" placeholder="请输入手机号" name="telephone" onblur="valid_telephone()">
				    </div>
				    <font id="t6" ></font>
				</div>
					<div class="form-group opt">  
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>  
						<div class="col-sm-6">
				 			<label class="radio-inline">
								<input type="radio" name="sex" id="inlineRadio1" value="1"> 男
							</label>
							<label class="radio-inline">
								<input type="radio" name="sex" id="inlineRadio2" value="0" checked> 女
							</label>
						</div>
					</div>		
				<div class="form-group">
				    <label for="date" class="col-sm-2 control-label">出生日期</label>
				    <div class="col-sm-6">
				    	<input type="date" class="form-control"  name="birthday" id="birthday" onblur="valid_birthday()" >		      
				    </div>
				    <font id="t7" ></font>
				</div>
				<div class="form-group">
				    <label for="date" class="col-sm-2 control-label">验证码</label>
				    <div class="col-sm-3">
				    	<input type="text" name="identifyingCode" class="form-control" id="checkcode" onblur="valid_checkcode()"  >
				    </div>
				    <div class="col-sm-2">
				    	<img src="${pageContext.request.contextPath}/ic" id="imgID" />
				    	<a href="javascript:void(0);" onclick="ic()">看不清，换一张</a>
				    </div>
			    	<font id="t8" ></font>
				</div>
				<div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				    	<input type="submit"  width="100" value="注册" name="submit" border="0"
				    	onclick="return valid_checkAll();"
					    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
					    height:35px;width:100px;color:white;">
				    <font id="t9" >${msg }</font>
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
	function valid_username() {
		var uPattern = /^[a-zA-Z0-9_-]{4,20}$/;
		var value = valid("username", uPattern);
		if (!value) {
			document.getElementById("t1").innerHTML = "用户名必须4到20位（字母，数字，下划线，减号）";
			return false;
		}
		$.post("${pageContext.request.contextPath}/user", {"method":"checkRepeat", "value":value }, function(tip){
			document.getElementById("t1").innerHTML = tip;
		});
		return true;
	}
	function valid_password() {
		var pPattern = /^[a-zA-Z0-9_-]{4,20}$/;
		var value = valid("password", pPattern);
		if (!value) {
			document.getElementById("t2").innerHTML = "密码必须4到20位（字母，数字，下划线，减号）";
			return false;
		} else {
			document.getElementById("t2").innerHTML = "√";
			return true;
		}
	}
	function valid_repassword() {
		var p = document.getElementById("password").value;
		var rep = document.getElementById("repassword").value;
		if (p == rep) {
			document.getElementById("t3").innerHTML = "√";
			return true;
		} else {
			document.getElementById("t3").innerHTML = "两次输入密码不一致！";
			return false;
		}
	}
	function valid_email() {
		var ePattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		var value = valid("email", ePattern);
		if (!value) {
			document.getElementById("t4").innerHTML = "邮箱格式错误！";
			return false;
		} else {
			$.post("${pageContext.request.contextPath}/user", {"method":"checkEmail", "value":value }, function(tip){
				document.getElementById("t4").innerHTML = tip;
			});
			return true;
		}
	}
	function valid_name() {
		var uPattern = /^.{1,20}$/;
		var value = valid("name", uPattern);
		if (!value) {
			document.getElementById("t5").innerHTML = "姓名必须1到20位";
			return false;
		} else {
			document.getElementById("t5").innerHTML = "√";
			return true;
		}
	}
	function valid_telephone() {
		var tPattern = /^1[34578]\d{9}$/; //http://caibaojian.com/regexp-example.html
		var value = valid("telephone", tPattern);
		if (!value) {
			document.getElementById("t6").innerHTML = "不存在的手机号";
			return false;
		} else {
			document.getElementById("t6").innerHTML = "√";
			return true;
		}
	}
	function valid_birthday() {
		var dP1 = /^\d{4}(\-)\d{1,2}\1\d{1,2}$/;
		var value = valid("birthday", dP1);
		if (!value) {
			document.getElementById("t7").innerHTML = "请选择正确的日期！";
			return false;
		} else {
			document.getElementById("t7").innerHTML = "√";
			return true;
		}
	}
	function valid_checkcode() {
		var p = /^\s*$/;
		var checkcode = document.getElementById("checkcode").value;
		if (p.test(checkcode)) {
			document.getElementById("t8").innerHTML = "请输入验证码！";
			return false;
		} else {
			document.getElementById("t8").innerHTML = " ";
			return true;
		}
	}
	function valid_checkAll() {
		var f1 = valid_username();
		var f2 = valid_password();
		var f3 = valid_repassword();
		var f4 = valid_email();
		var f5 = valid_name();
		var f6 = valid_telephone();
		var f7 = valid_birthday();
		var f8 = valid_checkcode();
		if (f1 && f2 && f3 && f4 && f5 && f6 && f7 && f8) {
			document.getElementById("t9").innerHTML = "注册中...请耐心等待！";
			$('#email').removeAttr('onblur');
			return true;
		}
		return false;
	}
	function ic() {
		//获取图片
		var image = document.getElementById("imgID");
		image.src = "${pageContext.request.contextPath}/ic?time=" + new Date().getTime();
			//这里加上时间戳，使得每一次访问都是一次不同的请求，以防缓存使得图片不更新
	}
</script>
</body>
</html>