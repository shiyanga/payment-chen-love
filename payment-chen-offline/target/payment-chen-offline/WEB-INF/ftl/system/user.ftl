<#assign sec=JspTaglibs["/WEB-INF/ftl/template/auth.tld"] />
<!DOCTYPE html>
<html class="gt-ie8 gt-ie9 not-ie">
<head>
    <title>权限管理-用户管理</title>
<#include "include/common-head.ftl"/>

    <style>
        .role-span-choice {
            background: #5BBB5B;
            color: white;
            padding: 3px 5px;
            margin-right: 5px;
            font-size: 8px;
        }

        span.label {
            margin-right: 5px;
        }
    </style>
    <script>
        var userDT;
        init.push(function () {
            //权限查询
            var hasUserDelAuth = true;
            var hasUserEditAuth =true;

            //data-table加载
            //data-table加载
            userDT = $('#user-dt').DataTable({
                "processing": true,
                "serverSide": true,
                "autoWidth": false,
                "searching": false,
                // "paging":false,
                // "dom": "lftr",
                "ajax": {
                    "url": "${basePath}/sys/authority/user/query",
                    "dataSrc": "result",
                    "type": "get",
                    "data": function (data) {
                        return "_method_=get&jsonParam=" + JSON.stringify(data);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $('#modals-alerts-danger').clone(true).modal('show');
                    },
                    complete: function (data) {
                        var jsonObj = JSON.parse(data.responseText);
                        if (jsonObj == null || jsonObj.resultCode == null || jsonObj.resultCode != '0000') {
                            var errormsg = '错误：' + jsonObj.errorMsg;
                            showErrorMsg(errormsg);
                            $("#user-dt tbody").html('<tr><td colspan="10"><span class="error-span">' + errormsg + '</span></td></tr>');
                            return false;
                        }
                    }
                },
                "columns": [
                    {"data": "id"},


                    {
                        "data": function (row) {
                            if (hasUserEditAuth) {
                                return '<a href="#" colname="employeeNumber" coltype="columnValue" data-url="${basePath}/sys/authority/user/update" userid="' + row.id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">' + encodeHtml(row.employeeNumber) + '</a>';
                            } else {
                                return encodeHtml(row.employeeNumber);
                            }
                        }
                    },


                    {
                        "data": function (row) {
                            if (hasUserEditAuth) {
                                return '<a href="#" colname="loginPassword" coltype="columnValue" data-url="${basePath}/sys/authority/user/update" userid="' + row.id + '"  data-pk="1" data-type="password" data-title="输入新的密码" class="editable-element editable editable-click">' + encodeHtml(row.loginPassword) + '</a>';
                            } else {
                                return encodeHtml(row.loginPassword);
                            }
                        }
                    },



                    {
                        "data": function (row) {
                            if (hasUserEditAuth) {
                                return '<a href="#" colname="name" coltype="columnValue" data-url="${basePath}/sys/authority/user/update" userid="' + row.id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">'
                                        + encodeHtml(row.name) +
                                        '</a>';
                            } else {
                                return encodeHtml(row.name);
                            }
                        }
                    },
                    {
                        "data": function (row) {
                            if (hasUserEditAuth) {
                                return '<a href="#" colname="email" coltype="columnValue" data-url="${basePath}/sys/authority/user/update" userid="' + row.id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">'
                                        + encodeHtml(row.email) +
                                        '</a>';
                            } else {
                                return encodeHtml(row.email);
                            }
                        }
                    },
                    {
                        "data": function (row) {
                            if (hasUserEditAuth) {
                                return '<a href="#" colname="department" coltype="columnValue" data-url="${basePath}/sys/authority/user/update" userid="' + row.id + '"  data-pk="1" data-type="select" data-title="输入修改的值" class="select-editable-element-department editable editable-click">'
                                        + encodeHtml(row.departmentDesc) +
                                        '</a>';
                            } else {
                                return encodeHtml(row.departmentDesc);
                            }
                        }
                    },
                    {
                        "data": function (row) {
                            var roleDiv = '';
                            var roles = row.roles;
                            if (roles != null && roles.length > 0) {
                                for (var role = 0; role < roles.length; ++role) {
                                    roleDiv = roleDiv + '<span class="label label-warning">' + encodeHtml(roles[role].name) + '</span>';
                                }
                            }
                            return roleDiv;
                        }
                    },
                    {
                        "data": function (row) {
                            if (hasUserEditAuth) {
                                return '<a href="#" colname="status" coltype="columnValue" data-url="${basePath}/sys/authority/user/update" userid="' + row.id + '"  data-pk="1" data-type="select" data-title="输入修改的值" class="select-editable-element-status editable editable-click">'
                                        + encodeHtml(row.statusDesc) +
                                        '</a>';
                            } else {
                                return encodeHtml(row.statusDesc);
                            }
                        }
                    },
                    {
                        "data": function (row) {
                            if (hasUserEditAuth) {
                                return '<a href="#" colname="remark" coltype="columnValue" data-url="${basePath}/sys/authority/user/update" userid="' + row.id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">'
                                        + safeString(row.remark) +
                                        '</a>';
                            } else {
                                return encodeHtml(row.remark);
                            }
                        }
                    },
                    {"data": "loginTime"},
                    {"data": "createTime"},
                    {
                        "data": function (row) {
                            return '查看详情';
                        }
                    }
                ],
                "columnDefs": [{
                    "targets": [10],
                    "render": function (data, type, full) {
                        var opHtml = '';
                        if (hasUserDelAuth) {
                            opHtml = '<button class="btn btn-outline btn-labeled btn-danger" onclick="deleteUser(this, \'' + full.id + '\')"><span class="btn-label icon fa  fa-trash-o"></span>删除</button>';
                        }
                        return opHtml;
                    }
                }],
                "initComplete": function (settings, json) {
                    $('#user-dt_wrapper .table-caption').text('用户管理');
                },
                "drawCallback": function (settings) {
                    $('#user-dt a.editable-element').editable({
                        params: function (params) {
                            params._method_ = "PATCH";
                            params.userId = $(this).attr("userid");
                            params.columnName = $(this).attr("colname");
                            params.columnValue = params.value;
                            return params;
                        },
                        success: function (response) {
                            if (response == null || '0000' != response.resultCode) {
                                $.growl.error({title: "更新失败", message: response.resultMessage});
                                return false;
                            } else {
                                $.growl.notice({title: "提示：", message: "操作成功！"});
                            }
                        }
                    });

                    $('#user-dt a.select-editable-element-department').editable({
                        prepend: "选择一个部门",
                        source: [
                            {value: 'LAD-HOTEL-DEVELOPMENT', text: '大住宿-酒店研发部'},
                            {value: 'LAD-HOTEL-RESERVE', text: '大住宿-酒店预订部'},
                            {value: 'LAD-PLATFORM-BUSINESS', text: '大住宿-平台商务部'},
                            {value: 'LAD-O2O-MANAGEMENT', text: '大住宿-O2O管理部'},
                            {value: 'LAD-CUSTOMER-SERVICE', text: '大住宿-客服部'},
                            {value: 'OTHER', text: '其它'}
                        ],
                        params: function (params) {
                            params._method_ = "PATCH";
                            params.userId = $(this).attr("userid");
                            params.columnName = $(this).attr("colname");
                            params.columnValue = params.value;
                            return params;
                        },
                        success: function (response) {
                            if (response == null || '0000' != response.resultCode) {
                                $.growl.error({title: "更新失败", message: response.resultMessage});
                                return false;
                            } else {
                                $.growl.notice({title: "提示：", message: "操作成功！"});
                            }
                        }
                    });

                    $('#user-dt a.select-editable-element-status').editable({
                        prepend: "选择一个状态",
                        source: [
                            {value: 1, text: '正常'},
                            {value: 2, text: '停用'},
                            {value: 3, text: '删除'}
                        ],
                        params: function (params) {
                            params._method_ = "PATCH";
                            params.userId = $(this).attr("userid");
                            params.columnName = $(this).attr("colname");
                            params.columnValue = params.value;
                            return params;
                        },
                        success: function (response) {
                            if (response == null || '0000' != response.resultCode) {
                                $.growl.error({title: "更新失败", message: response.resultMessage});
                                return false;
                            } else {
                                $.growl.notice({title: "提示：", message: "操作成功！"});
                            }
                        }
                    });
                },
                "order": [[9, 'desc']]
            });

            $("#user-search-btn").click(function () {
                userDT.ajax.reload(null, false);
            });
        });

        //用户删除
        function deleteUser(obj, userId) {
            if (userId == null) {
                return;
            }
            bootbox.confirm({
                message: "确定要删除此用户？ ",
                callback: function (result) {
                    if (result) {
                        $.ajax({
                            "url": "${basePath}/sys/authority/user/delete",
                            "data": {
                                "_method_": "DELETE",
                                "userId": userId
                            },
                            "type": "POST",
                            "error": function (response) {
                                $.growl.error({title: "异常", message: response.resultMessage});
                            },
                            "success": function (response) {
                                if (response == null || '0000' != response.resultCode) {
                                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                                    $.growl.error({title: "删除失败", message: msg});
                                } else {
                                    $.growl.notice({title: "提示：", message: "操作成功！"});
                                    location.reload();
                                }
                            }
                        });
                    }
                },
                className: "bootbox-sm"
            });
        }
    </script>
