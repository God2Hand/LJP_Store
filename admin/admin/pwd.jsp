<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
	</HEAD>
	
	<body>
		<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/manager" method="post">
			<input type="hidden" name="method" value="pwd">
			<input type="hidden" name="mid" value="${admin.mid }">
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>修改密码</STRONG>
						</strong>
					</td>
				</tr>
				<tr>
					<td width="45%" align="right" bgColor="#f5fafe" class="ta_01">
						新密码：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input onblur="valid_mpassword()" type="text" name="mpassword" id="mpassword" class="bg"/>
						<font color="red" id="t1" ></font>
					</td>
				</tr>
			
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="submit" onclick="return valid_checkAll();" id="userAction_save_do_submit" value="修改密码" class="button_ok">
							修改密码
						</button>
					</td>
				</tr>
			</table>
		</form>
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
			function valid_mpassword() {
				var pPattern = /^[a-zA-Z0-9_-]{4,32}$/;
				var value = valid("mpassword", pPattern);
				if (!value) {
					document.getElementById("t1").innerHTML = "密码必须4到32位（字母，数字，下划线，减号）";
					return false;
				} else {
					document.getElementById("t1").innerHTML = "√";
					return true;
				}
			}
			function valid_checkAll() {
				var f1 = valid_mpassword();
				if (f1) {
					document.Form1.submit();
					return true;
				} else {
					return false;
				}
			}
		</script>
	</body>
</HTML>