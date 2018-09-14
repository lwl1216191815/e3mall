<%--
  Created by IntelliJ IDEA.
  User: Dragon
  Date: 2018/8/29
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <jsp:include page="extResource.jsp"></jsp:include>
    <script type="text/javascript" src="<%=basePath%>js/student/studentModel.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/student/studentGrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/student/studentWin.js"></script>
    <title>学生数据</title>
</head>
<body>
<script type="text/javascript">
    var contextPath = '<%=basePath%>';
    Ext.onReady(function () {
        Ext.QuickTips.init();
        init();

        function init() {
            /**
             * 创建自定义StudentGrid
             * @type {SIMS.StudentGrid}
             */
            var grid = Ext.create('SIMS.StudentGrid', {
                contextPath: contextPath,
                margin: '5 10 15 20',
                listeners: {
                    'winShow': function () {
                        win.show();
                    },
                    'setWinTitle': function (title) {
                        win.setTitle(title);
                    },
                    'detailInfo': function (record) {
                            win.reviewData(record);
                    }

                }
            });
            /**
             * 创建自定义弹出窗口StudentWin
             * @type {SIMS.StudentWin}
             */
            var win = Ext.create('SIMS.StudentWin', {
                contextPath: contextPath,
                region: 'center',
                listeners: {
                    'refreshData': function () {
                        grid.queryData();
                    }
                }
            });
            /**
             * 将组件放到jsp页面上
             */
            Ext.create('Ext.Viewport', {
                layout: 'border',
                items: [grid]
            });
        }
    })
</script>
</body>
</html>
