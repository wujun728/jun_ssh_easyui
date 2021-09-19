<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<title>ajax file upload</title>
<script type="text/javascript" src="<c:url value='/static/js/upload/ajaxfileupload.js'/>"></script>
<script type="text/javascript" src="<c:url value='/static/js/upload/ajax.js'/>"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<img id="loading" src="${ctx }/static/images/loading.gif" style="display:none;">
			<input type="file" id="ajaxuploadfile" name="ajaxuploadfile">
		</div>
		<br />
		<div class="row">
			<button type="button" class="btn btn-default" id="ajaxupload-btn">上传</button>
		</div>
		<div id="msg">
		
		</div>
	</div>

</body>
</html>
