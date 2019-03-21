<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单</title>
<link href="${pageContext.request.contextPath}/css/left.css" rel="stylesheet" type="text/css"/>
<link rel="StyleSheet" href="${pageContext.request.contextPath}/css/dtree.css" type="text/css" />
</head>
<body>
<table width="100" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="100%" border="0">
  <tr>
    <td>
<div class="dtree">

	<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
	<script type="text/javascript">
	
		d = new dTree('d');
		d.add('01',-1,'系统菜单树');

		d.add('0101','01','分类管理','','','mainFrame');
		d.add('010201','0101','分类列表','${pageContext.request.contextPath}/adminCategory?method=findAll','','mainFrame');
		d.add('010202','0101','添加分类','${pageContext.request.contextPath}/adminCategory?method=addUI','','mainFrame');
		d.add('010203','0101','已删除分类','${pageContext.request.contextPath}/adminCategory?method=findDel','','mainFrame');
		
		d.add('0103','01','商品管理');
		d.add('010401','0103','已上架商品列表','${pageContext.request.contextPath}/adminProduct?method=findAll','','mainFrame');
		d.add('010402','0103','下架商品列表','${pageContext.request.contextPath}/adminProduct?method=findPflag','','mainFrame');
		d.add('010403','0103','添加上架商品','${pageContext.request.contextPath}/adminProduct?method=addUI','','mainFrame');
		d.add('010404','0103','已删除商品','${pageContext.request.contextPath}/adminProduct?method=findDel','','mainFrame');
		
		d.add('0105','01','订单管理');
		d.add('010501','0105','订单列表','${pageContext.request.contextPath}/adminOrder?method=findAllByState','','mainFrame');
		d.add('010502','0105','未付款订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=0','','mainFrame');
		d.add('010503','0105','已付款订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=1','','mainFrame');
		d.add('010504','0105','已发货订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=2','','mainFrame');
		d.add('010505','0105','已完成订单','${pageContext.request.contextPath}/adminOrder?method=findAllByState&state=3and4','','mainFrame');
		
		d.add('0107','01','用户管理','','','mainFrame');
		d.add('010701','0107','用户列表','${pageContext.request.contextPath}/adminUser?method=findAll','','mainFrame');
		
		d.add('0109','01','管理员管理','','','mainFrame');
		d.add('010901','0109','普通管理员列表','${pageContext.request.contextPath}/manager?method=findAllNormal','','mainFrame');
		d.add('010902','0109','修改密码','${pageContext.request.contextPath}/manager?method=pwdUI','','mainFrame');
		d.add('010903','0109','重新登录','${pageContext.request.contextPath}/houtai','','mainFrame');
		
		d.add('0111','01','备份与还原','','','mainFrame');
		d.add('011101','0111','备份数据库','${pageContext.request.contextPath}/manager?method=backupUI','','mainFrame');
		d.add('011102','0111','还原数据库','${pageContext.request.contextPath}/manager?method=restoreUI','','mainFrame');
		
		document.write(d);
		
	</script>
</div>	</td>
  </tr>
</table>
</body>
</html>
