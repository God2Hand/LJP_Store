<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html>
	<%@ include file="/jsp/headhead.jsp" %>
	<!-- <title>商品列表</title> -->
<body>
	<!-- 这里被抽取了“菜单栏”和“导航条” -->
	<!-- 静态包含是代码过来再运行，动态包含是运行或再把结果过来 -->
	<!-- 动态包含的话，那边的包还要导一遍！！所以还是用静态包含 -->
	<%@ include file="/jsp/head.jsp" %>
<div class="container">

	<div class="row" style="width:1210px;margin:0 auto;">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<%-- <li><a href="${pageContext.request.contextPath }/">首页</a></li> --%>
				${cname }
				<c:if test="${empty cname}">
					查找结果
				</c:if>
			</ol>
		</div>

		<c:if test="${empty pb.data && empty cname}">
			没有你要找的商品喔~
		</c:if>
		<c:if test="${empty pb.data && not empty cname}">
			该分类还没有商品喔~
		</c:if>
		<c:if test="${not empty pb.data }">
		<c:forEach items="${pb.data }" var="p" varStatus="vs" >		
			<div class="col-md-2" >
				<a href="${pageContext.request.contextPath }/product?method=getById&pid=${p.pid}">
					<img src="${pageContext.request.contextPath}/${p.pimage}" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${pageContext.request.contextPath }/product?method=getById&pid=${p.pid}" style='color:green'>${fn:substring(p.pname, 0, 10) }..</a></p>
				<p><a href="javascript:void(0)" style='color:red' >商城价：&yen;${p.shop_price }</a></p>
				<br>
			</div>
		</c:forEach>
		</c:if>
	</div><!-- row：结束 -->

	<!--分页 -->
	<div style="width:380px;margin:0 auto;margin-top:50px;">
		<ul class="pagination" style="text-align:center; margin-top:10px;">
		
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
					<a href="${pageContext.request.contextPath }/product?method=findByPage&pageNumber=${pb.pageNumber-1}&cid=${param.cid}" aria-label="Previous">
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
					<li><a href="${pageContext.request.contextPath }/product?method=findByPage&pageNumber=${n }&cid=${param.cid}">${n }</a></li>
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
					<a href="${pageContext.request.contextPath }/product?method=findByPage&pageNumber=${pb.pageNumber+1 }&cid=${param.cid}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:if>
		</ul>
	</div><!-- 分页结束======================= -->

	<!--
    	商品浏览记录:
    -->
	<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">
		<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
		<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
		<div style="clear: both;"></div>
		<div style="overflow: hidden;">
			<!-- 尚无尚未开发浏览记录 -->
			<%-- <ul style="list-style: none;">
				<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="${pageContext.request.contextPath}/products/1/cs10001.jpg" width="130px" height="130px" /></li>
			</ul> --%>
			假装你没有浏览记录哦~~
		</div>
	</div><!-- 商品浏览记录：结束 -->
	
</div>
<%@ include file="/jsp/foot.jsp" %>
</body>
</html>