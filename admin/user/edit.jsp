<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
	</HEAD>
	
	<body>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/adminUser" method="post" >
			<input type="hidden" name="method" value="update" />
			<input type="hidden" name="uid" value="${user.uid }" />
			<%-- <input type="hidden" name="code" value="${user.code }" /> --%>
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>编辑用户</STRONG>
						</strong>
					</td>
				</tr>

				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						用户名称：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input readonly="readonly" type="text" name="username" value="${user.username }" id="username" class="bg"/>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01" onblur="valid_username()" >
						密码：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="password" name="password" value="${user.password }" id="password" class="bg"/>
						<font color="red" id="t2" ></font>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						真实姓名：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="name" value="${user.name }" id="name" class="bg"  onblur="valid_name()"  />
						<font color="red" id="t5" ></font>
						</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						邮箱：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input readonly="readonly" type="text" name="email" value="${user.email }" id="email" class="bg"/>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						电话：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="telephone" value="${user.telephone }" id="telephone" class="bg" onblur="valid_telephone()" />
						<font color="red" id="t6" ></font>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						激活状态：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<select name="state">
							<c:if test="${user.state == 1 }">
								<option value="1" selected="selected" >已激活</option>
								<option value="0">未激活</option>
							</c:if>
							<c:if test="${user.state == 0 }">
								<option value="1">已激活</option>
								<option value="0" selected="selected" >未激活</option>
							</c:if>
						</select>
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
				var value = valid("password", pPattern);
				if (!value) {
					document.getElementById("t2").innerHTML = "密码必须4到20位（字母，数字，下划线，减号）";
					return false;
				} else {
					document.getElementById("t2").innerHTML = "√";
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
			function valid_checkAll() {
				var f2 = valid_password();
				var f5 = valid_name();
				var f6 = valid_telephone();
				if (f2 && f5 && f6) {
					document.Form1.submit();
					return true;
				} else {
					return false;
				}
			}
		</script>
	</body>
</HTML>