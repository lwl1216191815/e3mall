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
            'addStudent':true
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
                width: 150
            },
            {
                text: '学生姓名',
                dataIndex: 'name',
                width: 150
            },
            {
                text: '学生性别',
                dataIndex: 'sex',
                width: 150
            },
            {
                text: '备注',
                dataIndex: 'remark',
                width: 150
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
                    iconCls: 'icon-custom-add',
                    handler: function () {
                        me.addStudent();
                    }
                }
            ]
        });
        me.callParent();
    },
    /**
     * 添加学生的处理方法 ，触发事件
     */
   addStudent : function () {
       var me = this;
       me.fireEvent('addStudent',me);
   }


});