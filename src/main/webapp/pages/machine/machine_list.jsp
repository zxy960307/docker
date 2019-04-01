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
                <h1 style="display:inline" class="h1 col-md-offset-1">机器列表</h1>
                <%--<button type="button" class="btn btn-warning col-md-offset-8"  id="updateInfo" onclick="updateContainers()">--%>
                    <%--刷新容器信息--%>
                <%--</button>--%>
            </div>
            <div class="panel panel-default">
                <div class="panel-body" style="padding: 10px 40px 20px 10px">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>name</th>
                            <th>ip</th>
                            <th>status</th>
                            <th>网络状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${allMachines}" var="machine">
                            <tr>
                                <td>${machine.id}</td>
                                <td>${machine.name}</td>
                                <td>${machine.ip}</td>
                                <c:if test="${machine.status == 1}">
                                    <td>正常</td>
                                </c:if>
                                <c:if test="${machine.status == 0}">
                                    <td>异常</td>
                                </c:if>
                                <c:if test="${machine.pingFlag == true}">
                                    <td>网络畅通</td>
                                </c:if>
                                <c:if test="${machine.pingFlag == false}">
                                    <td>网络阻塞</td>
                                </c:if>
                                <td>
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-default dropdown-toggle btn-sm"
                                                data-toggle="dropdown">
                                            <span class="glyphicon glyphicon-wrench"></span>
                                        </button>
                                        <ul class="dropdown-menu" role="menu">
                                                <li>
                                                    <a href="">改变机器状态</a>
                                                </li>
                                                <li>
                                                    <a href="/machine/deleteMachine?id=${machine.id}">删除机器</a>
                                                </li>
                                        </ul>
                                    </div>
                                </td>
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
    <%--window.onload = function(){--%>
        <%--if("${alertFlag}" == "true")--%>
                <%--alert("${msg}");--%>
    <%--};--%>
    function updateContainers(){
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