<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
	<%@ include file="/jsp/headhead.jsp" %>
	<!-- <title>商品详情</title> -->
<body>
	<!-- 这里被抽取了“菜单栏”和“导航条” -->
	<!-- 静态包含是代码过来再运行，动态包含是运行或再把结果过来 -->
	<!-- 动态包含的话，那边的包还要导一遍！！所以还是用静态包含 -->
	<%@ include file="/jsp/head.jsp" %>
<div class="container">

	<div class="row">
		<%-- <div style="border: 1px solid #e4e4e4;width:930px;margin-bottom:10px;margin:0 auto;padding:10px;margin-bottom:10px;">
			<a href="">首页&nbsp;&nbsp;&gt;</a>
			<a href="${pageContext.request.contextPath }/product?method=findByPage&pageNumber=1&cid=${cid}">${cname }&nbsp;&nbsp;&gt;</a>
			${pBean.pname }
		</div> --%>
		
		<div style="margin:0 auto;width:950px;">
		<div class="col-md-6">
			<img style="opacity: 1;width:400px;height:350px;" title="" class="medium" src="${pageContext.request.contextPath}/${pBean.pimage }">
		</div>

		<div class="col-md-6">
		<div><strong>${pBean.pname }</strong></div>
		<div style="border-bottom: 1px dotted #dddddd;width:350px;margin:10px 0 10px 0;">
			<div>编号：${pBean.pid }</div>
		</div>

		<div style="margin:10px 0 10px 0;">商城价: <strong style="color:#ef0101;">￥：${pBean.shop_price }元</strong> 市场价： <del>￥：${pBean.market_price }元</del>
		</div>

		<div style="margin:10px 0 10px 0;">促销: <a target="_blank" title="限时抢购 (2014-07-30 ~ 2015-01-01)" style="background-color: #f07373;">限时抢购</a> </div>

		<div style="padding:10px;border:1px solid #e7dbb1;width:330px;margin:15px 0 10px 0;;background-color: #fffee6;">
		
		<div style="margin:5px 0 10px 0;">库存：<strong id="stock" style="color:#ef0101;">${pBean.stock }</strong>件</div>

			<!-- 提交到购物车的表单 -->
			<form action="${pageContext.request.contextPath }/cart" id="form1" method="post" >
				<!-- 提交的方法 -->
				<input type="hidden" name="method" value="add2cart" >
				
				<!-- 商品的pid -->
				<input type="hidden" name="pid" value="${pBean.pid }" >
				
				<!-- 商品数量 -->
				<div style="border-bottom: 1px solid #faeac7;margin-top:20px;padding-left: 10px;">购买数量:
					<input id="count" name="count" value="1" maxlength="4" size="10" type="text" onblur="valid_count()" > </div>
					<!-- 记得带这里设置最大长度还有权限控制 -->
					<font id="t1" ></font>
				<div style="margin:20px 0 10px 0;;text-align: center;">
			</form>
			
				<input onclick="return subForm();" style="background: url('${pageContext.request.contextPath}/images/product.gif') no-repeat scroll 0 -600px rgba(0, 0, 0, 0);height:36px;width:127px;" value="加入购物车" type="submit">
				&nbsp;收藏商品</div>
		</div><!-- margin：和row同级好了 -->
		</div>
	</div><!-- row：结束 -->
		<div class="clear"></div>
		<div style="width:950px;margin:0 auto;">
			<div style="background-color:#d3d3d3;width:930px;padding:10px 10px;margin:10px 0 10px 0;">
				<strong>商品介绍</strong>
			</div>
	
			<div>
				<img src="${pageContext.request.contextPath}/${pBean.pimage }">
			</div>
	
			<div style="background-color:#d3d3d3;width:930px;padding:10px 10px;margin:10px 0 10px 0;">
				<strong>${pBean.pdesc }</strong>
			</div>
			
	
	</div>
</div>
<%@ include file="/jsp/foot.jsp" %>
<script type="text/javascript">
	function subForm() {
		var b = valid_count();
		if (!b) {
			return false;
		}
		//让指定的表单提交
		document.getElementById("form1").submit();
	}
	function valid_count() {
		var stock = document.getElementById("stock").innerHTML;
		var value = document.getElementById("count").value;
		
		value = value.trim();
		var pattern = /^\+?[1-9][0-9]*$/;
		if (!pattern.test(value)) {
			document.getElementById("t1").innerHTML = "商品数量只能是正整数呢";
			return false;
		}
		
		stock = parseInt(stock);
		value = parseInt(value);
		
		if (value > stock) {
			document.getElementById("t1").innerHTML = "库存不足了！";
			return false;
		}
		return true;
	}
</script>
</html>