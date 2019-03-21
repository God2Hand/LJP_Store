<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
	<%@ include file="/jsp/headhead.jsp" %>
	<title>商城首页</title>
<body>
	<!-- 这里被抽取了“菜单栏”和“导航条” -->
	<!-- 静态包含是代码过来再运行，动态包含是运行或再把结果过来 -->
	<!-- 动态包含的话，那边的包还要导一遍！！所以还是用静态包含 -->
	<%@ include file="/jsp/head.jsp" %>
<div class="container-fluid">
		
		
			<!--
            	时间：2018-11-25
            	描述：轮播条
            -->
			<div class="container-fluid">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<div class="item active">
							<img src="${pageContext.request.contextPath}/img/1.jpg">
							<div class="carousel-caption">

							</div>
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/img/2.jpg">
							<div class="carousel-caption">

							</div>
						</div>
						<div class="item">
							<img src="${pageContext.request.contextPath}/img/3.jpg">
							<div class="carousel-caption">

							</div>
						</div>
					</div>

					<!-- Controls -->
					<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div><!-- 轮播条：结束 -->
			
			<!--
            	时间：2018-11-25
            	描述：商品显示-热门商品
            -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>热门商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="${pageContext.request.contextPath}/products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
				</div>
				<div class="col-md-10">
					<!-- 广告位招租 -->
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="">
							<img src="${pageContext.request.contextPath}/products/hao/middle01.jpg" width="516px" height="200px" style="display: inline-block;">
						</a>
					</div>
				
					<!-- 展示从数据库查到的商品 -->
					<c:forEach items="${hList }" var="p" >
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath }/product?method=getById&pid=${p.pid }">
								<img src="${pageContext.request.contextPath}/${p.pimage}" width="130" height="130" style="display: inline-block;">
							</a>
							<!-- 这里把名字截取一下，不然太长排版不好。用了functions库 -->
							<p><a href="product_info.html" style='color:#666'>${fn:substring(p.pname, 0, 10) }..</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${p.shop_price }</font></p>
						</div>
					</c:forEach>
				</div>
			</div><!-- 商品显示-热门商品：结束 -->
			
			<!--
            	时间：2018-11-25
            	描述：广告部分
            -->
            <div class="container-fluid">
				<img src="${pageContext.request.contextPath}/products/hao/ad.jpg" width="100%"/>
			</div><!-- 广告部分：结束 -->
			
			<!--
            	时间：2018-11-25
            	描述：商品显示-最新商品
            -->
			<div class="container-fluid">
				<div class="col-md-12">
					<h2>最新商品&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/img/title2.jpg"/></h2>
				</div>
				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;">
					<img src="${pageContext.request.contextPath}/products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/>
				</div>
				<div class="col-md-10">
					<!-- 广告位招租 -->
					<div class="col-md-6" style="text-align:center;height:200px;padding:0px;">
						<a href="">
							<img src="${pageContext.request.contextPath}/products/hao/middle02.jpg" width="516px" height="200px" style="display: inline-block;">
						</a>
					</div>
				
					<!-- 展示从数据库查到的商品 -->
					<c:forEach items="${nList }" var="p" >
						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;">
							<a href="${pageContext.request.contextPath }/product?method=getById&pid=${p.pid }">
								<img src="${pageContext.request.contextPath}/${p.pimage}" width="130" height="130" style="display: inline-block;">
							</a>
							<!-- 这里把名字截取一下，不然太长排版不好。用了functions库 -->
							<p><a href="product_info.html" style='color:#666'>${fn:substring(p.pname, 0, 10) }..</a></p>
							<p><font color="#E4393C" style="font-size:16px">&yen;${p.shop_price }</font></p>
						</div>
					</c:forEach>
				</div>
			</div><!-- 商品显示-最新商品:结束 -->
					
</div>
<%@ include file="/jsp/foot.jsp" %>
</body>
</html>