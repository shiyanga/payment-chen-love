<script>
    init.push(function () {
        //参数校验
        $("#role-add-validation-form").validate({
            ignore: '.ignore, .select2-input',
            focusInvalid: false,
            rules: {
                'validation-role-name': {
                    required: true,
                    maxlength:100
                },
                'validation-role-desc': {
                    required: true,
                    maxlength:500
                }
            }
        });
    });

    //提交保存
    function submitAddRoleDialog(){
        var validateResult = $("#role-add-validation-form").valid();
        if (!validateResult) {
            return false;
        }
        //提交添加表单
        $.ajax({
            "url": "${basePath}/sys/authority/role/add",
            "data": {"_method_": "put",
                "name": $("#role-name").val(),
                "description":$("#role-desc").val()

            },
            "type": "POST",
            "error": function (response) {
                $.growl.error({title: "异常", message: response.resultMessage});
            },
            "success": function (response) {
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null)?JSON.parse(response).errorMsg:response.resultMessage;
                    $.growl.error({title: "添加失败", message: msg});
                } else {
                    $.growl.notice({title: "提示：", message: "操作成功！"});
                    $("#role-add-dialog").modal("hide");
                    location.reload();
                }
            }
        });
    }
    //关闭添加窗口
    function cancelAddroleDialog(){
        document.getElementById("role-add-validation-form").reset();
    }
</script>

<style type="text/css">
    .form-group label.radio{
        display: inline-block;
    }
</style>
<div id="role-add-dialog" class="modal fade" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">添加新角色</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="role-add-validation-form" novalidate="novalidate">
                    <div class="panel-body">
                        <div class="form-group">
                            <label for="validation-role-name" class="col-sm-2 control-label">角色名称</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="role-name" name="validation-role-name" placeholder="输入角色名称（最大长度100）">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-role-desc" class="col-sm-2 control-label">角色描述</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" id="role-desc" rows="3" name="validation-role-desc" placeholder="输入权限描述（最大长度500）"></textarea>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <!-- / .modal-body -->
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
                <button id="role-add-save-btn" type="button" class="btn btn-primary" onclick="submitAddRoleDialog()">保 存</button>
            </div>
        </div>
    </div>
</div>