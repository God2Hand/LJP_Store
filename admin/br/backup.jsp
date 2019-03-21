<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	</HEAD>
	
	<body>
		<form id="Form1" name="Form1">
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>数据库 备份</STRONG>
						</strong>
					</td>
					<td>
						<!-- 这个是为了不要enter自动提交 -->
						<input type='text' style='display:none'/>
					</td>
				</tr>
				<tr>
					<td class="ta_01" align="right" colspan="2" >
						<button type="button" value="刷新" class="button_add" onclick="location.href='${pageContext.request.contextPath}/manager?method=backupUI';" >刷新</button>
					</td>
				</tr>
				<tr>
					<td width="45%" align="right" bgColor="#f5fafe" class="ta_01">
						请输入文件名：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input type="text" name="filename" id="filename" class="bg" onblur="valid_filename()" />
						<font color="red" id="t1" ></font>
					</td>
				</tr>
			
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="button" onclick="backup()" id="button" value="备份" class="button_ok">
							备份
						</button>
					</td>
				</tr>
			</table>
		</form>
		<div align="left" >
			<font color="red" id="msg" >${msg }</font>
		</div>
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
			function valid_filename() {
				var uPattern = /^.{2,32}$/;
				var value = valid("filename", uPattern);
				if (!value) {
					document.getElementById("t1").innerHTML = "文件名名必须2到32位";
					return false;
				} else {
					document.getElementById("t1").innerHTML = "√";
					return true;
				}
			}
			function backup() {
			if (valid_filename()) {
				var filename = document.getElementById("filename").value;
				filename = filename.trim();
				location.href="${pageContext.request.contextPath }/manager?method=backup&filename=" + filename;
				document.getElementById("msg").innerHTML = "等待备份下载...（下载完成后请右上角刷新 O(∩_∩)O）";
			}
		}
		</script>
	</body>
</HTML>