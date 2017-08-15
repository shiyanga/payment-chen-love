<script>
    init.push(function () {
       // $('#resource-code').mask('99-99-99-S0S-999');

        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };
        $("#resource-parent-code").select2({
            allowClear: true,
            placeholder: "选择下单的账号",
            id: function (rs) {
                return rs.id;
            },
            ajax: {
                url: "${basePath}/sys/authority/resource/query",
                dataType: 'json',
                delay: 1000,
                type:'POST',
                contentType: 'application/json',
                data: function (term, pageNo) {
                    return {
                        "_method_": "GET",
                        "blurResourceDesc":term
                    }
                },
                results: function (data, pageNo) {
                    if (data != null) {   //如果没有查询到数据，将会返回空串
                        return {
                            results: data.result
                        };
                    }
                },
                cache: true
            },
            initSelection: function (element, callback) {
            },
            formatResult: function formatAsText(item) {
                return item.code + " - " + item.description;
            },
            formatSelection: function resultFormatSelection(item) {
                return item.description;
            },
            escapeMarkup: function (markup) {
                return markup;
            }
        });

        //参数校验
        $("#resource-add-validation-form").validate({
            ignore: '.ignore, .select2-input',
            focusInvalid: false,
            rules: {
                'validation-parent-resource': {
                    maxlength:20
                },
                'validation-resource-code': {
                    required: true,
                    maxlength:20
                },
                'validation-resource-desc': {
                    required: true,
                    maxlength:500
                },
                'validation-resource-url': {
                    required: true,
                    maxlength:100
                },
                'validation-resource-reqType': {
                    required: true
                }
            }
        });
    });

    //提交保存
    function submitAddResourceDialog(){
        var validateResult = $("#resource-add-validation-form").valid();
        if (!validateResult) {
            return false;
        }
        //提交添加表单
        $.ajax({
            "url": "${basePath}/sys/authority/resource/add",
            "data": {"_method_": "put",
                "parentId": $("#resource-parent-code").val(),
                "code":$("#resource-code").val(),
                "description":$("#resource-desc").val(),
                "url":$("#resource-url").val(),
                "reqMethod":$("input[name='validation-resource-reqType']:checked").val()

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
                    $("#resource-add-dialog").modal("hide");
                    location.reload();
                }
            }
        });
    }
    //关闭添加窗口
    function cancelAddResourceDialog(){
        document.getElementById("resource-add-validation-form").reset();
    }
</script>

<style type="text/css">
    .form-group label.radio{
        display: inline-block;
    }
</style>
<div id="resource-add-dialog" class="modal fade" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
    <#--<div class="modal-dialog">-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">添加新权限资源</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="resource-add-validation-form" novalidate="novalidate">
                    <div class="panel-body">
                        <div class="form-group">
                            <label for="validation-parent-resource"  class="col-sm-3 control-label">父资源</label>
                            <div class="col-sm-9">
                                <input type="hidden" class="form-control" id="resource-parent-code" name="validation-parent-resource" placeholder="选择一个父资源，不选默认为根节点资源">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-resource-code"  class="col-sm-3 control-label">权限CODE</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="resource-code" name="validation-resource-code" placeholder="输入权限资源的CODE">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-resource-desc" class="col-sm-3 control-label">权限描述</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" id="resource-desc" rows="3" name="validation-resource-desc" placeholder="输入权限描述..."></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="validation-resource-url"  class="col-sm-3 control-label">权限URL</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="resource-url" name="validation-resource-url" placeholder="输入URL地址">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label" for="validation-resource-reqType">请求类型</label>
                            <div class="col-sm-9">
                                <p>
                                    <label class="radio">
                                        <input type="radio" name="validation-resource-reqType" class="px" value="" checked="checked">
                                        <span class="lbl">所有</span>
                                    </label>
                                    <label class="radio">
                                        <input type="radio" name="validation-resource-reqType" class="px" value="GET">
                                        <span class="lbl">GET</span>
                                    </label>
                                    <label class="radio">
                                        <input type="radio" name="validation-resource-reqType" class="px" value="POST">
                                        <span class="lbl">POST</span>
                                    </label>
                                    <label class="radio">
                                        <input type="radio" name="validation-resource-reqType" class="px" value="PUT">
                                        <span class="lbl">PUT</span>
                                    </label>
                                    <label class="radio">
                                        <input type="radio" name="validation-resource-reqType" class="px" value="DELETE">
                                        <span class="lbl">DELETE</span>
                                    </label>
                                </p>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <!-- / .modal-body -->
            <div class="modal-footer">
                <button id="resource-add-cancel-btn" type="button" class="btn btn-default" data-dismiss="modal" onclick="cancelAddResourceDialog()">取消
                </button>
                <button id="resource-add-save-btn" type="button" class="btn btn-primary" onclick="submitAddResourceDialog()">保 存</button>
            </div>
        </div>
    <#--</div>-->
</div>