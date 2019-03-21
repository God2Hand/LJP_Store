<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	</HEAD>
	
	<body>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/manager" method="post">
			<input type="hidden" name="method" value="mAdd">
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>添加管理员</STRONG>
						</strong>
					</td>
				</tr>

				<tr>
					<td width="45%" align="right" bgColor="#f5fafe" class="ta_01">
						用户名：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input type="text" name="mname" id="mname" class="bg" onblur="valid_mname()" />
						<font color="red" id="t1" ></font>
					</td>
				</tr>
				<tr>
					<td width="45%" align="right" bgColor="#f5fafe" class="ta_01">
						密码：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input type="password" name="mpassword" id="mpassword" class="bg" onblur="valid_mpassword()" />
						<font color="red" id="t2" ></font>
					</td>
				</tr>
			
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="submit" onclick="return valid_checkAll();" id="userAction_save_do_submit" value="确定" class="button_ok">
							&#30830;&#23450;
						</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
						<span id="Label1"></span>
					</td>
				</tr>
			</table>
		</form>
		<div align="center" >
			<font color="red" >${msg }</font>
		</div>
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
			function valid_mname() {
				var uPattern = /^.{3,32}$/;
				var value = valid("mname", uPattern);
				if (!value) {
					document.getElementById("t1").innerHTML = "用户名必须3到32位";
					return false;
				}
				$.post("${pageContext.request.contextPath}/manager", {"method":"checkRepeat", "value":value }, function(tip){
					document.getElementById("t1").innerHTML = tip;
				});
				return true;
			}
			function valid_mpassword() {
				var pPattern = /^[a-zA-Z0-9_-]{4,32}$/;
				var value = valid("mpassword", pPattern);
				if (!value) {
					document.getElementById("t2").innerHTML = "密码必须4到32位（字母，数字，下划线，减号）";
					return false;
				} else {
					document.getElementById("t2").innerHTML = "√";
					return true;
				}
			}
			function valid_checkAll() {
				var f1 = valid_mname();
				var f2 = valid_mpassword();
				if (f1 && f2) {
					document.Form1.submit();
					return true;
				} else {
					return false;
				}
			}
		</script>
	</body>
</HTML>