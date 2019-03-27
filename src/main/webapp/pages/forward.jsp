<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path +"/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>">
    <title>Docker网页管理系统</title>
    <link href="assets/layer/theme/default/layer.css">
    <script type="text/javascript" src="assets/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="assets/layer/layer.js"></script>
</head>
<body>
<script type="text/javascript">
    $(function(){
        var icons;
        if("${msgStatus}"=="true")
        {
            icons=6;
        }
        else
        {
            icons=5;
        }
        layer.alert("${msg}", {
            icon:icons
        },function () {
            window.location="<%=basePath%>${url}";
        });
    })

</script>
</body>
</html>
