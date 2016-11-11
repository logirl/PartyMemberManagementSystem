<%--
  Created by IntelliJ IDEA.
  User: xinxi
  Date: 2016/10/22
  Time: 10:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>注册</title>
    <%@include file="/include.jsp" %>
    <style>
        .form-horizontal .form-label {
            text-align: right;
        }
    </style>
    <script>
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        $(function () {
            $("#login-form").validate({
                rules: {
                    name: {
                        required: true
                    },
                    password: {
                        required: true,
                        minlength: 6,
                        maxlength: 12
                    }
                },
                submitHandler: function (form) {
                    $.post($(form).attr("action"), $(form).serialize(), function (ret) {
                        switch (ret.code) {
                            case 0:
                                parent.layer.msg(ret.data, {shade: 0});
                                break;
                            case 1:
                                setTimeout(function () {
                                    parent.layer.close(index);
                                }, 3000);
                                parent.window.location.reload();
                                parent.layer.msg(ret.data, {shade: 0});
                                break;
                            case 2:
                                break;
                            default:
                                break;
                        }
                        return false;
                    }, "json");
                    return false;
                }
            });
        })
    </script>
</head>
<body>
<div style="margin-top: 50px">
    <section class="container">
        <form action="${pageContext.request.contextPath }/login" method="post" class="form form-horizontal"
              id="login-form">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">用户名：</label>
                <div class="formControls col-xs-7 col-sm-7">
                    <input type="text" class="input-text" name="name" id="username">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">密码：</label>
                <div class="formControls col-xs-7 col-sm-7">
                    <input type="password" class="input-text" autocomplete="off" placeholder="密码必须是6-12位"
                           name="password"
                           id="password">
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                    <input class="btn btn-primary" type="submit" value="&nbsp;&nbsp;登录&nbsp;&nbsp;">
                </div>
            </div>
        </form>
    </section>
</div>
</body>
</html>
