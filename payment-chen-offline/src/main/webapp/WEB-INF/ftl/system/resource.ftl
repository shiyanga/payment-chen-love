<#assign sec=JspTaglibs["/WEB-INF/ftl/template/auth.tld"] />
<!DOCTYPE html>
<html class="gt-ie8 gt-ie9 not-ie">
<head>
    <title>权限管理-资源管理</title>
<#include "include/common-head.ftl"/>
    <script src="${basePath}/resources/js/jquery.treegrid.js"></script>

    <style type="text/css">
        .tb-td {
            border: 1px solid #eee;
            padding: 10px;
        }

        .treegrid-indent {
            width: 16px;
            height: 16px;
            display: inline-block;
            position: relative;
        }

        .treegrid-expander {
            width: 16px;
            height: 16px;
            display: inline-block;
            position: relative;
            cursor: pointer;
        }

        .td-main-column {
            font-weight: bold;
        }

        #resource-dt .treegrid-expander {
            font-size: 16px;
        }

        .current-tr {
            border-left: 5px solid #39c;
        }

        .table-hover > tbody > tr:hover > td,
        .table-hover > tbody > tr:hover > th {
            background-color: #EFEFE6;
        }

        .empty-column {
            border: none;
            color: black;
            font-weight: normal;
            font-style: italic;
        }
    </style>
    <script>
        var resourceDT;

        var hasResEditAuth = true;
        var hasResDelAuth = true;
        init.push(function () {
            //加载树状资源
            loadResourceWithTableTreeFormat();
            $("#resource-search-btn").click(function () {
                $("#resource-dt tbody").empty();
                loadResourceWithTableTreeFormat();
            });
        });

        //加载资源列表以 表格树的形式
        function loadResourceWithTableTreeFormat() {
            //提交添加表单
            $.ajax({
                "url": "${basePath}/sys/authority/resource/query",
                "data": {
                    "_method_": "GET"
                },
                "type": "get",
                "error": function (response) {
                    $.growl.error({title: "异常", message: response.resultMessage});
                },
                "success": function (response) {
                    if (response == null || '0000' != response.resultCode) {
                        var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                        $.growl.error({title: "加载失败", message: msg});
                    } else {
                        var tbodyHtml = parseTableTreeElement(response.result).html;
                        $("#resource-dt tbody").html(tbodyHtml);
                        $('#resource-dt').treegrid({
                            expanderExpandedClass: "fa fa-minus-square",
                            expanderCollapsedClass: "fa  fa-plus-square",
                            initialState: "collapsed",
                            expanderTemplate: '<span class="treegrid-expander"></span>',
                            indentTemplate: '<span class="treegrid-indent"></span>'
                        });
                        //初始化 - 可更新的字段
                        $('#resource-dt a.editable-element').editable({
                            emptytext: '无',
                            emptyclass: 'empty-column',
                            params: function (params) {
                                params._method_ = "PATCH";
                                params.resourceId = $(this).attr("resourceid");
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
                    }
                }
            });
        }

        //解析成层级对象
        function parseTableTreeElement(treeDataArray, parent) {
            if (treeDataArray == null) {
                return null;
            }
            //树对象数组
            var tableTreeObj = new Object();
            var tableTreeHtml = '';

            //不为空遍历
            for (var index = 0; index < treeDataArray.length; ++index) {
                treeDataArray[index].deep = parent == null ? 1 : parent.deep + 1;
                if (parent != null) {
                    tableTreeHtml = tableTreeHtml + '<tr class="treegrid-' + treeDataArray[index].id + ' treegrid-parent-' + parent.id + '">';
                } else {
                    tableTreeHtml = tableTreeHtml + '<tr class="treegrid-' + treeDataArray[index].id + '">';
                }

                tableTreeHtml = tableTreeHtml +
                        '<td class="td-main-column">' + (hasResEditAuth ? '<a href="#" colname="code" coltype="columnValue" data-url="${basePath}/sys/authority/resource/update" resourceid="' + treeDataArray[index].id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">' + treeDataArray[index].code + '</a>' : treeDataArray[index].code) + '</td>' +
                        '<td>' + treeDataArray[index].id + '</td>' +
                        '<td class="td-main-column">' + (hasResEditAuth ? '<a href="#" colname="parent" coltype="columnValue" data-url="${basePath}/sys/authority/resource/update" resourceid="' + treeDataArray[index].id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">' + safeString(treeDataArray[index].parentId) + '</a>' : safeString(treeDataArray[index].parentId)) + '</td>' +
                        '<td class="td-main-column">' + (hasResEditAuth ? '<a href="#" colname="description" coltype="columnValue" data-url="${basePath}/sys/authority/resource/update" resourceid="' + treeDataArray[index].id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">' + treeDataArray[index].description + '</a>' : treeDataArray[index].description) + '</td>' +
                        '<td class="td-main-column">' + (hasResEditAuth ? '<a href="#" colname="url" coltype="columnValue" data-url="${basePath}/sys/authority/resource/update" resourceid="' + treeDataArray[index].id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">' + treeDataArray[index].url + '</a>' : treeDataArray[index].url) + '</td>' +
                        '<td class="td-main-column">' + (hasResEditAuth ? '<a href="#" colname="reqMethod" coltype="columnValue" data-url="${basePath}/sys/authority/resource/update" resourceid="' + treeDataArray[index].id + '"  data-pk="1" data-type="text" data-title="输入修改的值" class="editable-element editable editable-click">' + safeString(treeDataArray[index].reqMethod) + '</a>' : safeString(treeDataArray[index].reqMethod)) + '</td>' +
                        '<td>' + treeDataArray[index].createTime + '</td>' +
                        '<td>' + treeDataArray[index].updateTime + '</td>' +
                        '<td>' + (hasResDelAuth ? '<button class="btn btn-outline btn-labeled btn-danger" onclick="deleteResource(this, \'' + treeDataArray[index].id + '\')"><span class="btn-label icon fa  fa-trash-o"></span>删除</button>' : '') + '</td>';

                tableTreeHtml = tableTreeHtml + '</tr>';
                //tableTreeDeep++;

                if (treeDataArray[index].children != null) {
                    //继续解析子类
                    var tempObj = parseTableTreeElement(treeDataArray[index].children, treeDataArray[index]);
                    //得到children最深的层数

                    tableTreeHtml = tableTreeHtml + tempObj.html;
                }
            }
            tableTreeObj.html = tableTreeHtml;
            return tableTreeObj;
        }

        //删除资源
        function deleteResource(obj, resourceId) {
            if (resourceId == null) {
                return;
            }
            bootbox.confirm({
                message: "确定要删除此资源？ ",
                callback: function (result) {
                    if (result) {
                        $.ajax({
                            "url": "${basePath}/sys/authority/resource/delete",
                            "data": {
                                "_method_": "DELETE",
                                "resourceId": resourceId
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

        //本地搜索资源信息
        function searchResourceByUrlLocal(obj) {
            $("tr.current-tr").removeClass('current-tr');
            var searchKey = $("#url-search-key-input").val();
            if (searchKey != null && $.trim(searchKey).length > 0) {
                searchKey = $.trim(searchKey);
                $('#resource-dt').treegrid('getAllNodes').each(function () {
                    var url = $(this).children('td')[4].children[0].innerHTML;
                    if (url != null && url.indexOf(searchKey) >= 0) {
                        var curNode = $(this);
                        while (curNode.treegrid('getParentNode') != null) {
                            curNode = curNode.treegrid('getParentNode');
                            curNode.treegrid('expand');
                        }
                        if (curNode.treegrid('getParentNode') == null) {
                            $(this).treegrid('getRootNodes').treegrid('expand');
                        }
                        //$(this).treegrid('expand');
                        $(this).addClass('current-tr');
                        return false;
                    }
                });
            }
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
            <li class="active"><a href="#">权限管理</a></li>
        </ul>

        <!-- 主页面开始 -->
        <div class="row">
            <div class="panel-group" id="resource-search-accordion">
                <div class="form-inline" style="margin-bottom: 10px;">
                    <div class="form-group">
                        <label class="sr-only" for="exampleInputEmail2">权限URL</label>
                        <input type="text" class="form-control" id="url-search-key-input" placeholder="输入URL搜索"
                               style="width: 300px;">
                    </div>
                    <button class="btn btn-labeled btn-primary" onclick="searchResourceByUrlLocal(this)"><span
                            class="btn-label icon fa fa-search"></span>搜索
                    </button>


                    <button class="btn btn-success btn-labeled" data-toggle="modal"
                            style=" float: right;margin-right:50px;"
                            data-target="#resource-add-dialog">
                        <span class="btn-label icon fa fa-plus"></span>添加新权限
                    </button>

                </div>

                <div class="table-primary">
                    <table id="resource-dt" cellpadding="0" cellspacing="0" border="0"
                           class="tree table-hover table table-striped table-bordered dataTable no-footer">
                        <thead>
                        <tr>
                            <th></th>
                            <th>资源ID</th>
                            <th>父资源ID</th>
                            <th>权限描述</th>
                            <th>URL</th>
                            <th>请求类型</th>
                            <th>创建时间</th>
                            <th>最后修改时间</th>
                            <th style="width:110px;">操作</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
            <!-- 主页面结束 -->
            <hr class="no-grid-gutter-h grid-gutter-margin-b no-margin-t">
        </div>

    </div>

    <!-- 添加add 页面 -->
<#include "system/resource-add.ftl" encoding="UTF-8">
<#include "include/common-bottom.ftl" encoding="UTF-8">
</body>
</html>