</head>
<body class="theme-default main-menu-animated">
<div id="main-wrapper">
<#include "include/common-menu.ftl" encoding="UTF-8">

    <div id="content-wrapper">
        <!-- 导航条 -->
        <ul class="breadcrumb breadcrumb-page">
            <li class="breadcrumb-label text-light-gray">You are here:</li>
            <li><a href="#">Home</a></li>
            <li class="active"><a href="#">用户管理</a></li>
        </ul>

        <!-- 主页面开始 -->
        <div class="row">
            <div class="panel-group" id="user-search-accordion">
                <div class="panel">
                    <div class="panel-heading">
                        <a class="accordion-toggle collapsed" data-toggle="collapse"
                           data-parent="#user-search-accordion" href="#user-search-collapse" style="font-weight: 600;">
                            <i class="panel-title-icon fa fa-tasks"></i>
                            <span class="panel-title">多条件查询</span>
                        </a>
                    </div>
                    <div class="panel-collapse collapse" id="user-search-collapse">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">xxx</label>
                                        <input type="email" name="email" class="form-control">
                                    </div>
                                </div>
                                <!-- col-sm-6 -->
                                <div class="col-sm-6">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">xxx</label>
                                        <input type="url" name="website" class="form-control">
                                    </div>
                                </div>
                                <!-- col-sm-6 -->
                            </div>
                            <!-- row -->
                        </div>
                    </div>
                    <div class="panel-footer text-right">
                        <button id="user-search-btn" class="btn btn-primary btn-labeled" data-toggle="modal">
                            <span class="btn-label icon fa fa-search"></span>查询
                        </button>
                    <#--<#if CtripAccount.ifUrlGranted('/sys/authority/user/add') == 'true'>-->
                        &nbsp;&nbsp;&nbsp;
                        <button class="btn btn-success btn-labeled" data-toggle="modal" data-target="#user-add-dialog">
                            <span class="btn-label icon fa fa-plus"></span>添加
                        </button>
                    <#--</#if>-->
                    </div>
                </div>
            </div>

            <div class="table-primary">
                <table id="user-dt" cellpadding="0" cellspacing="0" border="0"
                       class="table table-striped table-bordered dataTable no-footer">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>员工号</th>
                        <th>密码（已加密）</th>
                        <th>姓名</th>
                        <th>邮箱</th>
                        <th>部门</th>
                        <th>拥有的角色</th>
                        <th>状态</th>
                        <th>备注信息</th>
                        <th>最后登录时间</th>
                        <th>创建时间</th>
                        <th style="width:110px;">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
        <!-- 主页面结束 -->
        <hr class="no-grid-gutter-h grid-gutter-margin-b no-margin-t">
    </div>

</div>

<!-- 添加add 页面 -->
<#include "system/user-add.ftl" encoding="UTF-8">
<#include "include/common-bottom.ftl" encoding="UTF-8">

</body>
</html>
