<!DOCTYPE html>
<!--[if IE 8]>         <html class="ie8"> <![endif]-->
<!--[if IE 9]>         <html class="ie9 gt-ie8"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="gt-ie8 gt-ie9 not-ie"> <!--<![endif]-->
<head>
    <title>DB数据修改</title>
<#include "include/common-head.ftl"/>
    <link href="${basePath}/resources/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css">

</head>
<body class="theme-default main-menu-animated">
<div id="main-wrapper">
<#include "include/common-menu.ftl" encoding="UTF-8">

    <div id="content-wrapper">
        <!-- 导航条 -->
        <ul class="breadcrumb breadcrumb-page">
            <div class="breadcrumb-label text-light-gray">You are here:</div>
            <li><a href="#">Home</a></li>
            <li class="active"><a href="#">DB数据修改</a></li>
        </ul>

        <div class="row">
            <div class="panel-group" id="role-search-accordion">


                <div class="panel">
                    <div class="panel-heading">
                        <a class="accordion-toggle" data-toggle="collapse"
                           data-parent="#role-search-accordion"
                           href="#searchV2-collapse" style="font-weight: 600;">
                            <i class="panel-title-icon fa fa-tasks"></i>
                            <span class="panel-title">多条件查询</span>
                        </a>
                    </div>
                    <div class="panel-collapse collapse in" id="searchV2-collapse"
                         style="height: auto;">
                        <div class="panel-body">
                            <div class="row">

                                <div class="col-sm-3">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">数据库</label>
                                        <div class="select2-success select2-disabled-examples select2-colors-examples">
                                            <select id="db-select"
                                                    class="form-control select2-offscreen"
                                                    tabindex="-1">
                                                <option value="2" selected="">BNB_ORDER_DB</option>
                                                <option value="1">GROUP_WORMHOLE_DB</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-sm-3">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">数据库表名</label>
                                        <input id="dbTableName-input" type="text" class="form-control"
                                               placeholder="数据库表名">
                                    </div>
                                </div>

                                <div class="col-sm-3">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">查询字段名称</label>
                                        <input id="query-field-name" type="text" class="form-control"
                                               placeholder="唯一标识的查询字段名称">
                                    </div>
                                </div>

                                <div class="col-sm-3">
                                    <div class="form-group no-margin-hr">
                                        <label class="control-label">查询字段值</label>
                                        <input id="query-field-value" type="text" class="form-control"
                                               placeholder="唯一标识的查询字段值">
                                    </div>
                                </div>


                            </div>
                        </div>


                        <div class="panel-footer text-right">
                            <button class="btn btn-labeled btn-danger"
                                    onclick="clearSelectRecordConditions();"
                                    style="margin-right:30px;">
                                <span class="btn-label icon fa  fa-trash-o"></span>清空
                            </button>
                            <button id="searchDbRecord-btn" class="btn btn-primary">点击查询</button>
                        </div>
                    </div>
                </div>


                <div id="record-panel" class="panel" style="display: none">
                <#--<div class="panel-heading">
                    <span class="panel-title" style="font-weight: bolder;">DB数据修改</span>
                </div>-->

                    <div class="panel-body">
                        <table id="db-record-table" class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <td>字段名称</td>
                                <td>字段值</td>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- Page wide horizontal line -->
        <hr class="no-grid-gutter-h grid-gutter-margin-b no-margin-t">
    </div>
    <!-- / #content-wrapper -->
    <!-- / #main-wrapper -->
<#include "include/common-bottom.ftl" encoding="UTF-8">
    <script>

        $(document).ready(function () {
            $("#db-select").select2();

            $('#searchDbRecord-btn').click(function () {
                queryTargetDbRecord();
            });
        });

        var fillQueryRecordResult = function (dbRowResults) {
            if (dbRowResults.length < 1) {
                $.growl.notice({title: "提示：", message: "该条件下不存在记录。"});
                return;
            }
            if (dbRowResults.length > 1) {
                $.growl.notice({title: "提示：", message: "该条件下有不止一条记录，不能同时修改多条记录。"});
                return;
            }

            var $tbody = $('#db-record-table').find('tbody');
            $tbody.empty();
            var record = dbRowResults[0];
            for (var property in record) {
                if (record.hasOwnProperty(property)) {
                    var name = '<td>' + property + '</td>';

                    var value = '<td><a href="#" data-name="' + property + '" data-title="输入修改后的值" class="editable editable-click">'
                            + record[property] + '</a></td>';
                    $tbody.append('<tr>' + name + value + '</tr>')
                }
            }
            initAllEditableField();
            $('#record-panel').show();
        };

        var initAllEditableField = function () {
            $('table a.editable').editable({
                type: 'text',
                pk: 1,
                url: '${basePath}/system/dbupdate/updateOneRecord',
                ajaxOptions: {
                     type: 'post'
                 },
                params: function (params) {
                    //originally params contain pk, name and value
                    params._method_ = "post";

                    var tmp = {};
                    tmp.query = {
                        'dbEnumCode': $('#db-select').val(),
                        'tableName': $('#dbTableName-input').val(),
                        'queryFieldName': $('#query-field-name').val(),
                        'queryFieldValue': $('#query-field-value').val()
                    };
                    tmp.updateFieldName = params.name;
                    tmp.updateFieldValue = params.value;

                    params.param = JSON.stringify(tmp);
                    return params;
                },
                success: function (response, newValue) {
                    console.log(JSON.stringify(response));
                    if (!response) {
                        console.log('没有修改权限');
                    } else if ("0000" != response.resultCode) {
                        $.growl.error({
                            title: "修改失败：", message: response.resultMessage
                        });
                    } else {
                        $.growl.notice({title: "提示：", message: "修改成功！"});
                    }
                },
                error: function (response, newValue) {
                    $.growl.error({title: "修改失败：", message: response.resultMessage});
                }
            });
        };

        var queryTargetDbRecord = function () {
            var query = {
                'dbEnumCode': $('#db-select').val(),
                'tableName': $('#dbTableName-input').val(),
                'queryFieldName': $('#query-field-name').val(),
                'queryFieldValue': $('#query-field-value').val()
            };
            $.ajax({
                "url": "${basePath}/system/dbupdate/queryOneRecord?query=" + JSON.stringify(query),
                "dataType": "JSON",
                "contentType": "application/json",
                "type": "GET",
                "error": function () {
                },
                "success": function (response) {
                    if (response != null && '0000' != response.resultCode) {
                        $.growl.error({title: "操作失败：", message: response.resultMessage});
                    } else {
                        //$.growl.notice({title: "提示：", message: "操作成功"});
                        console.log(JSON.stringify(response.result));
                        fillQueryRecordResult(JSON.parse(response.result));
                    }
                }
            });
        };

        var clearSelectRecordConditions = function () {
            $('#dbTableName-input').val("");
            $('#query-field-name').val("");
            $('#query-field-value').val("");
        }
    </script>
</div>
</body>
</html>