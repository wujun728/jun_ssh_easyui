<%@page import="com.lostad.app.common.util.RequestUtil"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>Cluster App Session Test</title>
</head>
<body>
	Server IP:
	<%
	out.println( com.lostad.app.common.util.RequestUtil.getRemoteAddr(request) + "<br>");
	out.println("<br/> SESSIONID: <font color=red>" + session.getId() + "</font><br/>");
	String dataName = request.getParameter("dataName");

	if (dataName != null && dataName.length() > 0) {
		String dataValue = request.getParameter("dataValue");
		session.setAttribute(dataName, dataValue);
	}

	out.print("<br/><b>Session Info:</b><br/>");

	Enumeration e = session.getAttributeNames();

	while (e.hasMoreElements()) {
		String name = (String) e.nextElement();
		String value = session.getAttribute(name).toString();
		out.println(name + " = " + value );
		System.out.println(name + " = " + value);
	}
	out.print("<br/><br/>Form");
%>
	<form action="" method="POST">
		&nbsp; name: <input type=text size=20 name="dataName"> 
		&nbsp; value: <input type=text size=20 name="dataValue">  <input type=submit>
	</form>
	
	<p>
	 JVM初始分配的堆内存由-Xms指定，默认是物理内存的1/64；
   <p>JVM最大分配的堆内存由-Xmx指定，默认是物理内存的1/4。
   <p>默认空余堆内存小于40%时，JVM就会增大堆直到-Xmx的最大限制；
    <p>空余堆内存大于70%时，JVM会减少堆直到-Xms的最小限制。
    <p>因此服务器一般设置 -Xms、-Xmx <font color=red> 相等 </font>以避免在每次GC 后调整堆的大小。
	
	<p>
	<font color=red>
	<%="JVM最大允许内存(-xmx):\n "+(Runtime.getRuntime().maxMemory()/1024/1024)
		+"m, <b>已分配内存: "+(Runtime.getRuntime().totalMemory()/1024/1024)
		+"m,  <p><b>已分配内存中的剩余空间: "+(Runtime.getRuntime().freeMemory()/1024/1024)
		+"m,  <b>最大可用内存: "+((Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()
				+Runtime.getRuntime().freeMemory())/1024/1024)+"m" %>
	</font>	
	
	<br>
	<br>
	<br>
	<b>
	<a href="/swagger-ui.html#"> 接口文档 </a>
</body>
</html>