<%--
  Created by IntelliJ IDEA.
  User: Dragon
  Date: 2018/8/29
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="<%=basePath%>css/css/font-awesome.css" rel="stylesheet">
    <link href="<%=basePath%>css/ext/resources/css/ext-all.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>js/ext/ext-all.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ext/ext-lang-zh_CN.js" charset="UTF-8"></script>
</head>
<body>
</body>
</html>
