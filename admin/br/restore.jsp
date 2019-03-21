<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	</HEAD>
	
	<body>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/restore" method="post" enctype="multipart/form-data" >
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>数据库 还原</STRONG>
						</strong>
					</td>
					<td>
						<!-- 这个是为了不要enter自动提交 -->
						<input type='text' style='display:none'/>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="right" colspan="2" >
						<button type="button" value="刷新" class="button_add" onclick="location.href='${pageContext.request.contextPath}/manager?method=restoreUI';" >刷新</button>
					</td>
				</tr>
				<tr>
					<td width="45%" align="right" bgColor="#f5fafe" class="ta_01">
						请选择还原的备份：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input type="file" id="f" name="f" accept=".sql" onblur="valid_f()" >
						<font color="red" id="t1" ></font>
					</td>
				</tr>
			
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="button" onclick="restore()" id="button" value="还原" class="button_ok">
							还原
						</button>
					</td>
				</tr>
			</table>
		</form>
		<div align="center" >
			<font color="red" id="msg" >${msg }</font>
		</div>
		<script type="text/javascript">
			function valid_f() {
				var len = document.getElementById("f").value.length;
				if (len <= 0) {
					document.getElementById("t1").innerHTML = "请选择备份文件！";
					return false;
				} else {
					document.getElementById("t1").innerHTML = "√";
					return true;
				}
			}
			function restore() {
				if (valid_f()) {
					if (confirm("确认还原吗？可能会导致当前数据库内容丢失！建议先备份！")) {
						if (confirm("再次确认还原吗？真的考虑好后果了吗？请先备份当前数据库吧！")) {
							if (confirm("最后一次确认：还原吗？")) {
								document.getElementById("msg").innerHTML = "正在进行还原...";
								document.Form1.submit();
							}
						}
					}
				}
			}
		</script>
	</body>
</HTML>