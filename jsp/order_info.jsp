<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html>
	<%@ include file="/jsp/headhead.jsp" %>
	<!-- <title>订单详情</title> -->
<body>
	<!-- 这里被抽取了“菜单栏”和“导航条” -->
	<!-- 静态包含是代码过来再运行，动态包含是运行或再把结果过来 -->
	<!-- 动态包含的话，那边的包还要导一遍！！所以还是用静态包含 -->
	<%@ include file="/jsp/head.jsp" %>
<div class="container">

	<div class="row">
		<div style="margin:0 auto;margin-top:10px;width:950px;">
			<strong>订单详情</strong>
			<table class="table table-bordered">
				<tbody>
					<tr class="warning">
						<th colspan="2">订单编号:${oBean.oid } </th>
						<th colspan="1">
							<c:if test="${oBean.state == 0 }">去付款</c:if>
							<c:if test="${oBean.state == 1 }">已付款</c:if>
							<c:if test="${oBean.state == 2 }">确认收货</c:if>
							<c:if test="${oBean.state == 3 }">已完成</c:if>
						</th>
						<th colspan="2">时间:<fmt:formatDate value="${oBean.ordertime }" pattern="yyyy-MM-dd HH:mm:ss" /></th>
					</tr>
					<tr class="warning">
						<th>图片</th>
						<th>商品</th>
						<th>价格</th>
						<th>数量</th>
						<th>小计</th>
					</tr>
					<c:forEach items="${oBean.items }" var="oi" >
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
			</table><!-- table：结束 -->
		</div>

		<div style="text-align:right;margin-right:120px;">
			商品金额: <strong style="color:#ff6600;">￥${oBean.total }元</strong>
		</div>
	</div><!-- row：结束 -->

	<div>
		<hr/>
		<form action="${pageContext.request.contextPath }/order" id="orderForm" method="post" class="form-horizontal" style="margin-top:5px;margin-left:150px;">
			<!-- 提交的方法 -->
			<input type="hidden" name="method" value="pay" >
			<!-- 订单号 -->
			<input type="hidden" name="oid" value="${oBean.oid }" >
			
			<div class="form-group">
				<label for="username" class="col-sm-1 control-label">地址</label>
				<div class="col-sm-5">
					<input type="text" name="address" class="form-control" id="address" placeholder="请输入收货地址" onblur="valid_address()" >
				</div>
				<font id="t1" >地址很重要！请认真填写，如有错误，后果自负！</font>
			</div>
			<div class="form-group">
				<label for="inputPassword3" class="col-sm-1 control-label">收货人</label>
				<div class="col-sm-5">
					<input type="text" name="name" class="form-control" id="name" placeholder="请输收货人" onblur="valid_name()" >
				</div>
				<font id="t2" ></font>
			</div>
			<div class="form-group">
				<label for="confirmpwd" class="col-sm-1 control-label">电话</label>
				<div class="col-sm-5">
					<input type="text" name="telephone" class="form-control" id="telephone" placeholder="请输入联系方式" onblur="valid_telephone()" >
				</div>
				<font id="t3" ></font>
			</div>
		<hr/>
			<div style="margin-top:5px;margin-left:150px;">
				<strong>选择银行：</strong>
				<p>
					<br/>
					<input type="radio" name="pd_FrpId" value="ICBC-NET-B2C" checked="checked" />工商银行
					<img src="${pageContext.request.contextPath}/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="BOC-NET-B2C" />中国银行
					<img src="${pageContext.request.contextPath}/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行
					<img src="${pageContext.request.contextPath}/bank_img/abc.bmp" align="middle" />
					<br/>
					<br/>
					<input type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行
					<img src="${pageContext.request.contextPath}/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
					<img src="${pageContext.request.contextPath}/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" />建设银行
					<img src="${pageContext.request.contextPath}/bank_img/ccb.bmp" align="middle" />
					<br/>
					<br/>
					<input type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行
					<img src="${pageContext.request.contextPath}/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
					<img src="${pageContext.request.contextPath}/bank_img/cmb.bmp" align="middle" />
				</p>
				<hr/>
				<p style="text-align:right;margin-right:100px;">
					<a href="javascript:void(0);" onclick="payOrder()" >
						<img src="${pageContext.request.contextPath}/images/finalbutton.gif" width="204" height="51" border="0" />
					</a>
				</p>
				<hr/>
			</div>
		</form><!-- form：结束 -->
	</div>

</div>
<%@ include file="/jsp/foot.jsp" %>
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
function valid_address() {
	var pattern = /^.{3,30}$/;
	var value = valid("address", pattern);
	if (!value) {
		document.getElementById("t1").innerHTML = "收货地址 必须3到30位！";
		return false;
	} else {
		document.getElementById("t1").innerHTML = "√";
		return true;
	}
}
function valid_name() {
	var pattern = /^.{1,20}$/;
	var value = valid("name", pattern);
	if (!value) {
		document.getElementById("t2").innerHTML = "收货人 姓名必须1到20位！";
		return false;
	} else {
		document.getElementById("t2").innerHTML = "√";
		return true;
	}
}
function valid_telephone() {
	var tPattern = /^1[34578]\d{9}$/;
	var value = valid("telephone", tPattern);
	if (!value) {
		document.getElementById("t3").innerHTML = "请认真填写收货人手机号!";
		return false;
	} else {
		document.getElementById("t3").innerHTML = "√";
		return true;
	}
}
function payOrder() {
	var f1 = valid_address();
	var f2 = valid_name();
	var f3 = valid_telephone();
	if (f1 && f2 && f3) {
		document.getElementById('orderForm').submit();
	} else {
		return false;
	}
}
</script>
</body>
</html>