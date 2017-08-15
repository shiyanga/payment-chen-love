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

    .current-tr {
        border-left: 5px solid #39c;
    }

    #role-resource-dt > tbody > tr:hover > td,
    #role-resource-dt > tbody > tr:hover > th {
        background-color: #EFEFE6;
        color: #1C81C2;
    }
</style>
<script>
    var roleResourceArray = new Array();
    var roleUserArray = new Array();
    init.push(function () {

    });

    //角色编辑权限
    function authRoleEdit(obj, roleId) {
        if (roleId == null) {
            return false;
        }
        $("#role-resource-edit-dialog").modal();
        loadResourceWithTableTreeFormat(roleId);
    }

    //角色用户编辑
    function roleUserEdit(obj, roleId) {
        if (roleId == null) {
            return false;
        }
        $("#role-user-edit-dialog").modal();
        loadRoleUser(roleId);
    }

    function loadRoleUser(roleId) {
        roleUserArray = new Array();
        //查询角色拥有的用户信息
        $.ajax({
            "url": "${basePath}/sys/authority/role/user/query",
            "data": {
                "_method_": "GET",
                "roleId": roleId
            },
            "type": "POST",
            async: false,
            "error": function (response) {
                $.growl.error({title: "异常", message: response.resultMessage});
            },
            "success": function (response) {
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                    $.growl.error({title: "查询失败", message: msg});
                } else {
                    for (var index = 0; index < response.result.length; ++index) {
                        roleUserArray[index] = response.result[index].user.id;
                    }
                }
            }
        });

        //查询所有用户信息
        $.ajax({
            "url": "${basePath}/sys/authority/role/all-user/query",
            "data": {
                "_method_": "GET"
            },
            "type": "POST",
            async: false,
            "error": function (response) {
                $.growl.error({title: "异常", message: response.resultMessage});
            },
            "success": function (response) {
                var isHasAddAuthority =true;
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                    $.growl.error({title: "查询失败", message: msg});
                } else {
                    var tbodyHtml = '';
                    //查询权限
                    for (var index = 0; index < response.result.length; ++index) {
                        var ifContained = roleUserArray.indexOf(response.result[index].id) >= 0 ? ' checked="checked" ' : '';

                        tbodyHtml = tbodyHtml +
                                '<tr><td>' + encodeHtml(response.result[index].department) + '</td>' +
                                '<td>' + encodeHtml(response.result[index].employeeNumber) + '</td>' +
                                '<td>' + encodeHtml(response.result[index].name) + '</td>' +
                                '<td>' + encodeHtml(response.result[index].telephone) + '</td>' +
                                '<td>' + encodeHtml(response.result[index].email) + '</td>' +
                                '<td>' + encodeHtml(response.result[index].status) + '</td>' +
                                ((isHasAddAuthority) ? '<td><label class="px-single"><input type="checkbox" name="" class="px" ' + ifContained + ' onclick="updateRoleUser(this, \'' + roleId + '\',\'' + response.result[index].id + '\')"><span class="lbl"></span></label></td>' : '<td></td>') +
                                //
                                '</tr>';
                    }
                    $("#role-user-dt tbody").html(tbodyHtml);
                }
            }
        });
    }

    //加载资源列表以 表格树的形式
    function loadResourceWithTableTreeFormat(roleId) {
        roleResourceArray = new Array();
        //查询角色拥有的资源信息
        $.ajax({
            "url": "${basePath}/sys/authority/role/resource/query",
            "data": {
                "_method_": "GET",
                "roleId": roleId
            },
            "type": "POST",
            async: false,
            "error": function (response) {
                $.growl.error({title: "异常", message: response.resultMessage});
            },
            "success": function (response) {
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                    $.growl.error({title: "查询失败", message: msg});
                } else {
                    for (var index = 0; index < response.result.length; ++index) {
                        roleResourceArray[index] = response.result[index].resource.id;
                    }
                }
            }
        });
        //查询所有资源信息
        $.ajax({
            "url": "${basePath}/sys/authority/role/all-resource/query",
            "data": {
                "_method_": "GET"
            },
            "type": "POST",
            async: false,
            "error": function (response) {
                $.growl.error({title: "异常", message: response.resultMessage});
            },
            "success": function (response) {
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                    $.growl.error({title: "查询失败", message: msg});
                } else {
                    var tbodyHtml = parseTableTreeElement(roleId, response.result).html;
                    $("#role-resource-dt tbody").html(tbodyHtml);
                    $('#role-resource-dt').treegrid({
                        expanderExpandedClass: "fa fa-minus-square",
                        expanderCollapsedClass: "fa fa-plus-square",
                        initialState: "collapsed"
                    });
                }
            }
        });
    }

    //解析成层级对象
    function parseTableTreeElement(roleId, treeDataArray, parent) {
        if (treeDataArray == null) {
            return null;
        }
        var isHasResAddAuthority = true;
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
//                for(var tempIndex =1;tempIndex<treeDataArray[index].deep; tempIndex++){
//                    tableTreeHtml = tableTreeHtml +'<td></td>';
//                }

            var ifContained = roleResourceArray.indexOf(treeDataArray[index].id) >= 0 ? ' checked="checked" ' : '';
            tableTreeHtml = tableTreeHtml +
                    '<td class="td-main-column">' + treeDataArray[index].code + '</td>' +
                    '<td>' + treeDataArray[index].description + '</td>' +
                    '<td>' + treeDataArray[index].url + '</td>' +
                    '<td>' + treeDataArray[index].reqMethod + '</td>' +
                    (isHasResAddAuthority ? '<td><label class="px-single"><input type="checkbox" name="" class="px" ' + ifContained + ' onclick="updateRoleResource(this, \'' + roleId + '\',\'' + treeDataArray[index].id + '\')"><span class="lbl"></span></label></td>' : '<td></td>') +
                    '';

            tableTreeHtml = tableTreeHtml + '</tr>';
            //tableTreeDeep++;

            if (treeDataArray[index].children != null) {
                //继续解析子类
                var tempObj = parseTableTreeElement(roleId, treeDataArray[index].children, treeDataArray[index]);
                //得到children最深的层数
                tableTreeHtml = tableTreeHtml + tempObj.html;
            }
        }
        tableTreeObj.html = tableTreeHtml;
        return tableTreeObj;
    }

    //更新角色资源信息
    function updateRoleResource(obj, roleId, resourceId) {
        if (obj == null) {
            return false;
        }

        var reqUrl = $(obj).is(':checked') ? '${basePath}/sys/authority/role/resource/add' : '${basePath}/sys/authority/role/resource/delete';
        var reqMethod = $(obj).is(':checked') ? 'PUT' : 'DELETE';

        //更新角色的权限信息
        $.ajax({
            "url": reqUrl,
            "data": {
                "_method_": reqMethod,
                "roleId": roleId,
                "resourceId": resourceId
            },
            "type": "POST",
            async: false,
            "error": function (response) {
                $.growl.error({title: "异常", message: response.resultMessage});
                $(obj).attr('checked', !$(obj).is(':checked'));
            },
            "success": function (response) {
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                    $.growl.error({title: "操作失败", message: msg});
                    $(obj).prop('checked', !$(obj).is(':checked'));
                } else {
                    $.growl.notice({title: "操作成功", message: response.resultMessage});
                }
            }
        });
    }

    //更新角色用户信息
    function updateRoleUser(obj, roleId, userId) {
        if (obj == null) {
            return false;
        }

        var reqUrl = $(obj).is(':checked') ? '${basePath}/sys/authority/role/user/add' : '${basePath}/sys/authority/role/user/delete';
        var reqMethod = $(obj).is(':checked') ? 'PUT' : 'DELETE';

        //更新角色的权限信息
        $.ajax({
            "url": reqUrl,
            "data": {
                "_method_": reqMethod,
                "roleId": roleId,
                "userId": userId
            },
            "type": "POST",
            async: false,
            "error": function (response) {
                $.growl.error({title: "异常", message: response.resultMessage});
                $(obj).attr('checked', !$(obj).is(':checked'));
            },
            "success": function (response) {
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                    $.growl.error({title: "操作失败", message: msg});
                    $(obj).prop('checked', !$(obj).is(':checked'));
                } else {
                    $.growl.notice({title: "操作成功", message: response.resultMessage});
                }
            }
        });
    }

    //本地搜索资源信息
    function searchResourceByUrlLocal(obj) {
        $("tr.current-tr").removeClass('current-tr');
        var searchKey = $("#url-search-key-input").val();
        if (searchKey != null && $.trim(searchKey).length > 0) {
            searchKey = $.trim(searchKey);
            $('#role-resource-dt').treegrid('getAllNodes').each(function () {
                var url = $(this).children('td')[2].innerHTML;
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

    function refreshAuthorityCache() {
        $.ajax({
            "url": "${basePath}/sys/authority/cache/refresh",
            "type": "GET",
            "error": function (response) {
            },
            "success": function (response) {
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                    $.growl.error({title: "操作失败", message: msg});
                } else {
                    $.growl.notice({title: "操作成功", message: response.resultMessage});
                }
            }
        });
    }
</script>

<div id="role-resource-edit-dialog" class="modal fade" tabindex="-1" role="dialog" style="display: none;"
     aria-hidden="true">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title" id="myModalLabel">编辑角色权限信息</h4>
        </div>
        <div class="modal-body">
            <div class="form-inline" style="margin-bottom: 10px;">
                <div class="form-group">
                    <label class="sr-only" for="exampleInputEmail2">权限URL</label>
                    <input type="text" class="form-control" id="url-search-key-input" placeholder="输入URL搜索">
                </div>
                <button class="btn btn-labeled btn-primary" onclick="searchResourceByUrlLocal(this)"><span
                        class="btn-label icon fa fa-search"></span>搜索
                </button>

            <#if CtripAccount.ifUrlGranted('/sys/authority/cache/refresh') == 'true'>
                <button class="btn" onclick="javascript:refreshAuthorityCache();">
                    <span class="btn-label icon fa fa-refresh"></span>刷新缓存
                </button>
            </#if>

            </div>

            <table id="role-resource-dt" cellpadding="0" cellspacing="0" border="0"
                   class="tree table-hover table table-striped table-bordered dataTable no-footer">
                <thead>
                <tr>
                    <th></th>
                    <th>权限描述</th>
                    <th>URL</th>
                    <th>请求类型</th>
                    <th style="width:110px;">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        <div class="modal-footer">
            <button class="btn btn-outline btn-labeled btn-success" data-dismiss="modal"><span
                    class="btn-label icon fa fa-times"></span>关闭
            </button>
        </div>
    </div>
</div>

<div id="role-user-edit-dialog" class="modal fade" tabindex="-1" role="dialog" style="display: none;"
     aria-hidden="true">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title" id="myModalLabel">编辑角色用户信息</h4>
        </div>
        <div class="modal-body">
            <table id="role-user-dt" cellpadding="0" cellspacing="0" border="0"
                   class="tree table table-striped table-bordered dataTable no-footer">
                <thead>
                <tr>
                    <th>部门</th>
                    <th>员工号</th>
                    <th>姓名</th>
                    <th>电话</th>
                    <th>邮箱</th>
                    <th>状态</th>
                    <th style="width:110px;">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        <div class="modal-footer">
            <button class="btn btn-outline btn-labeled btn-success" data-dismiss="modal"><span
                    class="btn-label icon fa fa-times"></span>关闭
            </button>
        </div>
    </div>
</div>
