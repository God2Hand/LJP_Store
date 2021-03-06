<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript">
			function flush(){
				window.location.href="${pageContext.request.contextPath}/adminCategory?method=findAll";
			}
			function deleteC(cid) {
				if (confirm("确认删除吗？（该分类下的商品都会被删除）")) {
					window.location.href="${ pageContext.request.contextPath }/adminCategory?method=delete&cid=" + cid;
				}
			}
		</script>
	</HEAD>
	<body>
		<br>
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>分类列表</strong>
						</TD>
					</tr>
					<tr>
						<!-- 这个按钮可能要废~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
						<td class="ta_01" align="right">
							&nbsp;&nbsp;友情提示：Ctrl + F 可以按名快速查找哦~&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" id="add" name="add" value="刷新" class="button_add" onclick="flush()" >刷新</button>
						</td>
					</tr>
					<c:if test="${empty list }">
						<tr>
							<td>
								<h2>该分类空空如也喔~~~</h2>
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty list }">
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
										分类名称
									</td>
									<td width="10%" align="center">
										查看该分类商品
									</td>
									<td width="10%" align="center">
										编辑
									</td>
									<td width="10%" align="center">
										删除
									</td>
								</tr>
								<c:forEach var="c" items="${ list }" varStatus="vs" >
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												${vs.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="25%">
												${c.cname }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="25%">
												<a href="${pageContext.request.contextPath}/adminProduct?method=findByC&cid=${c.cid }&del=0">
													<font color='blue' ><u>See</u></font>
												</a>
											</td>
											<td align="center" style="HEIGHT: 22px">
												<a href="${ pageContext.request.contextPath }/adminCategory?method=editUI&cid=${c.cid}">
													<img src="${pageContext.request.contextPath}/images/i_edit.gif" border="0" style="CURSOR: hand">
												</a>
											</td>
									
											<td align="center" style="HEIGHT: 22px">
												<a href="javascript:void(0)" onclick="deleteC('${c.cid}')" >
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
	</body>
</HTML>

