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
                <h1 style="display:inline" class="h1 col-md-offset-1">镜像列表</h1>
                <%--<button type="button" class="btn btn-warning col-md-offset-8"  id="updateInfo" onclick="updateContainers()">--%>
                    <%--刷新容器信息--%>
                <%--</button>--%>
            </div>
            <div class="panel panel-default">
                <div class="panel-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>镜像id</th>
                            <th>镜像标签</th>
                            <th>机器ip</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${allImages}" var="image">
                            <tr>
                                <td>${image.imageId}</td>
                                <td>${image.repoTags}</td>
                                <td>${image.machineIp}</td>
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