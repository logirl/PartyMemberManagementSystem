<%--
  Created by IntelliJ IDEA.
  User: xinxi
  Date: 2016/10/23
  Time: 16:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改党员信息</title>
    <%@include file="/include.jsp" %>
    <style>
        .form-horizontal .form-label {
            text-align: right;
        }
    </style>
    <script>
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        $(function () {
            $('.skin-minimal input').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });
            $("#login-form").validate({
                rules: {
                    name: {
                        required: true
                    },
                    activistTime: {
                        required: true,
                        minlength: 10
                    },
                    developTime: {
                        required: true,
                        minlength: 10
                    },
                    score: {
                        required: true,
                        range: [0, 100],
                        digits: true
                    }
                },
                messages: {
                    activistTime: {
                        minlength: "这是必填字段"
                    },
                    developTime: {
                        minlength: "这是必填字段"
                    },
                    score: {
                        range: "必须是0-100的整数",
                        digits: "必须是0-100的整数"
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
                                    parent.window.location.reload();
                                    parent.layer.close(index);
                                }, 3000);
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
        <form action="${pageContext.request.contextPath }/info/modify" method="post" class="form form-horizontal"
              id="login-form">
            <input type="hidden" name="id" value="${info.id}"/>
            <input type="hidden" name="studentNumber" value="${info.studentNumber}"/>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">学号：</label>
                <div class="formControls col-xs-7 col-sm-7">
                    <input type="text" class="input-text" name="studentNumber" id="studentNumber" placeholder="学号是6位数字"
                           value="${info.studentNumber}" disabled="disabled">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">姓名：</label>
                <div class="formControls col-xs-7 col-sm-7">
                    <input type="text" class="input-text" name="name" id="name" value="${info.name}">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">积极分子时间：</label>
                <div class="formControls col-xs-3 col-sm-7">
                    <input type="text" onfocus="WdatePicker({skin:'whyGreen'})"
                           id="activistTime" class="input-text Wdate" name="activistTime" value="${info.activistTime}">
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">发展对象时间：</label>
                <div class="formControls col-xs-3 col-sm-7">
                    <input type="text" onfocus="WdatePicker({skin:'whyGreen'})"
                           id="developTime" class="input-text Wdate" name="developTime" value="${info.developTime}">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">批次：</label>
                <div class="formControls col-xs-3">
                    <span class="select-box">
                        <select id="batch" class="select" size="1" name="batch">
                            <option value="1" ${info.batch==1?"selected":""}>第一批</option>
                            <option value="2" ${info.batch==2?"selected":""}>第二批</option>
                            <option value="3" ${info.batch==3?"selected":""}>第三批</option>
                        </select>
				    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">预备党员：</label>
                <div class="formControls skin-minimal col-xs-5">
                    <div class="radio-box">
                        <input type="radio" id="status-1" name="status" value="1" ${info.status==1?"checked":""}>
                        <label for="status-1">是</label>
                    </div>
                    <div class="radio-box">
                        <input type="radio" id="status-2" name="status" value="0" ${info.status==0?"checked":""}>
                        <label for="status-2">否</label>
                    </div>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">党课成绩：</label>
                <div class="formControls col-xs-7 col-sm-7">
                    <input type="text" class="input-text" name="score" id="score" placeholder="0-100的整数"
                           value="${info.score}">
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                    <input class="btn btn-primary" type="submit" value="&nbsp;&nbsp;修改&nbsp;&nbsp;">
                </div>
            </div>
        </form>
    </section>
</div>
</body>
</html>