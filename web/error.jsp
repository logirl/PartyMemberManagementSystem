<%--
  Created by IntelliJ IDEA.
  User: xinxi
  Date: 2016/10/22
  Time: 15:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>服务器忙</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/layer/layer.js"></script>
    <style>
        .layui-layer-dialog .layui-layer-content {
            line-height: 50px;
            font-size: 20px;
            background-color: rgba(251, 166, 200, 0.72);
            color: #ff0055;
        }

    </style>

</head>

<body>
<script>
    setTimeout(function () {
        window.location.href = "/";
    }, 5000);
    layer.msg('对不起，服务器忙，请稍候再试', {shade: 0.3,time:5000});
</script>
</body>

</html>
