<%@ taglib prefix="shior" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Wenline
  Date: 2020/12/26
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<shior:hasPermission name="企业管理">
    <button>企业管理</button><br/>
</shior:hasPermission>

<shior:hasPermission name="货运管理">
    <button>货运管理</button><br/>
</shior:hasPermission>
</body>
</html>
