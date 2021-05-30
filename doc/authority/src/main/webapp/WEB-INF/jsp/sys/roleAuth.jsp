<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<!-- 全局js -->
    <script src="${pageContext.request.contextPath}/static/js/jquery/jquery-2.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery/jquery-migrate-1.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/plugins/jquery-validation/1.14.0/jquery.validate.min.js"></script>
	<%@include file="/include/header_treeview.jsp" %>
	<script type="text/javascript">

	  	var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }

		  return false;
		}
			
		$(document).ready(function(){
			$("#name").focus();
			validateForm = $("#inputForm").validate({
			
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${menuList}" var="menu">
					   {id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
		
		});
		
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/grant" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
		<form:hidden path="menuIds"/>
	</form:form>
</body>
</html>