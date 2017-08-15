<!DOCTYPE html>
<!--[if IE 8]>         <html class="ie8"> <![endif]-->
<!--[if IE 9]>         <html class="ie9 gt-ie8"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="gt-ie8 gt-ie9 not-ie"> <!--<![endif]-->
<head>
    <meta charset="UTF-8">
    <title>系统管理-Key-Value配置</title>
<#include "include/common-head.ftl"/>

    <style type="text/css">
        #jq-datatable td {
            max-width: 300px;
        }

        .editable-input {
            max-width: 800px;
        }

        .editable-input textarea {
            min-width: 300px;
        }

        #jq-datatable td {
            word-break: break-all;
        }
    </style>
</head>

<body class="theme-default main-menu-animated">

<script>var init = [];</script>

<div id="main-wrapper">
<#include "include/common-menu.ftl" encoding="UTF-8">

    <div id="content-wrapper">
        <!-- 导航条 -->
        <ul class="breadcrumb breadcrumb-page">
            <div class="breadcrumb-label text-light-gray">You are here:</div>
            <li><a href="#">Home</a></li>
            <li class="active"><a href="#">Configure</a></li>
        </ul>
        <div class="row">
            <script>

                init.push(function () {
                    datatable = $('#jq-datatable').DataTable({
                        "processing": true,
                        "serverSide": true,
                        "autoWidth": false,
                        "ajax": {
                            "url": "${basePath}/sys/configure/query",
                            "dataSrc": "result",
                            "data": function (data) {
                                var keyArray = new Array();
                                var keyStr = $("#key-input").val();
                                if (keyStr != null && keyStr.length > 0) {
                                    keyArray = keyStr.split(",");
                                    data.key = keyArray;
                                }
                                var groupArray = new Array();
                                var groupStr = $("#group-input").val();
                                if (groupStr != null && groupStr.length > 0) {
                                    groupArray = groupStr.split(",");
                                    data.group = groupArray;
                                }
                                return "jsonParam=" + JSON.stringify(data);
                            }
                        },
                        "columns": [
                            {"data": "id"},
                            {
                                "data": function(row){
                                    return encodeHtml(row.group);
                                }
                            },
                            {
                                "data": function(row){
                                    return encodeHtml(row.key);
                                }
                            },
                            {
                                "data": function (row) {
                                    //权限检查
                                    <#--if(!${CtripAccount.ifUrlGranted('/sys/configure/update')}){-->
                                        <#--return encodeHtml(row.value);-->
                                    <#--}-->
                                    if (row.group == '5051_OPERATION_TIPS') {
                                        return '<div style="cursor: pointer;overflow: scroll;" coltype="value" data-url="${basePath}/sys/configure/update" conid="' + row.id + '"  data-type="textarea" data-pk="1" data-title="输入修改的值" class="rich-input-editable editable editable-click">' + encodeHtml(row.value) + '</div>';
                                    } else {
                                        return '<a href="#" coltype="value" data-url="${basePath}/sys/configure/update" conid="' + row.id + '"  data-type="textarea" data-pk="1" data-title="输入修改的值" class="common-input-editable editable editable-click">' + encodeHtml(row.value) + '</a>';
                                    }
                                }
                            },
                            {
                                "data": function (row) {
                                    //权限检查
                                    <#--if(!${CtripAccount.ifUrlGranted('/sys/configure/update')}){-->
                                        <#--return encodeHtml(row.description);-->
                                    <#--}-->
                                    return '<a href="#" coltype="desc" data-url="${basePath}/sys/configure/update" conid="' + row.id + '"  data-type="textarea" data-pk="1" data-title="输入修改的描述信息" class="common-input-editable editable editable-click">' + encodeHtml(row.description) + '</a>';
                                }
                            },
                            {"data": "createTime", "dateFormat": "yy-mm-dd"},
                            {"data": "updateTime"}
                        ],
                        "columnDefs": [{
                            "targets": [7],
                            "render": function (data, type, full) {
                                if (true) {
                                    return ' <button class="btn btn-outline btn-labeled btn-danger" onclick="deleteConfigItem(\'' + full.id + '\',\'' + full.key + '\')"><span class="btn-label icon fa  fa-trash-o"></span>删除</button>';
                                }else{
                                    return '';
                                }
                            }
                        }],
                        "initComplete": function (settings, json) {
                            $('#jq-datatable_wrapper .table-caption').text('配置列表');
                            $('#jq-datatable_wrapper .dataTables_filter input').attr('placeholder', '输入ID查找');
                        }, "drawCallback": function (settings) {
                            //个性化编辑显示
                            $(".common-input-editable").editable({
                                params: function (params) {
                                    params._method_ = "PATCH";
                                    params.conId = $(this).attr("conid");
                                    params.conValue = decodeHtml(params.value);
                                    params.conColumn = ($(this).attr("coltype") == "value") ? "value" : "desc";
                                    return params;
                                },
                                onblur: 'ignore',
                                success: function (response) {
                                    if (response != null && '0000' == response.resultCode) {
                                        $.growl.notice({title: "提示：", message: "更新成功！"});
                                    } else {
                                        $.growl.error({title: "更新失败：", message: response.resultMessage});
                                    }
                                }
                            });

                            //个性化编辑显示
                            $(".rich-input-editable").editable({
                                params: function (params) {
                                    params._method_ = "PATCH";
                                    params.conId = $(this).attr("conid");

                                    if ($(this).attr("coltype") == "value") {
                                        params.conValue = $(this).next("div.editable-container").find("textarea").code();
                                        params.conColumn = "value";
                                    } else {
                                        params.conValue = params.value;
                                        params.conColumn = "desc";
                                    }

                                    return params;
                                },
                                savenochange: true,
                                onblur: 'ignore',
                                success: function (response) {
                                    if (response != null && '0000' == response.resultCode) {
                                        $(this).html("sss");
                                        $.growl.notice({title: "提示：", message: "更新成功！"});
                                    } else {
                                        $.growl.error({title: "更新失败：", message: response.resultMessage});
                                    }
                                },
                                display: function (value, sourceData) {
                                    var valueD = $(this).next("div.editable-container").find("textarea").code();
                                    if (valueD != null && valueD.length > 0) {
                                        $(this).html(valueD);
                                    }
                                }
                            });

                            $('.rich-input-editable').on('shown', function (e, editable) {
                                $(this).next("div.editable-container").find("textarea").summernote({
                                    height: 200,
                                    tabsize: 2,
                                    codemirror: {
                                        theme: 'monokai'
                                    }
                                }).code($(this).html());
                            });
                        }
                    });
                    $("#search-btn").click(function () {
                        datatable.ajax.url("${basePath}/sys/configure/query").load();
                    });

                });
                function deleteConfigItem(recId, key) {
                    if (recId == null || key == null) {
                        return;
                    }
                    bootbox.confirm({
                        message: "确定要删除【" + key + "】？",
                        callback: function (result) {
                            if (result) {
                                $.ajax({
                                    "url": "${basePath}/sys/configure/delete?primaryKey=" + recId,
                                    "type": "delete",
                                    "error": function () {
                                    },
                                    "success": function (response) {
                                        if (response != null && '0000' != response.resultCode) {
                                            $.growl.error({title: "Update Failed：", message: response.resultMessage});
                                        } else {
                                            location.reload();
                                        }
                                    }
                                });
                            }
                        },
                        className: "bootbox-sm"
                    });
                }
                //刷新权限缓存
                function refreshAuthority() {
                    $.ajax({
                        "url": "${basePath}/sys/configure/cache/refresh",
                        "type": "get",
                        "error": function () {
                        },
                        "success": function (response) {
                            if (response != null && '0000' != response.resultCode) {
                                $.growl.error({title: "Refresh Failed：", message: response.resultMessage});
                            } else {
                                location.reload();
                            }
                        }
                    });
                }
            </script>

            <div class="panel">
                <div class="panel-heading">
                    <span class="panel-title" style="font-weight: bolder;">Key-Value 配置</span>
                </div>

                <table class="table search-table" id="inputs-table" style="margin: 10px 0px 0px 15px;">
                    <tbody>
                    <tr>
                        <td style="width: 60px;vertical-align:middle;">组名：</td>
                        <td style="width: 400px;vertical-align:middle;"><input id="group-input" type="text"
                                                                               class="form-control"
                                                                               placeholder="如输入多个，请用,分隔"></td>
                        <td style="width: 30px;vertical-align:middle;">键：</td>
                        <td style="width: 400px;vertical-align:middle;"><input id="key-input" type="text"
                                                                               class="form-control"
                                                                               placeholder="如输入多个，请用,分隔"></td>
                        <td style="vertical-align:middle;">
                            <button type="button" id="search-btn" class="btn btn-primary" style="width: 70px;">查询
                            </button>
                        </td>
                        <td>

                            <#include "system/configurePopup.ftl" encoding="UTF-8">
                            <div class="pull-right col-xs-12 col-sm-auto" style="margin-right:40px;">
                                <button class="btn btn-primary btn-labeled" data-toggle="modal"
                                        data-target="#addConfigureDialog">
                                    <span class="btn-label icon fa fa-plus"></span>添加
                                </button>
                            </div>
                        </td>
                        <td>
                        <#if CtripAccount.ifUrlGranted('/sys/configure/cache/refresh') == 'true'>
                            <div class="pull-right col-xs-12 col-sm-auto" style="margin-right:40px;">
                                <button class="btn" onclick="javascript:refreshAuthority();">
                                    <span class="btn-label icon fa fa-refresh"></span>刷新缓存
                                </button>
                            </div>
                        </#if>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="panel-body">
                    <div class="table-primary">
                        <table id="jq-datatable" cellpadding="0" cellspacing="0" border="0"
                               class="table table-striped table-bordered dataTable no-footer"
                               aria-describedby="jq-datatables-example_info">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>组别</th>
                                <th>键</th>
                                <th>键值</th>
                                <th>描述</th>
                                <th>创建时间</th>
                                <th>最后更新时间</th>
                                <th style="width: 140px;">操作</th>
                            </tr>
                            </thead>
                        </table>
                        <div class="table-footer clearfix">

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Page wide horizontal line -->
        <hr class="no-grid-gutter-h grid-gutter-margin-b no-margin-t">

    </div>
    <!-- / #content-wrapper -->

</div>
<!-- / #main-wrapper -->
<#include "include/common-bottom.ftl" encoding="UTF-8">
</body>
</html>