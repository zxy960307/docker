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
        <div id="page-inner" style="padding: 50px 100px 0px;">
            <div class="panel panel-primary" >
                <div class="panel-heading">
                    <h3 class="panel-title">创建机器选项</h3>
                </div>
                <div class="panel-body">
                    <div style="padding: 50px 100px 10px;">
                        <form class="bs-example bs-example-form" role="form" method="get" action="/machine/createMachine">
                        <div class="input-group col-lg-8 col-md-offset-2">
                            <span class="input-group-addon">命名</span>
                            <input type="text" class="form-control " placeholder="twitterhandle" name="name">
                        </div>
                        <br>
                            <div class="input-group col-lg-8 col-md-offset-2">
                                <span class="input-group-addon">ip</span>
                                <input type="text" class="form-control " placeholder="twitterhandle" name="ip">
                            </div>
                            </br>
                            <div class="input-group col-lg-6 col-md-offset-2">
                                <div class="col-lg-8 col-md-offset-2">
                                    <button type="submit" class="btn btn-lg btn-info col-md-offset-2 btn-block">创建</button>
                                </div>
                            </div>
                        </form>
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
<script>
</script>
</body>
</html>