<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
	<%@ include file="/jsp/headhead.jsp" %>
	<!-- <title>订单列表</title> -->
<body>
	<!-- 这里被抽取了“菜单栏”和“导航条” -->
	<!-- 静态包含是代码过来再运行，动态包含是运行或再把结果过来 -->
	<!-- 动态包含的话，那边的包还要导一遍！！所以还是用静态包含 -->
	<%@ include file="/jsp/head.jsp" %>
<div class="container">

	<div class="row">
		<div style="margin:0 auto; margin-top:10px;width:950px;">
			<strong>我的订单</strong>
			<table class="table table-bordered">
				<c:forEach items="${pb.data }" var="o" >
					<tbody>
						<tr class="success">
							<th colspan="2">订单编号:${o.oid } </th>
							<th colspan="1">
								<c:if test="${o.state == 0 }">
									<a href="${pageContext.request.contextPath }/order?method=getById&oid=${o.oid}" >去付款</a>
									<a href="javascript:void(0)" onclick="cancelOrder('${o.oid}')" >（取消订单）</a>
								</c:if>
								<c:if test="${o.state == 1 }">已付款</c:if>
								<c:if test="${o.state == 2 }"><a href="javascript:void(0)" onclick="received('${o.oid}')" >确认收货</a></c:if>
								<c:if test="${o.state == 3 }">已完成<a href="javascript:void(0)" onclick="deleteOrder('${o.oid}')" >（删除记录）</a></c:if>
							</th>
							<th colspan="2">金额:${o.total }</th>
						</tr>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<c:forEach items="${o.items }" var="oi" >
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${oi.product.pimage }" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank"> ${oi.product.pname }</a>
								</td>
								<td width="20%">
									￥${oi.product.shop_price }
								</td>
								<td width="10%">
									${oi.count }
								</td>
								<td width="15%">
									<span class="subtotal">￥${oi.subtotal }</span>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</c:forEach><!-- co:foreach：结束 -->
			</table>
		</div>
	</div><!-- row：结束 -->
	
	<div style="text-align: center;">
		<ul class="pagination">
			<!-- =============复制过来的分页============= -->
			<!-- 判断是否是第一页 -->
			<c:if test="${pb.pageNumber == 1 }">
				<li class="disabled">
					<a href="javascript:void(0)" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
			</c:if>
			<c:if test="${pb.pageNumber != 1 }">
				<li>
					<a href="${pageContext.request.contextPath }/order?method=findMyOrdersByPage&pageNumber=${pb.pageNumber-1}" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
			</c:if>
			
			<!-- 展示所有页码 -->
			<c:forEach begin="1" end="${pb.totalPage }" var="n" >
				<!-- 判断是否是当前页 -->
				<c:if test="${pb.pageNumber == n }">
					<li class="active"><a href="javascript:void(0)">${n }</a></li>
				</c:if>
				<c:if test="${pb.pageNumber != n }">
					<li><a href="${pageContext.request.contextPath }/order?method=findMyOrdersByPage&pageNumber=${n }">${n }</a></li>
				</c:if>
			</c:forEach>
			
			<!-- 判断是否是第一页 -->
			<c:if test="${pb.pageNumber == pb.totalPage }">
				<li class="disabled">
					<a href="javascript:void(0)" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:if>
			<c:if test="${pb.pageNumber != pb.totalPage }">
				<li>
					<a href="${pageContext.request.contextPath }/order?method=findMyOrdersByPage&pageNumber=${pb.pageNumber+1 }" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:if>
			<!-- =============复制过来的分页：结束============= -->
		</ul>
	</div>
			
</div>
<%@ include file="/jsp/foot.jsp" %>
</body>
<script type="text/javascript">
	function received(oid) {
		if (confirm("确认收货了吗？")) {
			location.href = "${pageContext.request.contextPath }/order?method=confirmReceive&oid=" + oid;
		}
	}
	function deleteOrder(oid) {
		if (confirm("确认删除记录吗？")) {
			location.href = "${pageContext.request.contextPath }/order?method=deleteOrder&oid=" + oid;
		}
	}
	function cancelOrder(oid) {
		if (confirm("确认删除订单吗？")) {
			location.href = "${pageContext.request.contextPath }/order?method=cancelOrder&oid=" + oid;
		}
	}
</script>
</html>