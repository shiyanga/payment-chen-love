<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>信息管理系统</title>
    <link href="resources/css/iconfont.css" rel="stylesheet">
    <style type="text/css">
        html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
            margin: 0;
            padding: 0;
            border: 0;
            font: inherit;
            font-size: 100%;
            vertical-align: baseline
        }

        html {
            line-height: 1
        }

        ol, ul {
            list-style: none
        }

        table {
            border-collapse: collapse;
            border-spacing: 0
        }

        caption, th, td {
            text-align: left;
            font-weight: normal;
            vertical-align: middle
        }

        q, blockquote {
            quotes: none
        }

        q:before, q:after, blockquote:before, blockquote:after {
            content: "";
            content: none
        }

        a img {
            border: none
        }

        article, aside, details, figcaption, figure, footer, header, hgroup, main, menu, nav, section, summary {
            display: block
        }

        html {
            *overflow: auto
        }

        body, button, input, select, textarea {
            font-family: PingFang SC, Lantinghei SC, Microsoft Yahei, Hiragino Sans GB, Microsoft Sans Serif, WenQuanYi Micro Hei, sans;
            font-size: 14px
        }

        .clearfix:after {
            content: "";
            display: block;
            height: 0;
            clear: both;
            visibility: hidden
        }

        .clearfix {
            display: inline-block
        }

        .clearfix {
            height: 1%
        }

        .clearfix {
            display: block;
            overflow: hidden
        }

        .ellipsis {
            text-overflow: ellipsis;
            white-space: nowrap;
            overflow: hidden
        }

        .alert {
            margin-bottom: 20px;
            padding: 0 10px;
            height: 36px;
            line-height: 36px;
            border: 1px solid #ddd;
            color: #888
        }

        .alert .close {
            float: right;
            font-size: 12px;
            color: #999
        }

        .alert .close:hover {
            text-decoration: none
        }

        .alert.alert-warning {
            background: #fff5db;
            color: #e2ba89;
            border-color: #ffe195
        }

        .alert.alert-error {
            background: #fceee8;
            color: #fc0000;
            border-color: #fc0000
        }

        ::-webkit-scrollbar-track-piece {
            background-color: #fff;
            -webkit-border-radius: 0
        }

        ::-webkit-scrollbar {
            width: 10px;
            height: 10px
        }

        ::-webkit-scrollbar-thumb {
            height: 50px;
            background-color: #b8b8b8;
            -webkit-border-radius: 6px;
            outline: 2px solid #fff;
            outline-offset: -2px;
            border: 2px solid #fff;
            filter: alpha(opacity=50);
            -moz-opacity: 0.5;
            -khtml-opacity: 0.5;
            opacity: 0.5
        }

        ::-webkit-scrollbar-thumb:hover {
            height: 50px;
            background-color: #878987;
            -webkit-border-radius: 6px
        }

        @font-face {
            font-family: 'iconfont';
            src: url('../fonts/iconfont.eot?1464535104');
            src: url('../fonts/iconfont.eot?&1464535104#iefix') format("embedded-opentype"), url('../fonts/iconfont.woff?1464535104') format("woff"), url('../fonts/iconfont.ttf?1464535104') format("truetype"), url('../fonts/iconfont.svg?1464535104#iconfont') format("svg")
        }

        .iconfont {
            font-family: "iconfont" !important;
            font-size: 16px;
            font-style: normal;
            -webkit-font-smoothing: antialiased;
            -webkit-text-stroke-width: 0.2px;
            -moz-osx-font-smoothing: grayscale
        }

        .kv-table {
            border-right: 1px solid #cacaca \9;
            *border-right: 1px solid #cacaca
        }

        .kv-table .kv-table-row {
            border-bottom: 1px solid #cacaca
        }

        .kv-table .kv-table-row .kv-item {
            padding-left: 134px
        }

        .kv-table .kv-table-row .kv-item .kv-label {
            float: left;
            padding: 0 10px;
            margin-left: -134px;
            width: 112px;
            background: #f5f5f5;
            border: 1px solid #cacaca;
            border-bottom: none;
            border-top: none
        }

        .kv-table .kv-table-row .kv-item .kv-content-wrap {
            float: left;
            width: 100%
        }

        .kv-table .kv-table-row .kv-item .kv-content {
            padding: 10px
        }

        .kv-table .kv-table-row.col-3 .kv-item-wrap {
            float: left;
            width: 33.33%
        }

        .kv-table .kv-table-row.col-2 .kv-item-wrap {
            float: left;
            width: 33.33%
        }

        table.kv-table {
            width: 100%
        }

        table.kv-table .kv-label {
            padding: 0 10px;
            width: 114px;
            background: #f5f5f5;
            border: 1px solid #cacaca;
            border-top: none
        }

        table.kv-table td.kv-content, table.kv-table td.kv-label {
            height: 29px;
            padding: 5px 0;
            border-bottom: 1px solid #cacaca;
            font-size: 14px;
            padding-left: 20px
        }

        table.kv-table tr:first-child td.kv-content, table.kv-table tr:first-child td.kv-label {
            border-top: 1px solid #cacaca
        }

        table.kv-table tr td.kv-content:last-child {
            border-right: 1px solid #cacaca
        }

        table.kv-table tr .button {
            text-align: center;
            border-radius: 0;
            text-indent: 0;
            height: 32px
        }

        table.kv-table .kv-content {
            width: 260px;
            padding: 5px 10px
        }

        table.kv-table .textarea-wrap textarea {
            width: 98%
        }

        html, body {
            height: 100%
        }

        body {
            background-color: #f1f1f1
        }

        body.white {
            background-color: #fff
        }

        body.white .login-hd {
            background: #fff
        }

        body.white .login-hd .left-bg {
            display: none
        }

        body.white .login-hd .right-bg {
            display: none
        }

        body.white .login-hd .logo {
            background: url(resources/images/login_logo_w.png) no-repeat
        }

        body.white .login-hd .split {
            background-color: #ccc
        }

        body.white .login-bd {
            background-color: #f1f1f1
        }

        body.white .login-bd .lg-zone {
            background: url(resources/images/login_bg_tubiao.png) center 46px no-repeat
        }

        body.white .login-bd .lg-zone .lg-box .lg-label h4 {
            border-left: none
        }

        .login-hd {
            position: relative;
            height: 94px;
            background: #09825b;
            line-height: 94px;
            min-width: 1200px
        }

        .login-hd .left-bg {
            position: absolute;
            width: 348px;
            height: 92px;
            left: 0;
            top: 0;
            background: url(resources/images/login_top_left.png) no-repeat
        }

        .login-hd .right-bg {
            position: absolute;
            width: 267px;
            height: 92px;
            right: 0;
            top: 0;
            background: url(resources/images/login_top_right.png) no-repeat
        }

        .login-hd .hd-inner {
            width: 1200px;
            height: 94px;
            line-height: 94px;
            margin: 0 auto
        }

        .login-hd .hd-inner .logo {
            position: relative;
            display: inline-block;
            width: 245px;
            height: 73px;
            margin-left: 15px;
            margin-top: 10px;
            margin-right: 10px;
            background: url(resources/images/login_logo.png) no-repeat
        }

        .login-hd .hd-inner .split {
            position: relative;
            display: inline-block;
            height: 32px;
            width: 1px;
            top: -17px;
            margin-right: 10px;
            background-color: #51b65d
        }

        .login-hd .hd-inner .sys-name {
            display: inline-block;
            position: relative;
            color: #000;
            font-size: 22px;
            top: -31px
        }

        .login-bd {
            position: relative;
            border: 1px solid #e5e5e5;
            background-color: #fff;
            min-width: 1200px
        }

        .login-bd .bd-inner {
            width: 1200px;
            margin: 0 auto
        }

        .login-bd .bd-inner .inner-wrap {
            position: relative;
            padding-right: 504px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone {
            position: absolute;
            right: 0;
            top: 0;
            bottom: 0;
            width: 504px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .alert {
            margin: 0 33px 10px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box {
            position: absolute;
            right: 30px;
            top: 46px;
            background: #fff;
            box-shadow: 2px 2px 3px #ddd, -2px 0 3px #ddd;
            width: 364px;
            padding-bottom: 20px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .line {
            position: absolute;
            left: 0;
            top: 0;
            height: 6px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .line.line-y {
            width: 127px;
            background: #ff9d02
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .line.line-g {
            left: 127px;
            right: 0;
            background: #1da02d
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .lg-label {
            color: #000;
            font-size: 22px;
            height: 44px;
            line-height: 44px;
            margin-top: 23px;
            text-indent: 30px;
            margin-bottom: 20px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .lg-label h4 {
            border-left: 4px solid #09825b
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .input-item {
            margin-bottom: 22px;
            width: 296px;
            line-height: 40px;
            height: 40px;
            border-top: 1px solid #adadad;
            border-left: 1px solid #adadad;
            border-right: 1px solid #cfcfcf;
            border-bottom: 1px solid #cfcfcf;
            margin-left: 34px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .input-item i {
            float: left;
            display: inline-block;
            width: 40px;
            height: 40px;
            line-height: 40px;
            text-align: center;
            color: #5d5c68
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .input-item input {
            border: 0;
            height: 40px;
            padding: 0;
            width: 236px;
            float: left;
            outline: none;
            text-decoration: none
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .input-item input:focus {
            outline: none;
            text-decoration: none
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .lg-check {
            margin-bottom: 10px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .lg-check .input-item {
            float: left;
            width: 172px;
            margin-right: 10px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .lg-check .input-item input {
            width: 132px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .lg-check .check-code {
            float: left;
            display: inline-block;
            height: 42px;
            line-height: 42px;
            background-color: #ff9d02;
            color: #fff;
            width: 114px;
            text-align: center;
            font-size: 14px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .tips {
            height: 20px;
            line-height: 20px;
            margin-bottom: 30px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .tips input[type=checkbox] {
            position: relative;
            margin-left: 34px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .tips label {
            font-size: 14px;
            color: #a3a3a3
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .tips a {
            float: right;
            font-size: 12px;
            text-decoration: none
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .tips a:hover {
            text-decoration: underline
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .tips a.register {
            color: #09825b;
            margin-left: 12px;
            margin-right: 34px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .tips a.forget-pwd {
            color: #000
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .enter a {
            display: inline-block;
            text-decoration: none;
            height: 48px;
            line-height: 48px;
            text-align: center;
            color: #fff;
            font-size: 16px;
            width: 142px;
            font-weight: bold
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .enter a.purchaser {
            margin-left: 34px;
            background-color: #f45438;
            margin-right: 9px
        }

        .login-bd .bd-inner .inner-wrap .lg-zone .lg-box .enter a.supplier {
            background-color: #09825b
        }

        .login-bd .bd-inner .inner-wrap .lg-poster {
            height: 541px;
            background: url(resources/images/loginbg.png) right center no-repeat
        }

        .login-ft {
            padding-top: 20px;
            min-width: 1200px
        }

        .login-ft .ft-inner {
            margin: 0 auto
        }

        .login-ft .ft-inner .about-us {
            height: 20px;
            line-height: 20px;
            width: 295px;
            margin: 0 auto;
            margin-bottom: 10px
        }

        .login-ft .ft-inner .about-us a {
            color: #666;
            text-decoration: none;
            font-size: 14px;
            float: left;
            margin-left: 15px
        }

        .login-ft .ft-inner .about-us a:hover {
            text-decoration: underline
        }

        .login-ft .ft-inner .address {
            text-align: center;
            color: #999;
            font-size: 12px;
            margin-bottom: 10px
        }

        .login-ft .ft-inner .other-info {
            text-align: center;
            color: #999;
            font-size: 12px;
            margin-bottom: 50px
        }

        input:-webkit-autofill,
        textarea:-webkit-autofill,
        select:-webkit-autofill {
            -webkit-box-shadow: 0 0 0 1000px white inset;
        }
    </style>


</head>
<body>
<div class="login-hd">
    <div class="left-bg"></div>
    <div class="right-bg"></div>
    <div class="hd-inner">
        <span class="logo"></span>
        <span class="split"></span>
        <span class="sys-name">采购协同平台</span>
    </div>
</div>
<div class="login-bd">
    <div class="bd-inner">
        <div class="inner-wrap">
            <div class="lg-zone">
                <div class="lg-box">
                    <div class="lg-label"><h4>用户登录</h4></div>
                <#--<div class="alert alert-error">-->
                <#--<i class="iconfont">&#xe62e;</i>-->
                <#--<span>请输入用户名</span>-->
                <#--</div>-->
                    <form id="formlogin" action="login/userlogin" method="post" autocomplete="off">
                        <div class="lg-username input-item clearfix">
                            <i class="icon Hui-iconfont">&#xe60d;</i>
                            <input type="text" placeholder="账号/邮箱" id="username" name="username" autocomplete="off">
                        </div>
                        <div class="lg-password input-item clearfix">
                            <i class="icon Hui-iconfont">&#xe63f;</i>
                            <input type="password" placeholder="请输入密码" id="password" name="password" autocomplete="off">
                        </div>
                        <#--<div class="lg-check clearfix">-->
                            <#--<div class="input-item">-->
                                <#--<i class="icon Hui-iconfont">&#xe633;</i>-->
                                <#--<input type="text" placeholder="验证码">-->
                            <#--</div>-->
                            <#--<span class="check-code">XD34F</span>-->
                        <#--</div>-->
                        <div class="tips clearfix">
                            <label><input type="checkbox" checked="checked">记住用户名</label>
                            <a href="javascript:;" class="register">立即注册</a>
                            <a href="javascript:;" class="forget-pwd">忘记密码？</a>
                        </div>
                        <div class="enter">
                            <a href="javascript:;" class="purchaser" onClick="javascript:document.getElementById('formlogin').submit()">采购商登录</a>
                            <a href="javascript:;" class="supplier"  onClick="javascript:window.location='welcome'">供应商登录</a>
                        </div>
                    </form>
                </div>
            </div>
            <div class="lg-poster"></div>
        </div>
    </div>
</div>
<div class="login-ft">
    <div class="ft-inner">
        <div class="about-us">
            <a href="javascript:;">关于我们</a>
            <a href="javascript:;">法律声明</a>
            <a href="javascript:;">服务条款</a>
            <a href="javascript:;">联系方式</a>
        </div>
        <div class="address">地址：上海市松江区古楼公路&nbsp;邮编：210019&nbsp;&nbsp;Copyright&nbsp;©&nbsp;2016&nbsp;-&nbsp;2017&nbsp;payment-专注于框架设计&nbsp;版权所有</div>
        <div class="other-info">建议使用IE8及以上版本浏览器&nbsp;沪ICP备&nbsp;012345678号&nbsp;E-mail：admin@easyui.com</div>
    </div>
</div>


<script>
    var errori =${error};
if(errori=='yes'){
    alert("用户名或密码错误！");
}
</script>

</body>
</html>

