var datatable;
//取得安全字符串
function safeString(origStr, replaceWith, maxLength){
    var safeStr = '';
	 // 添加HTML编码
    origStr = encodeHtml(origStr);

    if(origStr != null && origStr!== 'null'){
        var length = origStr.length;
        if(maxLength!= null && maxLength > 0 && length > maxLength){
            safeStr = origStr.substr(0, maxLength) + '...';
        }else{
            safeStr = origStr;
        }
    }else{
        safeStr = (replaceWith == null?"":replaceWith);
    }
    return safeStr;
}

//显示error信息
function showErrorMsg(msg) {
    showTopScrollMsg('danger', msg);
}
//显示info信息
function showInfoMsg(msg){
    showTopScrollMsg('info', msg);
}
function showTopScrollMsg(type, msg) {
    $('html,body').animate({ scrollTop: 0 }, 500);
    setTimeout(function () {
        PixelAdmin.plugins.alerts.clear(
            true, // animate
            'pa_page_alerts_dark' // namespace
        );
        var options = {
            type: type,
            namespace: 'pa_page_alerts_dark',
            classes:'alert-dark'
        };
        PixelAdmin.plugins.alerts.add(msg, options);
    }, 800);
}


function getProcessStatusName(statusId){
    var statusDiv = "未知";
    if(statusId != null){
        switch(statusId){
            case 0:
                statusDiv = "处理完成";break;
            case 1:
                statusDiv = "处理中";break;
        }
    }
    return statusDiv;
}

function getTaskStatus(statusId){
    var name = "未知";
    if(statusId != null){
        switch(statusId){
            case 10: name = "待分配";break;
            case 20: name = "待接单";break;
            case 30: name = "待处理";break;
            case 40: name = "处理中";break;
            case 50: name = "处理完成，待更新信息";break;
            case 51: name = "处理成功";break;
            case 52: name = "处理失败";break;
            case 53: name = "已取消";break;
            default: name ="未知("+statusId+")";
        }
    }
    return name;
}

function getOrderStatus(statusId){
    var name = "未知";
    if(statusId != null){
        switch(statusId){
            case 10: name = "待下单";break;
            case 11: name = "下单成功";break;
            case 12: name = "下单失败";break;
            case 20: name = "待退款";break;
            case 21: name = "退款成功";break;
            case 22: name = "退款失败";break;
            default: name ="未知("+statusId+")";
        }
    }
    return name;
}

function clearSelect2(select2Id1, select2Id2){
    if(select2Id1!=null){
        $("#"+select2Id1+"").val(null).trigger("change");
    }
    if(select2Id2!=null){
        $("#"+select2Id2+"").val(null).trigger("change");
    }
}

function logout(){
    $.ajax({
        "url":"${basePath}/account/logout",
        "type":"GET",
        "error":function(){
        },
        "success":function(res){
        }
    });
}
//格式化时间
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

Date.prototype.addDays = function(days)
{
    var dat = new Date(this.valueOf());
    dat.setDate(dat.getDate() + days);
    return dat;
};


Array.prototype.clean = function(deleteValue) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == deleteValue) {
            this.splice(i, 1);
            i--;
        }
    }
    return this;
};

function convertBlank(value, defaultValue) {
    if (!defaultValue) {
        //class='label label-warning' <span >暂无</span>
        defaultValue = "";
    }
    var valueStr = JSON.stringify(value);
    if (!valueStr || valueStr === "null" || !valueStr.trim()) {
        return defaultValue;
    }
    return value;
}

function joinChar(left, source, right) {
    if (!left) {
        left = '';
    }
    if (!right) {
        right = '';
    }

    return source != null ? left + source + right : source;
}

function isInteger(str) {
    var n = ~~Number(str);
    return String(n) === str && n >= 0;
}

//hover的bootstrap popover加载
var loadHoverPopover = function () {
    $('a.hover-popover').popover({ trigger: "manual" }).on("mouseenter", function () {
        var _this = this;
        $(this).popover("show");
        $(".popover").on("mouseleave", function () {
            $(_this).popover('hide');
        });
    }).on("mouseleave", function () {
        var _this = this;
        setTimeout(function () {
            if (!$(".popover:hover").length) {
                $(_this).popover("hide");
            }
        }, 300);
    });
}


/**
 *  对输出的字符串内容进行HtmlEncode, 防止XSS攻击:
 *  http://cp4.mgmt.ctripcorp.com/browse/ISST02-3127
 */
var REGX_HTML_ENCODE = /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g;
var REGX_HTML_DECODE = /&\w+;|&#(\d+);|\s/g;
function encodeHtml(s){
    if(typeof(s) != "string" || s.length < 1)
        return s;

    return s.replace(REGX_HTML_ENCODE, function($0){
        var c = $0.charCodeAt(0);
        if(c == 0x20){
            return $0;
        }

        var r = ["&#"];encodeHtml
        //c = (c == 0x20) ? 0xA0 : c;
        r.push(c);
        r.push(";");
        return r.join("");
    });
};

function decodeHtml(s){
    if(typeof(s) != "string" || s.length < 1)
        return s;

    return s.replace(REGX_HTML_DECODE, function($0,$1){
        if($0.charCodeAt(0) ==0xA0){
            // chrome TextArea bug.
            return String.fromCharCode(0x20);
        }else if(!isNaN($1)){
            // Maybe is Entity Number
            // Uncode: &#\d+;
            return String.fromCharCode(($1 == 0xA0) ? 0x20 : $1);
        }else {
            return  $0;
        }
    });
};
