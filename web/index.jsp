<%--
  Created by IntelliJ IDEA.
  User: xinxi
  Date: 2016/10/21
  Time: 21:42
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>学生党员管理系统</title>
    <%@include file="include.jsp" %>
    <style type="text/css">
        html, body {
            height: 100%;
        }

        .icheckbox-blue {
            margin-top: -25%;
            margin-left: 10%;
        }
    </style>
    <script>
        /*
         参数解释：
         title	标题
         url		请求的url
         id		需要操作的数据id
         w		弹出层宽度（缺省调默认值）
         h		弹出层高度（缺省调默认值）
         */
        function layer_show(title, url, w, h) {
            if (title == null || title == '') {
                title = false;
            }
            if (url == null || url == '') {
                url = "404.html";
            }
            if (w == null || w == '') {
                w = 800;
            }
            if (h == null || h == '') {
                h = ($(window).height() - 50);
            }
            layer.open({
                type: 2,
                area: [w + 'px', h + 'px'],
                fix: false, //不固定
                maxmin: true,
                shade: 0.4,
                title: title,
                scrollbar: false,
                content: url
            });
        }
        $(function () {
            $(".register").click(function () {
                layer_show('注册', '/register.jsp', '550', '350');
            });
            $(".login").click(function () {
                layer_show('登录', '/login.jsp', '450', '300');
            });
            <c:if test="${not empty user}">
            $(".add-btn").click(function () {
                layer_show('添加', '/info/add.jsp', '750', '550');
            });
            $(".batch-delete").click(function () {
                var ids = '';
                $.each($(".icheckbox-blue"), function (i, n) {
                    if ($(n).hasClass("checked")) {
                        ids += $(n).children().val() + ',';
                    }
                });
                if (ids.length > 0) {
                    ids = ids.substring(0, ids.length - 1);
                    $.post("/info/batchDelete", {ids: ids}, function (ret) {
                        switch (ret.code) {
                            case 0:
                                layer.msg(ret.data, {shade: 0.3});
                                break;
                            case 1:
                                layer.msg(ret.data, {shade: 0.3});
                                setTimeout(function () {
                                    window.location.reload();
                                }, 3000);
                                break;
                            case 2:
                                break;
                            default:
                                break;
                        }
                        return false;
                    }, "json");
                }
            });
            //            $.post("/info/allinfos", {}, function (ret) {
            //                if (ret.code == 1) {
            //                    if (ret.data.totalrecords < 1) {
            //                        $("#content").append('没有党员信息');
            //                    } else {
            //                        $("#content").append('<thead><tr>' +
            //                                '<th class="col1">学号</th>' +
            //                                '<th class="col2">姓名</th>' +
            //                                '<th class="col3">积极分子时间</th>' +
            //                                '<th class="col4">发展对象时间</th>' +
            //                                '<th class="col5">批次</th>' +
            //                                '<th class="col6">预备党员</th>' +
            //                                '<th class="col7">党课成绩</th>' +
            //                                '<th class="col8">操作</th>' +
            //                                '</tr></thead>');
            //                        $("#content").append('<tbody>');
            //                        $.each(ret.data.records, function (i, n) {
            //                            if (n.status == 1) {
            //                                n.status = '是';
            //                            } else {
            //                                n.status = '否';
            //                            }
            //                            $("#content").append('<tr>' +
            //                                    '<td class="col1">' + n.studentNumber + '</td>' +
            //                                    '<td class="col2">' + n.name + '</td>' +
            //                                    '<td class="col3">' + n.activistTime + '</td>' +
            //                                    '<td class="col4">' + n.developTime + '</td>' +
            //                                    '<td class="col5">第' + n.batch + '批</td>' +
            //                                    '<td class="col6">' + n.status + '</td>' +
            //                                    '<td class="col7">' + n.score + '</td>' +
            //                                    '<td class="col8">' +
            //                                    '<button class="btn btn-primary radius btn-modity" type="button" value="' + n.id + '">修改</button>' +
            //                                    '&nbsp;&nbsp;' +
            //                                    '<button class="btn btn-danger radius btn-delete" type="button" value="' + n.id + '">删除</button></td>' +
            //                                    '</tr>');
            //                        });
            //                        $("#content").append('</tbody>');
            //                        $(".btn-modity").click(function () {
            //                            layer_show('修改', '/info/toModify?id=' + $(this).val(), '750', '550');
            //                        });
            //                        $(".btn-delete").click(function () {
            //                            $.post("/info/delete", {id: $(this).val()}, function (ret) {
            //                                if (ret.code == 1) {
            //                                    layer.msg(ret.data, {shade: 0.3});
            //                                    setTimeout(function () {
            //                                        window.location.reload();
            //                                    }, 3000);
            //                                } else {
            //                                    layer.msg(ret.data, {shade: 0.3});
            //                                }
            //                            });
            //                        });
            //                    }
            //                }
            //            }, "json");
            function demo(curr) {
                $.getJSON('info/getPage', {pagenum: curr || 1}, function (ret) {
                    if (ret.code == 1) {
                        if (ret.data.totalrecords < 1) {
                            $("#content").append('没有党员信息');
                        } else {
                            $("#content").html("");
                            $("#content").append('<thead><tr>' +
                                    <c:if test="${user.flag==1}">
                                    '<th class="col0" style="width: 50px">选择</th>' +
                                    </c:if>
                                    '<th class="col1">学号</th>' +
                                    '<th class="col2">姓名</th>' +
                                    '<th class="col3">积极分子时间</th>' +
                                    '<th class="col4">发展对象时间</th>' +
                                    '<th class="col5">批次</th>' +
                                    '<th class="col6">预备党员</th>' +
                                    '<th class="col7">党课成绩</th>' +
                                    '<th class="col8">操作</th>' +
                                    '</tr></thead>');
                            $("#content").append('<tbody>');
                            $.each(ret.data.records, function (i, n) {
                                if (n.status == 1) {
                                    n.status = '是';
                                } else {
                                    n.status = '否';
                                }
                                $("#content").append('<tr>' +
                                        <c:if test="${user.flag==1}">
                                        '<td class="col0" style="position: relative">' +
                                        '<div class="skin-minimal">' +
                                        '<div class="check-box">' +
                                        '<input type="checkbox"  id="checkbox-1" name="checked" value="' + n.id + '" style="position: absolute"/>' +
                                        '</div>' +
                                        '</div>' +
                                        '</td>' +
                                        </c:if>
                                        '<td class="col1">' + n.studentNumber + '</td>' +
                                        '<td class="col2">' + n.name + '</td>' +
                                        '<td class="col3">' + n.activistTime + '</td>' +
                                        '<td class="col4">' + n.developTime + '</td>' +
                                        '<td class="col5">第' + n.batch + '批</td>' +
                                        '<td class="col6">' + n.status + '</td>' +
                                        '<td class="col7">' + n.score + '</td>' +
                                        '<td class="col8">' +
                                        '<button class="btn btn-primary radius btn-modity" type="button" value="' + n.id + '">修改</button>' +
                                        <c:if test="${user.flag==1}">
                                        '&nbsp;&nbsp;' +
                                        '<button class="btn btn-danger radius btn-delete" type="button" value="' + n.id + '">删除</button></td>' +
                                        </c:if>
                                        '</tr>');
                            });
                            $("#content").append('</tbody>');
                            $('.skin-minimal input').iCheck({
                                checkboxClass: 'icheckbox-blue',
                                radioClass: 'iradio-blue',
                                increaseArea: '40%'
                            });
                            $(".btn-modity").click(function () {
                                layer_show('修改', '/info/toModify?id=' + $(this).val(), '750', '550');
                            });
                            $(".btn-delete").click(function () {
                                $.post("/info/delete", {id: $(this).val()}, function (ret) {
                                    if (ret.code == 1) {
                                        layer.msg(ret.data, {shade: 0.3});
                                        setTimeout(function () {
                                            window.location.reload();
                                        }, 3000);
                                    } else {
                                        layer.msg(ret.data, {shade: 0.3});
                                    }
                                });
                            });
                            laypage({
                                cont: 'layPage', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：&lt;div id="page1">&lt;/div>
                                pages: ret.data.totalpage, //通过后台拿到的总页数
                                curr: curr || 1, //初始化当前页
                                jump: function (obj, first) { //触发分页后的回调
                                    if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                        demo(obj.curr);
                                    }
                                }
                            });
                            $("#theTotalCount").html(ret.data.totalrecords);
                        }
                    }
                });
            }

            demo();
            </c:if>
        })

    </script>
