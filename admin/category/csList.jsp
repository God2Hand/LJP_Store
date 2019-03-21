<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript">
			function deleteProduct(pid, pimage) {
				if (confirm("确认删除商品吗？")) {
					location.href = "${pageContext.request.contextPath }/adminProduct?method=delete&pid=" + pid + "&pimage=" + pimage;
				}
			}
			/* function delC(cid) {
				if (confirm("确定【清空】该分类【所有商品】并【删除分类】吗？")) {
					if (confirm("真的确定【清空】该分类【所有商品】并【删除分类】吗？")) {
						if (confirm("【最后】再确认一遍：真的确定【清空】该分类【所有商品】并【删除分类】吗？")) {
							location.href = "${pageContext.request.contextPath }/adminProduct?method=delC&cid=" + cid;
						}
					}
				}
			} */
		</script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/admin/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>${c.cname }</strong>
						</TD>
					</tr>
					<tr>
						<!-- 这个按钮可能要废~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
						<td class="ta_01" align="right">
							&nbsp;&nbsp;友情提示：Ctrl + F 可以按名快速查找哦~&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" id="add" name="add" value="刷新" class="button_add" onclick="history.go(-1)" >返回</button>
						</td>
					</tr>
					<c:if test="${empty list }">
						<tr>
							<td>
								<h2>该分类空空如也~~~</h2>
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

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="17%">
										商品图片
									</td>
									<td align="center" width="17%">
										商品名称
									</td>
									<td align="center" width="17%">
										商品价格
									</td>
									<td align="center" width="15%">
										库存
									</td>
									<td align="center" width="10%">
										是否热门
									</td>
									<td width="7%" align="center">	
										编辑
									</td>
									<td width="7%" align="center">
										删除
									</td>
								</tr>
								<c:forEach items="${list }" var="p" varStatus="vs">
										<tr onmouseover="this.style.backgroundColor = 'white'"
											onmouseout="this.style.backgroundColor = '#F5FAFE';">
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												${vs.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												<img width="40" height="45" src="${ pageContext.request.contextPath }/${p.pimage}">
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${p.pname }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${p.shop_price }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="15%">
												${p.stock }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="10%">
												<c:if test="${p.is_hot==1 }">是</c:if>
												<c:if test="${p.is_hot!=1 }">否</c:if>
											</td>
											<td align="center" style="HEIGHT: 22px">
												<a href="${ pageContext.request.contextPath }/adminProduct?method=editUI&pid=${p.pid}">
													<c:if test="${p.pflag == 4 }">
														<font color="blue" ><u>恢复</u></font>
													</c:if>
													<c:if test="${p.pflag != 4 }">
															<img src="${pageContext.request.contextPath}/images/i_edit.gif" border="0" style="CURSOR: hand">
													</c:if>
												</a>
											</td>
									
											<td align="center" style="HEIGHT: 22px">
												<c:if test="${p.pflag == 4 }">
													已删除
												</c:if>
												<c:if test="${p.pflag != 4 }">
													<a href="javascript:void(0)" onclick="deleteProduct('${p.pid}', '${p.pimage}')">
														<img src="${pageContext.request.contextPath}/images/i_del.gif" width="16" height="16" border="0" style="CURSOR: hand">
													</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
					</c:if><!-- 商品展示的if结束 -->
				</TBODY>
			</table>
		</form>
		<%-- <div align="right" >
			清空该分类所有商品并删除该分类：<button onclick="delC('${c.cid}')" ><font color="red">清空并删除</font></button>&nbsp;&nbsp; 
		</div>
		<div>
			${delMsg }
		</div> --%>
	</body>
</HTML>

