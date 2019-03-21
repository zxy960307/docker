<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <base href="<%=basePath%>">
    <title>docker网页管理系统</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <link href="assets/css/basic.css" rel="stylesheet" />
    <link href="assets/css/custom.css" rel="stylesheet" />
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
<div id="wrapper">
    <jsp:include page="/pages/header.jsp"></jsp:include>

    <!-- 此处编写内容  -->
    <div id="page-wrapper">
        <div id="page-inner">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h5 class="panel-title">容器列表</h5>
                    <button type="button" id="create-container" class="btn btn-info col-md-offset-7">创建容器</button>
                    <button type="button" class="btn btn-warning col-md-offset-1">刷新容器信息
                    </button>
                </div>
                <div class="panel-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>container_id</th>
                            <th>status</th>
                            <th>image</th>
                            <th>创建者admin_id</th>
                            <th>创建时间</th>
                            <th>开放端口</th>
                            <th>ip地址</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${allContainers}" var="container">
                            <tr>
                                <td>${container.containerId}</td>
                                <td>${container.status}</td>
                                <td>${container.image}</td>
                                <td>${container.createAdminId}</td>
                                <td>${container.createTime}</td>
                                <td>22</td>
                                <td>127.0.0.1</td>
                                <td><div class="btn-group">
                                    <button type="button" class="btn btn-default dropdown-toggle btn-sm"
                                            data-toggle="dropdown">
                                        <span class="glyphicon glyphicon-wrench"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li>
                                            <a href="#">启动</a>
                                        </li>
                                        <li>
                                            <a href="#">暂停</a>
                                        </li>
                                        <li>
                                            <a href="#">重启</a>
                                        </li>
                                        <li>
                                            <a href="#">删除</a>
                                        </li>
                                    </ul>
                                </div></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/pages/footer.jsp"></jsp:include>
<script src="assets/js/jquery-1.10.2.js"></script>
<script src="assets/js/bootstrap.js"></script>
<script src="assets/js/jquery.metisMenu.js"></script>
<script src="assets/js/custom.js"></script>
<script>
//    $("#create-container").onclick(function(){
//        layer.open({
//            type:2,
//            content:,
//            title:'创建容器',
//        });
//    })
</script>
</body>
</html>