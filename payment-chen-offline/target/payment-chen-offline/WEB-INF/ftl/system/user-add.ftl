<script>
    init.push(function () {
        // $('#user-code').mask('99-99-99-S0S-999');

        //参数校验
        $("#user-add-validation-form").validate({
            ignore: '.ignore, .select2-input',
            focusInvalid: false,
            rules: {
                'validation-user-dep': {
                    required: true
                },
                'validation-user-status': {
                    required: true
                },
                'validation-user-employeeno': {
                    required: true,
                    maxlength: 20
                },'validation-user-pwd': {
                    required: true,
                    minlength:3,
                    maxlength: 10
                },'validation-user-pwd-confirm': {
                    required: true,
                    minlength:3,
                    maxlength: 10,
                    equalTo:"#user-pwd"
                },
                'validation-user-name': {
                    required: true
                },
                'validation-user-email': {
                    required: true,
                    email: true
                },
                'validation-user-remark':{
                    maxlength: 500
                }
            },
            messages: {

                confirm_password: {
                    //required: "请输入密码",
                    //minlength: "密码长度不能小于 5 个字母",
                    equalTo: "两次密码输入不一致"
                }
            }
        });
    });

    //提交保存
    function submitAddUserDialog() {
        var validateResult = $("#user-add-validation-form").valid();
        if (!validateResult) {
            return false;
        }
        //提交添加表单
        $.ajax({
            "url": "${basePath}/sys/authority/user/add",
            "data": {
                "_method_": "put",
                "employeeNumber": $("#user-employee-no").val(),
                "name": $("#user-name").val(),
                "loginPassword": $("#user-pwd").val(),
                "email": $("#user-email").val(),
                "telephone": $("#user-mobile").val(),
                "department": $("#user-dep").val(),
                "status":$("input[name='validation-user-status']:checked").val(),
                "remark":$("#user-remark").val(),
            },
            "type": "POST",
            "error": function (response) {
                $.growl.error({title: "异常", message: response.resultMessage});
            },
            "success": function (response) {
                if (response == null || '0000' != response.resultCode) {
                    var msg = (response.resultCode == null) ? JSON.parse(response).errorMsg : response.resultMessage;
                    $.growl.error({title: "添加失败", message: msg});
                } else {
                    $.growl.notice({title: "提示：", message: "操作成功！"});
                    $("#user-add-dialog").modal("hide");
                    location.reload();
                }
            }
        });
    }
    //关闭添加窗口
    function cancelAdduserDialog() {
        document.getElementById("user-add-validation-form").reset();
    }
</script>

<style type="text/css">
    .form-group label.radio {
        display: inline-block;
    }
