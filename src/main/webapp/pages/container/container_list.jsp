<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="my" uri="/WEB-INF/returnstatusstr.tld"%>
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
            <div style="padding: 10px 10px 20px 10px">
                <h1 style="display:inline" class="h1 col-md-offset-1">容器列表</h1>
                <button type="button" class="btn btn-warning col-md-offset-8"  id="updateInfo" onclick="updateContainers()">
                    刷新容器信息
                </button>
            </div>
            <div class="panel panel-default">
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
                                <td>${my:returnstatusstr(container.status)}</td>
                                <%--<td>${container.status}</td>--%>
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
                                        <c:if test="${container.status==0 ||container.status==2 }">
                                            <li>
                                                <a href="#">启动</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${container.status==1 }">
                                            <li>
                                                <a href="#">暂停</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${container.status==3}">
                                            <li>
                                                <a href="#">取消暂停</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${container.status==0 ||container.status==2 }">
                                            <li>
                                                <a href="#">重启</a>
                                            </li>
                                        </c:if>
                                            <li>
                                                <a href="#">删除</a>
                                            </li>
                                    </ul>
                                </div></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="col-md-5 col-md-offset-4">
                        <jsp:include page="/pages/split_bar.jsp"></jsp:include>

                    </div>
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
<script type="text/javascript">
    function updateContainers(){
        //alert("1");
        $.ajax({
            url:"${basePath}"+"container/updateContainers",
            type:"GET",
            success:function(data){
                alert(JSON.stringify(data));
                window.location.href="${bastPath}"+"container/getAllContainers";
            },
            error:function(data){
                console.log(data);
                window.location.href="${bastPath}"+"container/getAllContainers";
            }
        });
    }
</script>
</body>
</html>