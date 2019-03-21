<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<!--
    	时间：2018-11-25
    	描述：菜单栏
    -->
	<div class="container-fluid">
		<div class="col-md-4">
			<a href="${pageContext.request.contextPath}/">
				<img src="${pageContext.request.contextPath}/img/logo2.png" />
			</a>
		</div>
		<div class="col-md-5">
			<img src="${pageContext.request.contextPath}/img/header.png" />
		</div>
		<div class="col-md-3" style="padding-top:20px">
			<ol class="list-inline">
				<c:if test="${empty user }">
					<li><a href="${pageContext.request.contextPath }/user?method=loginUI">登录</a></li>
					<li><a href="${pageContext.request.contextPath }/user?method=registUI">注册</a></li>
					<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
				</c:if>
				<c:if test="${not empty user }">
					${user.name }:你好！
					<li><a href="${pageContext.request.contextPath }/user?method=logout">注销</a></li>
					<br />
					<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
					<li><a href="${pageContext.request.contextPath }/order?method=findMyOrdersByPage&pageNumber=1">我的订单</a></li>
					<li>
						<a href="${pageContext.request.contextPath }/jsp/forgetPwd.jsp?uid=${user.uid }">忘记密码</a>
						/
						<a href="${pageContext.request.contextPath }/jsp/changePwd.jsp?uid=${user.uid }">修改密码</a>
					</li>
				</c:if>
			</ol>
		</div>
	</div>
	<!--
          	时间：2018-11-25
          	描述：导航条
          -->
	<div class="container-fluid">
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="${pageContext.request.contextPath }">首页</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav" id="c_ul" >
					</ul>
					<form class="navbar-form navbar-right" role="search" action="${pageContext.request.contextPath }/product" method="post" >
						<input hidden="hidden" name="method" value="search" >
						<div class="form-group">
							<input type="text" class="form-control" placeholder="请输入商品名称..." name="searchName" >
						</div>
						<button type="submit" class="btn btn-default">Search</button>
					</form>

				</div>
				<!-- /.navbar-collapse -->
			</div>
			<!-- /.container-fluid -->
		</nav>
	</div>

<script type="text/javascript">
	$(function(){
		//发送ajax，查询所有的分类
		$.post("${pageContext.request.contextPath}/category", {"method":"findAll"}, function(obj){
			//alert(obj);
			//遍历json列表，获取每一个分类，包装成li标签，插入到ul内部
			//$.each(${obj}, function(){});
			$(obj).each(function(){
				$("#c_ul").append("<li><a href='${pageContext.request.contextPath}/product?method=findByPage&pageNumber=1&cid=" + this.cid + "&cname=" + this.cname +"'>" + this.cname + "</a></li>");
			});
		}, "json");
	});
</script>