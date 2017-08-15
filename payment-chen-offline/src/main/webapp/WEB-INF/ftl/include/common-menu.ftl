<style type="text/css">
    #main-navbar-collapse ul.navigation li {
        font-weight: bolder;
    }
    .menu-selected {
        background: rgba(111, 111, 111, 0.18);
        border-bottom: 3px solid #FFFFFF;
    }
</style>
<div id="main-navbar" class="navbar navbar-inverse" role="navigation">
    <button type="button" id="main-menu-toggle"><i class="navbar-icon fa fa-bars icon"></i><span class="hide-menu-text">HIDE MENU</span>
    </button>
    <div class="navbar-inner">
        <div class="navbar-header">
            <!-- Logo -->
            <div class="navbar-brand">
                <div><img alt="offline" src="${basePath}/resources/assets/images/pixel-admin/main-navbar-logo.png">
                </div>
                BNB OFFLINE
            </div>
        </div>
        <!-- 顶部菜单 -->
        <div id="main-navbar-collapse" class="collapse navbar-collapse main-navbar-collapse">
            <div>
                <ul class="navigation nav navbar-nav">
                    <li>
                        <a href="${basePath}/order-center">订单中心</a>
                    </li>
                    <li>
                        <a href="${basePath}/quality-assurance">品控中心</a>
                    </li>
                    <li>
                        <a href="${basePath}/merchant">商户中心</a>
                    </li>
                    <li>
                        <a href="${basePath}/settle">结算中心</a>
                    </li>
                    <li>
                        <a href="${basePath}/operation-center">运营中心</a>
                    </li>
                    <li>
                        <a href="${basePath}/data-center">数据中心</a>
                    </li>
                    <li>
                        <a href="${basePath}/sys">系统管理</a>
                    </li>
                </ul>


                <div class="right clearfix">
                    <ul class="nav navbar-nav pull-right right-navbar-nav">
                        <li>
                            <form class="navbar-form pull-left">
                                <input type="text" class="form-control" placeholder="Search">
                            </form>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle user-menu" data-toggle="dropdown">
                                <span>sy石阳</span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="${basePath}/account/logout"><i class="dropdown-icon fa fa-sign-out"></i>&nbsp;&nbsp;退出</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 左侧菜单 -->
<div id="main-menu" role="navigation">
    <div id="main-menu-inner">
        <div class="menu-content top" id="menu-content-demo">
            <div>
                <div class="text-bg" style="font-size: 13px;"><span class="text-slim">欢迎,</span> <span
                        class="text-semibold">石阳</span></div>

                <img src="${basePath}/resources/assets/demo/avatars/1.jpg" alt="" class="">

                <div class="btn-group">
                    <a href="#" class="btn btn-xs btn-primary btn-outline dark"><i class="fa fa-envelope"></i></a>
                    <a href="#" class="btn btn-xs btn-primary btn-outline dark"><i class="fa fa-user"></i></a>
                    <a href="#" class="btn btn-xs btn-primary btn-outline dark"><i class="fa fa-cog"></i></a>
                    <a style="cursor:pointer;" href="${basePath}/account/logout"
                       class="btn btn-xs btn-danger btn-outline dark">
                        <i class="fa fa-sign-out"></i>
                    </a>
                </div>
                <a href="#" class="close">&times;</a>
            </div>
        </div>
        <!-- 左侧菜单 -->
        <ul class="navigation">
            <!-- 订单管理 -->
            <li class="mm-dropdown mm-dropdown-root">
                <a href="${basePath}/order-center"><i class="menu-icon fa fa-building-o"></i><span
                        class="mm-text mmc-dropdown-delay animated fadeIn">订单中心</span></a>
                <ul class="mmc-dropdown-delay animated fadeInLeft">
                    <li>
                        <a href="${basePath}/order-center/order"><span class="mm-text">订单DB映射</span></a>
                    </li>


                    <li>
                        <a href="${basePath}/order-center/customer-service"><span class="mm-text">订单管理</span></a>
                    </li>
                </ul>
            </li>

            <li class="mm-dropdown mm-dropdown-root">
                <a href="${basePath}/quality-assurance"><i class="menu-icon fa fa-medkit"></i><span
                        class="mm-text mmc-dropdown-delay animated fadeIn">品控中心</span></a>
                <ul class="mmc-dropdown-delay animated fadeInLeft">
                    <li>
                        <a href="${basePath}/product/product-audit-task"><span class="mm-text">房源审核任务处理</span></a>
                    </li>
                    <li>
                        <a href="${basePath}/product"><span class="mm-text">房源管理</span></a>
                    </li>
                    <li>
                        <a href="${basePath}/product/switch"><span class="mm-text">房源开关管理</span></a>
                    </li>
                    <li>
                        <a href="${basePath}/product/tag"><span class="mm-text">房源标签管理</span></a>
                    </li>
                    <li>
                        <a href="${basePath}/product/distribution/spaces/manage"><span
                                class="mm-text">分销产品管理</span></a>
                    </li>
                </ul>
            </li>
            <li class="mm-dropdown mm-dropdown-root open active open">
                <a href="${basePath}/sys"><i class="menu-icon fa fa-cogs"></i><span
                        class="mm-text mmc-dropdown-delay animated fadeIn">系统管理</span></a>
                <ul class="mmc-dropdown-delay animated fadeInLeft">
                    <li class="mm-dropdown open active open">
                        <a tabindex="-1" href="${basePath}/sys/authority"><span class="mm-text">权限分配管理</span><span
                                class="label label-warning">3</span></a>
                        <ul class="" style="">
                            <li>
                                <a tabindex="-1" href="${basePath}/sys/authority/resource"><span
                                        class="mm-text">权限管理</span></a>
                            </li>
                            <li>
                                <a tabindex="-1" href="${basePath}/sys/authority/role"><span
                                        class="mm-text">角色管理</span></a>
                            </li>
                            <li class=" active open">
                                <a tabindex="-1" href="${basePath}/sys/authority/user"><span
                                        class="mm-text">人员管理</span></a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="${basePath}/sys/configure"><span class="mm-text">Key-Value配置</span></a>
                    </li>

                    <li>
                        <a href="${basePath}/sys/db-back-door"><span class="mm-text">数据更新</span></a>
                    </li>


                </ul>
            </li>
            <li class="mm-dropdown mm-dropdown-root">
                <a href="${basePath}/lab"><i class="menu-icon fa fa-flask"></i><span
                        class="mm-text mmc-dropdown-delay animated fadeIn">实验室</span></a>
                <ul class="mmc-dropdown-delay animated fadeInLeft">
                    <li>
                        <a tabindex="-1" href="${basePath}/lab/socket"><span class="mm-text">Socket端口测试</span></a>
                    </li>
                    <li>
                        <a tabindex="-1" href="${basePath}/lab/springurl"><span class="mm-text">SpringURL映射</span></a>
                    </li>
                    <li>
                        <a tabindex="-1" href="${basePath}/lab/test/wpf"><span class="mm-text">WPF测试页面</span></a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <!-- / #main-menu-inner -->
</div> <!-- / #main-menu -->

<div id="main-menu-bg"></div>