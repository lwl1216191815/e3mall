Ext.define('SIMS.StudentGrid', {
    extend: 'Ext.grid.Panel',
    requires: ['SIMS.StudentModel'],
    title: '',
    margin: '',
    region: 'center',
    columnLines: true,
    multiSelect: true,
    selModel: new Ext.selection.CheckboxModel(),
    contextPath: '',
    width: 400,
    initComponent: function () {
        var me = this;
        this.addEvents({
            'addStudent' : true,
            'winShow' : true,
            'setWinTitle' : true,
            'detailInfo' : true
        });
        me.columns = [
            {
                xtype: 'rownumberer',
                text: '序号',
                width: 40
            },
            {
                text: 'id',
                dataIndex: 'id',
                width: 255
            },
            {
                text: '学生姓名',
                dataIndex: 'name',
                width: 80
            },
            {
                text: '学生性别',
                dataIndex: 'sex',
                width: 80,
                renderer : function (value) {
                    if(value == '0'){
                        return '男';
                    }else{
                        return '女';
                    }
                }
            },
            {
                text: '备注',
                dataIndex: 'remark',
                width: 250
            }
        ];
        me.store = Ext.create('Ext.data.Store', {
            model: 'SIMS.StudentModel',
            autoLoad: true,
            pageSize: 5,
            proxy: {
                type: 'ajax',
                url: me.contextPath + 'student/getStudent.action',
                reader: {
                    type: 'json',
                    root: ''
                }
            }
        });
        Ext.apply(this, {
            store: me.store,
            columns: me.columns,
            tbar: [
                {
                    text: '增加',
                    xType: 'button',
                    iconCls: 'icon-plus-sign-alt',
                    handler: function () {
                        me.addStudent();
                    }
                },
                {
                    text : '更改',
                    xtype : 'button',
                    iconCls : 'icon-edit',
                    handler : function(){
                        me.modifyStudent();
                    }
                }
            ]
        });
        me.callParent();
    },
    /**
     * 添加学生的处理方法
     * 触发显示窗口事件
     * 触发更换窗口标题事件
     */
   addStudent : function () {
       var me = this;
       me.fireEvent('winShow',me);
       me.fireEvent('setWinTitle','添加学生信息',me);
   },
    /**
     * 获取表单的值，并且更新store
     */
    queryData : function () {
        var me = this;
        me.store.loadPage(1);
    },
    /**
     * 更改学生信息的方法
     */
    modifyStudent : function () {
        var me = this;
        var selectData = me.getSelectedIds();
        if (selectData.length > 1) {
            Ext.Msg.alert("提示", "请选择一条记录操作", function (rep) {
                if (rep == 'ok') {
                    return;
                }
            });
        }else{
            var data = me.getSelectionModel().getLastSelected();
            me.fireEvent('detailInfo',data,me);
            me.fireEvent('winShow',me);
            me.fireEvent('setWinTitle','修改学生信息',me);
        }
    },
    /**
     * 获取当前选中对象的id
     */
    getSelectedIds : function(){
        var me = this;
        var rows = me.getSelectionModel().getSelection();
        if(rows.length == 0){
              Ext.Msg.alert("警告","请选择一条记录");
        }
        return rows;
    }
});