<!DOCTYPE html>
<html class="gt-ie8 gt-ie9 not-ie">
<head>
    <title>权限管理-角色管理</title>
<#include "include/common-head.ftl"/>

    <script>
        var roleDT;
        //权限检查
        var hasUserMangAuth = true;
        var hasAuthMang = true;
        var hasRoleEditAuth = true;
        var hasRoleDelAuth =true;

        init.push(function () {
            //data-table加载
            roleDT = $('#role-dt').DataTable({
                "processing": true,
                "serverSide": true,
                "autoWidth": false,
                "searching":false,
                "paging":false,
                "dom": "lftr",
                "ajax": {
                    "url": "${basePath}/sys/authority/role/query",
                    "dataSrc": "result",
                    "type": "GET",
                    "data": function (data) {
                        //data.queryType = 'group';
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
                            $("#role-dt tbody").html('<tr><td colspan="10"><span class="error-span">' + errormsg + '</span></td></tr>');
                            return false;
                        }
                    }
                },
                "columns": [
                    {"data": "id"},
                    {"data": function(row){
                        if(hasRoleEditAuth){
                            return '<a href="#" colname="name" coltype="columnValue" data-url="${basePath}/sys/authority/role/update" roleid="' + row.id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">'
                                    + encodeHtml(row.name) +
                                    '</a>';
                        }else{
                            return encodeHtml(row.name);
                        }
                    }},
                    {"data": function(row){
                        if(hasRoleEditAuth){
                            return '<a href="#" colname="description" coltype="columnValue" data-url="${basePath}/sys/authority/role/update" roleid="' + row.id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">'
                                    + encodeHtml(row.description) +
                                    '</a>';
                        }else{
                            return encodeHtml(row.description);
                        }
                    }},
                    {"data": "createTime"},
                    {"data": "updateTime"}
                ],
                "columnDefs": [{
                    "targets": [5],
                    "render": function (data, type, full) {
                        var btnDiv = '';
                        //权限检查
                        if (hasUserMangAuth){
                            btnDiv = btnDiv + '<button class="btn btn-labeled btn-primary" onclick="roleUserEdit(this, \'' + full.id + '\')">成员管理</button> &nbsp;&nbsp;';
                        }
                        if(hasAuthMang){
                            btnDiv = btnDiv + '<button class="btn btn-labeled btn-success" onclick="authRoleEdit(this, \'' + full.id + '\')"><span class="btn-label icon fa fa-edit"></span>权限管理</button>';
                        }
                        if (hasRoleDelAuth) {
                            btnDiv = btnDiv + '&nbsp;&nbsp;<button class="btn btn-outline btn-labeled btn-danger" onclick="deleteRole(this, \'' + full.id + '\')"><span class="btn-label icon fa  fa-trash-o"></span>删除</button>';
                        }
                        return btnDiv;
                    }
                }],
                "initComplete": function (settings, json) {
                    $('#role-dt_wrapper .table-caption').text('角色管理');
                    //$('#role-dt_wrapper .dataTables_filter input').attr('placeholder', '输入ID查找');
                },
                "drawCallback": function (settings) {
                    $('#role-dt a.editable-element').editable({
                        params: function (params) {
                            params._method_ = "PATCH";
                            params.roleId = $(this).attr("roleid");
                            params.columnName = $(this).attr("colname");
                            params.columnValue = params.value;
                            return params;
                        },
                        success: function (response) {
                            if (response == null || '0000' != response.resultCode) {
                                $.growl.error({title: "更新失败", message:  response.resultMessage});
                                return false;
                            } else {
                                $.growl.notice({title: "提示：", message: "操作成功！"});
                            }
                        }
                    });
                },
                "order": [[3, 'desc']]
            });

            $("#role-search-btn").click(function(){
                roleDT.ajax.reload(null, false);
            });
        });

        function deleteRole(obj, roleId) {
            if (roleId == null) {
                return;
            }
            bootbox.confirm({
                message: "确定要删除此角色？ ",
                callback: function (result) {
                    if (result) {
                        $.ajax({
                            "url": "${basePath}/sys/authority/role/delete",
                            "data": {
                                "_method_": "DELETE",
                                "roleId": roleId
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
            <li class="active"><a href="#">角色管理</a></li>
        </ul>

        <!-- 主页面开始 -->
        <div class="row">
            <ul id="uidemo-tabs-default-demo" class="nav nav-tabs">
                <li class="active">
                    <a href="#tab-role-manage" data-toggle="tab">角色管理</a>
                </li>
                <li class="">
                    <a href="#tab-role-user-manage" data-toggle="tab">角色-用户管理</a>
                </li>
            </ul>

            <div class="tab-content tab-content-bordered">
                <div class="tab-pane fade active in" id="tab-role-manage">
                    <div class="panel-group" id="role-search-accordion">
                        <div class="panel">
                            <div class="panel-heading">
                                <a class="accordion-toggle collapsed" data-toggle="collapse"
                                   data-parent="#role-search-accordion" href="#role-search-collapse"
                                   style="font-weight: 600;">
                                    <i class="panel-title-icon fa fa-tasks"></i>
                                    <span class="panel-title">多条件查询</span>
                                </a>
                            </div>
                            <div class="panel-collapse collapse" id="role-search-collapse">
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
                                <button id="role-search-btn" class="btn btn-primary btn-labeled" data-toggle="modal">
                                    <span class="btn-label icon fa fa-search"></span>查询
                                </button>
                                &nbsp;&nbsp;&nbsp;
                            <#--<#if CtripAccount.ifUrlGranted('/sys/authority/role/add') == 'true'>-->
                                <button class="btn btn-success btn-labeled" data-toggle="modal"
                                        data-target="#role-add-dialog">
                                    <span class="btn-label icon fa fa-plus"></span>添加
                                </button>
                            <#--</#if>-->
                            </div>
                        </div>
                    </div>
                    <div class="table-primary">
                        <table id="role-dt" cellpadding="0" cellspacing="0" border="0"
                               class="table table-hover table-striped table-bordered dataTable no-footer"
                               aria-describedby="jq-datatables-example_info">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>名称</th>
                                <th>描述</th>
                            <#--<th>状态</th>-->
                                <th>创建时间</th>
                                <th>最后修改时间</th>
                                <th style="width:300px;">操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
                <div class="tab-pane fade" id="tab-role-user-manage">
                    TODO
                </div>
            </div>
        </div>

        <!-- 主页面结束 -->
        <hr class="no-grid-gutter-h grid-gutter-margin-b no-margin-t">
    </div>

</div>

<!-- 添加add edit页面 -->
<#include "system/role-add.ftl" encoding="UTF-8">
<#include "system/role-edit.ftl" encoding="UTF-8">

<#include "include/common-bottom.ftl" encoding="UTF-8">

</body>
</html>
