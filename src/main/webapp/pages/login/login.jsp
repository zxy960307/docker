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
    <title>login</title>
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <link href="assets/css/basic.css" rel="stylesheet" />
    <link href="assets/css/custom.css" rel="stylesheet" />
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body style="background-color: #E2E2E2;">
<div class="container">
    <div class="row text-center " style="padding-top:100px;">
        <div class="col-md-12">
            <h2>docker网页管理系统</h2>
        </div>
        <div class="col-md-6 col-md-offset-3">
            <hr style="width:50%"/>
            <h4 >管理员登录</h4>
        </div>
    </div>

    <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">

        <div class="panel-body">
            <form id="loginForm" role="form"  action="<%=basePath%>login/doLogin" method="post">
                <br />
                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-user"  ></i></span>
                    <input type="text" id="id" class="form-control" placeholder="Your Username "  name="userName"  />
                </div>
                <div class="form-group input-group">
                    <span class="input-group-addon"><i class="fa fa-lock"  ></i></span>
                    <input type="password" class="form-control"  placeholder="Your Password" name="password" id="password" />
                </div>

                <button type="submit" class="btn btn-primary col-md-offset-7">登陆</button>
            </form>
        </div>

    </div>
</div>
</div>
</body>
</html>