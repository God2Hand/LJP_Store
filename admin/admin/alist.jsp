<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
		<script language="javascript" src="${pageContext.request.contextPath}/layer/layer.js"></script>
		<script type="text/javascript">
			function addA() {
				window.location.href="${ pageContext.request.contextPath }/admin/admin/add.jsp";
			}
			function deleteA(mid) {
				if (confirm("确认删除吗？")) {
					if (confirm("真的确认删除吗？")) {
						window.location.href="${ pageContext.request.contextPath }/manager?method=deleteA&mid=" + mid;
					}
				}
			}
			function pwdA(mid) {
				layer.prompt({title: '请输入新密码，并确认', formType: 1}, function(mpassword, index){
					layer.close(index);
					if (!valid(mpassword)) {
						layer.msg("密码必须4到32位（字母，数字，下划线，减号）", {
							time: 2000, //2s后自动关闭
							btn: ['关闭']
						});
					} else {
						window.location.href="${ pageContext.request.contextPath }/manager?method=pwdA&mid=" + mid + "&mpassword=" + mpassword;
					}
				});
			}
			function see(mpassword){
				layer.msg("<font size='2'>" + mpassword + "</font>", {
					time: 1000, //2s后自动关闭
					btn: ['关闭']
				});
			}
		</script>
	</HEAD>
	<body>
		<br>
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>普通管理员列表</strong>
						</TD>
					</tr>
					<tr>
						<td class="ta_01" align="right">
							&nbsp;&nbsp;友情提示：Ctrl + F 可以按名快速查找哦~&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" id="add" name="add" value="新增" class="button_add" onclick="addA()" >
								<font color="red" >
									&nbsp;新增&nbsp;
								</font>
							</button>
							&nbsp;&nbsp;
						</td>
					</tr>
					<c:if test="${empty aList }">
						<tr>
							<td>
								<h2>还没有普通管理员喔~~~</h2>
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty aList }">
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="15%">
										序号
									</td>
									<td align="center" width="30%">
										管理员用户名
									</td>
									<td width="10%" align="center">
										密码
									</td>
									<td width="10%" align="center">
										更改密码
									</td>
									<td width="10%" align="center">
										删除
									</td>
								</tr>
								<c:forEach var="a" items="${ aList }" varStatus="vs" >
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												${vs.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="25%">
												${a.mname }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="25%">
												<a href="javascript:(0)" onclick="see('${a.mpassword}')" >
													<font color='blue' ><u>See</u></font>
												</a>
											</td>
											<td align="center" style="HEIGHT: 22px">
												<a href="javascript:void(0)" onclick="pwdA('${a.mid}')">
													<img src="${pageContext.request.contextPath}/images/i_edit.gif" border="0" style="CURSOR: hand">
												</a>
											</td>
									
											<td align="center" style="HEIGHT: 22px">
												<a href="javascript:void(0)" onclick="deleteA('${a.mid}')" >
													<img src="${pageContext.request.contextPath}/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
												</a>
											</td>
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
					</c:if>
				</TBODY>
			</table>
					${msg }
			<script type="text/javascript">
				function valid(value) {
					var pattern = /^[a-zA-Z0-9_-]{4,32}$/;
					value = value.trim();
					if (!pattern.test(value)) {
						return false;
					} else {
						return value;
					}
				}
			</script>
	</body>
</HTML>