</head>
<body>
<div class="content">
    <div class="content-header">
        <div class="text-c" style="margin-top: auto;"><h2>学生党员管理系统</h2></div>
    </div>
    <div class="content-body">
        <div class="content-test">
            <c:if test="${empty user}">
                您还没有登录，请先登录后再进行操作。
                <button class="register btn btn-secondary-outline radius">注册</button>
                <button class="login btn btn-success-outline radius">登录</button>
            </c:if>
            <c:if test="${not empty user}">
                欢迎您：${user.name}&nbsp;&nbsp;&nbsp;<a href="/logout" class="btn btn-warning-outline radius">注销</a>
                <div class="cl pd-5 bg-2 bk-gray mt-20">
                    <span class="l">
                        <c:if test="${user.flag==1}">
                        <a href="javascript:;" class="btn btn-danger radius batch-delete">
                            <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
                        </c:if>
                        <a href="javascript:;" class="btn btn-primary radius add-btn">
                            <i class="Hui-iconfont">&#xe600;</i> 添加</a>
                    </span>
                    <span class="r">共有数据：<strong id="theTotalCount"></strong> 条</span>
                </div>
                <table id="content" class="table table-border table-bordered table-striped mt-20">

                </table>
                <div id="layPage" class="text-c"></div>
            </c:if>
        </div>
    </div>
</div>
<div class="footer-container">
    <footer class="footer mt-20">
        <div class="container-fluid">
            <nav><a href="#" target="_blank">关于我们</a> <span class="pipe">|</span> <a href="#" target="_blank">联系我们</a>
                <span class="pipe">|</span> <a href="#" target="_blank">法律声明</a></nav>
            <p>Copyright &copy;2016 logirl.cc All Rights Reserved. <br>
                <a href="http://www.miitbeian.gov.cn/" target="_blank" rel="nofollow">京ICP备1000000号</a><br>
            </p>
        </div>
    </footer>
</div>


</body>
</html>
