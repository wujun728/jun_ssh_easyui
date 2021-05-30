<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
		<%@ include file="/include/header_bootstrap.jsp"%>
	<%@include file="/include/header_other.jsp" %>
	
	<script type="text/javascript">
	 	var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	
		  if(validateForm.form()){
			  loading('正在提交，请稍等...');
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function(){
			$("#name").focus();
			
			validateForm= $("#inputForm").validate({
				rules: {
					roleCode: {remote: "${ctx}/sys/role/checkRoleCode?oldRoleCode=" + encodeURIComponent("${role.roleCode}")+"&id=${role.id}"}
				},
				messages: {
					roleCode: {remote: "角色编码已存在"}
				},
				submitHandler: function(form){
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
			
			//在ready函数中预先调用一次远程校验函数，是一个无奈的回避案。(刘高峰）
			//否则打开修改对话框，不做任何更改直接submit,这时再触发远程校验，耗时较长，
			//submit函数在等待远程校验结果然后再提交，而layer对话框不会阻塞会直接关闭同时会销毁表单，因此submit没有提交就被销毁了导致提交表单失败。
			$("#inputForm").validate().element($("#name"));
			$("#inputForm").validate().element($("#roleCode"));
		
		});
	
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="role" autocomplete="off" action="${ctx}/sys/role/save" method="post" class="form-horizontal" >
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>角色:</label>
		         </td>
		         <td class="width-35">
					<form:input path="roleCode" htmlEscape="false" maxlength="50" class="form-control required"/>
				 </td>
				 
		         <td  class="width-15" class="active"><label class="pull-right"><font color="red">*</font>角色名称:</label>
		         </td>
		         <td class="width-35">
					<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required" />
				 </td>
		      </tr>
		  
		      <tr>
		         <td class="width-15 active"><label class="pull-right">数据范围:</label></td>
		         <td class="width-35">
		             <form:select path="dataScope" class="form-control ">
					    <form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline">特殊情况下，可进行跨机构授权</span>
				 <td class="width-15 active"><label class="pull-right">备注:</label></td>
		         <td class="width-35"><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control "/></td>
		      </tr>
			</tbody>
			</table>
	</form:form>
</body>
</html>