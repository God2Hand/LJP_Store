<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
	</HEAD>
	
	<body>
		<!--  -->
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/addProduct" method="post" enctype="multipart/form-data">
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>添加商品</STRONG>
						</strong>
					</td>
				</tr>

				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品名称：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="pname" value="" id="pname" class="bg" onblur="valid_pname()" />
						<font color="red" id="t1" ></font>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						是否热门：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<select name="is_hot">
							<option value="1">是</option>
							<option value="0">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						市场价格：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="market_price" value="" id="market_price" class="bg"  onblur="valid_market_price()" maxlength="15" />
						<font color="red" id="t2" ></font>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商城价格：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="shop_price" value="" id="shop_price" class="bg"  onblur="valid_shop_price()"  maxlength="15"  />
						<font color="red" id="t3" ></font>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品图片：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="file" name="pimage" id="pimage" accept=".jpg, .bmp, .jpeg, .png, .gif, .JPG, .BMP, .JPEG, .PNG, .GIF"  onblur="valid_file"  />
						<font color="red" id="t6" ></font>
					</td>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						库存：
					</td>
					<td class="ta_01" bgColor="#ffffff">
						<input type="text" name="stock" value=""${p.stock }  id="stock" class="bg"  onblur="valid_stock()"  maxlength="4"  />
						<font color="red" id="t4" ></font>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						所属分类：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<select name="cid">
							<c:forEach items="${list }" var="c" >
								<option value="${c.cid }">${c.cname }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						商品描述：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<textarea name="pdesc" id="pdesc" rows="5" cols="30" onblur="valid_pdesc()" ></textarea>
						<font color="red" id="t5" ></font>
					</td>
				</tr>
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="button" onclick="return valid_checkAll();" id="bu" value="确定" class="button_ok">
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
			function valid_pname() {
				var pPattern = /^.{1,50}$/;
				var value = valid("pname", pPattern);
				if (!value) {
					document.getElementById("t1").innerHTML = "商品至少有个名字吧？最长50个字符";
					return false;
				} else {
					document.getElementById("t1").innerHTML = "√";
					return true;
				}
			}
			function valid_market_price() {
				var pPattern = /^\d+(\.{0,1}\d+){0,1}$/;
				var value = valid("market_price", pPattern);
				if (!value) {
					document.getElementById("t2").innerHTML = "价格只能是非负数字呢";
					return false;
				} else {
					document.getElementById("t2").innerHTML = "√";
					return true;
				}
			}
			function valid_shop_price() {
				var pPattern = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
				var value = valid("shop_price", pPattern);
				if (!value) {
					document.getElementById("t3").innerHTML = "价格只能是非负数字呢";
					return false;
				} else {
					document.getElementById("t3").innerHTML = "√";
					return true;
				}
			}
			function valid_stock() {
				var pPattern = /^([+]?)\d*$/;
				var value = valid("stock", pPattern);
				if (!value) {
					document.getElementById("t4").innerHTML = "库存只能是非负整数呢";
					return false;
				} else {
					document.getElementById("t4").innerHTML = "√";
					return true;
				}
			}
			function valid_pdesc() {
				var len = document.getElementById("pdesc").value.length;
				if (len > 256) {
					document.getElementById("t5").innerHTML = "描述不要超过256个字符";
					return false;
				} else {
					document.getElementById("t5").innerHTML = "√";
					return true;
				}
			}
			function valid_file() {
				var len = document.getElementById("pimage").value.length;
				if (len <= 0) {
					document.getElementById("t6").innerHTML = "请选择图片！";
					return false;
				} else {
					document.getElementById("t6").innerHTML = "√";
					return true;
				}
			}
			function valid_checkAll() {
				var f1 = valid_pname();
				var f2 = valid_market_price();
				var f3 = valid_shop_price();
				var f4 = valid_stock();
				var f5 = valid_pdesc();
				var f6 = valid_file();
				if (f1 && f2 && f3 && f4 && f5 && f6) {
					document.Form1.submit();
					return true;
				} else {
					return false;
				}
			}
		</script>
	</body>
</HTML>