</style>
<div id="user-add-dialog" class="modal fade" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">添加新用户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="user-add-validation-form" novalidate="novalidate">
                    <div class="panel-body">
                        <div class="form-group">
                            <label for="validation-user-dep" class="col-sm-2 control-label">所属部门</label>

                            <div class="col-sm-10">
                                <select id="user-dep" class="form-control" name="validation-user-dep">
                                    <option value>-- 选择一个部门 --</option>
                                    <option value="LAD-HOTEL-DEVELOPMENT">大住宿-酒店研发部</option>
                                    <option value="LAD-HOTEL-RESERVE">大住宿-酒店预订部</option>
                                    <option value="LAD-PLATFORM-BUSINESS">大住宿-平台商务部</option>
                                    <option value="LAD-O2O-MANAGEMENT">大住宿-O2O管理部</option>
                                    <option value="LAD-CUSTOMER-SERVICE">大住宿-客服部</option>
                                    <option value="OTHER">其它</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-user-status" class="col-sm-2 control-label">状态</label>

                            <div class="col-sm-10">
                                <p>
                                    <label class="radio">
                                        <input type="radio" name="validation-user-status" class="px" checked="checked" value="1">
                                        <span class="lbl">开启</span>
                                    </label>
                                    <label class="radio">
                                        <input type="radio" name="validation-user-status" class="px" value="2">
                                        <span class="lbl">禁用</span>
                                    </label>
                                </p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-user-employeeno" class="col-sm-2 control-label">员工号</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="user-employee-no"
                                       name="validation-user-employeeno" placeholder="输入携程员工号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-user-name" class="col-sm-2 control-label">姓名</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="user-name" name="validation-user-name"
                                       placeholder="输入用户姓名">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="validation-user-pwd" class="col-sm-2 control-label">密码</label>

                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="user-pwd"
                                       name="validation-user-pwd" placeholder="输入密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-user-pwd-confirm" class="col-sm-2 control-label">确认密码</label>

                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="user-pwd-confirm"
                                       name="validation-user-pwd-confirm" placeholder="再次输入密码">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="validation-user-mobile" class="col-sm-2 control-label">手机号</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="user-mobile" name="validation-user-mobile"
                                       placeholder="输入手机号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-user-email" class="col-sm-2 control-label">邮箱</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="user-email" name="validation-user-email"
                                       placeholder="输入用户邮箱地址">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-user-remark" class="col-sm-2 control-label">备注信息</label>

                            <div class="col-sm-10">
                                <textarea class="form-control" id="user-remark" name="validation-user-remark"
                                       placeholder="输入备注信息"></textarea>
                            </div>
                        </div>

                        <!-- 角色信息分配 -->
                        <#--<div class="form-group">-->
                            <#--<label for="validation-user-email" class="col-sm-2 control-label">角色分配</label>-->

                            <#--<div class="col-sm-10">-->
                                <#--<div style="border: 1px solid #E4E2E2;padding:0px 0px 20px 10px">-->
                                    <#--<h6 class="text-light-gray text-semibold text-xs" style="margin:20px 0 10px 0;">-->
                                        <#--角色1</h6>-->

                                    <#--<p>-->
                                        <#--<label class="checkbox-inline">-->
                                            <#--<input type="checkbox" class="px">-->
                                            <#--<span class="lbl">One</span>-->
                                        <#--</label>-->
                                        <#--<label class="checkbox-inline">-->
                                            <#--<input type="checkbox" class="px" checked="checked">-->
                                            <#--<span class="lbl">Two</span>-->
                                        <#--</label>-->
                                        <#--<label class="checkbox-inline">-->
                                            <#--<input type="checkbox" class="px">-->
                                            <#--<span class="lbl">Three</span>-->
                                        <#--</label>-->
                                    <#--</p>-->

                                    <#--<h6 class="text-light-gray text-semibold text-xs" style="margin:20px 0 10px 0;">-->
                                        <#--角色2</h6>-->

                                    <#--<p>-->
                                        <#--<label class="checkbox-inline">-->
                                            <#--<input type="checkbox" class="px">-->
                                            <#--<span class="lbl">One</span>-->
                                        <#--</label>-->
                                        <#--<label class="checkbox-inline">-->
                                            <#--<input type="checkbox" class="px" checked="checked">-->
                                            <#--<span class="lbl">Two</span>-->
                                        <#--</label>-->
                                        <#--<label class="checkbox-inline">-->
                                            <#--<input type="checkbox" class="px">-->
                                            <#--<span class="lbl">Three</span>-->
                                        <#--</label>-->
                                    <#--</p>-->
                                <#--</div>-->
                            <#--</div>-->
                        <#--</div>-->
                    </div>
                </form>
            </div>
            <!-- / .modal-body -->
            <div class="modal-footer">
                <button id="user-add-cancel-btn" type="button" class="btn btn-default" data-dismiss="modal"
                        onclick="cancelAdduserDialog()">取消
                </button>
                <button id="user-add-save-btn" type="button" class="btn btn-primary" onclick="submitAddUserDialog()">保
                    存
                </button>
            </div>
        </div>
    </div>
</div>