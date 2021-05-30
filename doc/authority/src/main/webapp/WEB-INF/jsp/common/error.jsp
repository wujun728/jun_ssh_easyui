<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html lang="zh">
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title> Error 页面</title>
   <%@ include file="/include/header_bootstrap.jsp"%>
</head>

<body class="gray-bg">

    <div class="middle-box text-center animated fadeInDown">
        <h1>Error</h1>
        <h3 class="font-bold">页面异常！<a href="javascript:history.back()">返回</a></h3>

        <div class="error-desc">
            页面异常 ~
              <!-- 
            <form class="form-inline m-t" role="form">
                <div class="form-group">
              
                    <input type="email" class="form-control" placeholder="请输入您需要查找的内容 …">
                   
                </div>
                <button type="submit" class="btn btn-primary">搜索</button>
            </form>
              -->
        </div>
    </div>

</body>

</html>
