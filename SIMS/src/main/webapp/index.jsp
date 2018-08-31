<%--
  Created by IntelliJ IDEA.
  User: Dragon
  Date: 2018/8/28
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <link href="<%=basePath%>css/theme-classic-all.css" rel="stylesheet">
    <script type="text/javascript" src="<%=basePath%>js/ext/ext-all.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/student/studentModel.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/student/studentGrid.js"></script>
    <title>首页</title>
</head>
<body>
<script type="text/javascript">
    Ext.define('StudentModel',{
        extend : 'Ext.data.Model',
        fields:[
            {name : 'id'},
            {name : 'name'},
            {name:'sex'},
            {name : 'remark'}]
    });
    Ext.onReady(function(){
        var  myStore=Ext.create('Ext.data.Store',{
            model:'StudentModel',
            autoLoad:true,
            proxy:{
                type:'ajax',
                method:'post',
                url:'<%=basePath%>student/getStudent.action',
                reader:{
                    type:'json',
                    root:''
                }
            }
        });
        Ext.create('Ext.grid.Panel',{
            id : 'firstGrid',
            title : '学生信息',
            store:myStore,
            renderTo:Ext.getBody(),
            columns:[
                {
                    text:'学生姓名',
                    dataIndex:'name',
                    width:150
                }
                ]
        });
    });
</script>
</body>
</html>
