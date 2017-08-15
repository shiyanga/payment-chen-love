<script>
    init.push(function () {
        var valueInputTypeRadioBtn = $("input[type=radio][name='value-input-type-radio']");

        $("#addNewConfigure-btn").click(function(){
            var group = $("#group-div").val();
            var key = $("#key-div").val();

            var valueInputType = $("input[type=radio][name='value-input-type-radio']:checked").val();
            var value = (valueInputType == 'RICH')?$('#value-div').code():$("#value-div").val();
            var description = $("#desc-div").val();

            if(group!=null && $.trim(group).length>0 && key!=null && $.trim(key).length>0 && value!=null && $.trim(value).length>0){
                $.ajax({
                    "url":"${basePath}/sys/configure/add",
                    "data":{"_method_":"put","group":group,"key":key, "value":value,"description":description},
                    "type":"POST",
                    "error":function(){
                    },
                    "success":function(response){
                        if(response == null || '0000' != response.resultCode){
                            $.growl.error({title:"添加失败：", message: response.resultMessage});
                        }else{
                            $.growl.notice({title:"提示：",message: "操作成功！"});
                            setInterval(function(){
                                $("#closeConfigureDialog-btn").click();
                            },1000);
                            location.reload();
                        }
                    }
                });
            }else{
                $.growl.error({title:"校验失败：", message: "信息填写不合法，请检查！"});
            }
        });

        //value类型单选按钮点击
        valueInputTypeRadioBtn.click(function(){
            var inputValueType = $(this).val();
            var commonValueInput = $("#value-div");

            switch(inputValueType){
                case "COMMON":{
                    commonValueInput.destroy();
                    break;
                }
                case "RICH":{
                    commonValueInput.summernote({
                        height: 200,
                        tabsize: 2,
                        codemirror: {
                            theme: 'monokai'
                        }
                    });
                    break;
                }
                default: return;
            }
        });
    });
</script>
<div id="addConfigureDialog" class="modal fade" tabindex="-1" role="dialog" style="display: none;" aria-hidden="true">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h4 class="modal-title" id="myModalLabel">添加新记录</h4>
        </div>
        <div class="modal-body">
            <form class="form-horizontal" id="jq-validation-form" novalidate="novalidate">
            <div class="panel-body">
                <div class="form-group">
                    <label for="validation-group" class="col-sm-2 control-label">组别</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="group-div" placeholder="group">
                    </div>
                </div>
                <div class="form-group">
                    <label for="validation-key" class="col-sm-2 control-label">键</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="key-div" placeholder="key">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">键值</label>
                    <div class="col-sm-10">
                            <p>
                                <label class="radio" style="display: inline;">
                                    <input type="radio" name="value-input-type-radio" class="px" checked="checked" value="COMMON">
                                    <span class="lbl">普通文本</span>
                                </label>

                                <label class="radio" style="display: inline;">
                                    <input type="radio" name="value-input-type-radio" class="px" value="RICH">
                                    <span class="lbl">富文本</span>
                                </label>
                            </p>
                        <div>
                            <#--<input type="text" class="form-control" id="value-div" placeholder="value">-->
                            <textarea class="form-control" id="value-div" rows="10"></textarea>
                        </div>

                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-10">
                        <textarea class="form-control" id="desc-div" placeholder="描述下这是个什么鬼..."></textarea>
                    </div>
                </div>
            </div>
            </form>
        </div> <!-- / .modal-body -->
        <div class="modal-footer">
            <button id="closeConfigureDialog-btn" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button id="addNewConfigure-btn" type="button" class="btn btn-primary">保 存</button>
        </div>
    </div>
</div>