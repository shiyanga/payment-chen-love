<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

<#include "include/common-head.ftl"/>
</head>

<body class="theme-default main-menu-animated">

<script>var init = [];</script>

<div id="main-wrapper">
<#include "include/common-menu.ftl" encoding="UTF-8">

    <div id="content-wrapper">

        <div class="note note-info">TODO：此页以后用来展示帮助信息</div>
        <div class="search-tabs">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="#search-tabs-all" data-toggle="tab">帮助</a>
                </li>
            </ul>
            <!-- / .nav -->
        </div>
        <div class="panel search-panel">
            <form action="" class="search-form bg-primary">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon no-background"><i class="fa fa-search"></i></span>
                    <input type="text" name="s" class="form-control" placeholder="输入查询信息...">
                    <span class="input-group-btn">
						<button class="btn" type="submit">Search</button>
					</span>
                </div>
                <!-- / .input-group -->
            </form>
        </div>
        <div class="panel-body tab-content">
            <ul class="search-classic tab-pane fade in active" id="search-tabs-all">
                <h6 class="text-light-gray text-semibold text-xs" style="margin:20px 0 10px 0;">
                    有问题请联系：<a href="mailto:258184151@qq.com">258184151@qq.com</a>
                </h6>
            </ul>
        </div>
    </div>

</div>
<#include "include/common-bottom.ftl" encoding="UTF-8">
</body>
</html>