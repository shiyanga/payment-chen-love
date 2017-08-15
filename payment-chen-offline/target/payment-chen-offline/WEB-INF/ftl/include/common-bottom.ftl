<script type="text/javascript">
    var basicPath='${basePath}';
    init.push(function () {
        window.addEventListener("load", function () {

            $('#menu-content-demo .close').click(function () {
                var $p = $(this).parents('.menu-content');
                $p.addClass('fadeOut');
                setTimeout(function () {
                    $p.css({ height: $p.outerHeight(), overflow: 'hidden' }).animate({'padding-top': 0, height: $('#main-navbar').outerHeight()}, 500, function () {
                        $p.remove();
                    });
                }, 300);
                return false;
            });
        });
    });
    window.PixelAdmin.start(init);

</script>

<script src="${basePath}/resources/assets/javascripts/theme-change.js"></script>
