/**
 * Created by LiYonglei on 2016/4/11.
 */
$(function () {
    /*主页左侧tabs*/
    (function leftTabs() {
        $.get(PATH.index.leftTabs).done(function (res) {
            generateLeftTabs(res, $("#leftTabs"));
            /*
             * 这个方法是为了让第一个有子页面链接的按钮被显示出来并且子页面加载 ps:插件没有api;下面的代码写的很烂。。。
             * */
            (function expandedFirstPageBtn() {
                var firstPageBtn = $("a[data-url]", $("#leftTabs")).eq(0);
                firstPageBtn.parentsUntil(".sidebar-nav", "ul[aria-expanded=false]").each(function (idx, ul) {
                    $(ul).css({ "height": "" }).addClass("in");
                    $(ul).parent("li").addClass("active");
                });
                firstPageBtn.click();
            })();
        });
        /*
        * 当点击的左侧的tabs列表中的节点拥有data-url的时候；右侧加载对应的页面；当要加载的页面已经存在的时候就不再加载而只是显示；
        * 将其他的已经存在的页面隐藏掉
        * */
        $("html").on("click", "#leftTabs a", function (event) {
            var url = this.dataset.url;
            if (url) {
                $(event.delegateTarget).find(".customActive").removeClass("customActive");
                $(this).addClass("customActive");
                var pageContent = $("#indexMainContent");
                pageContent.children().addClass("hide");
                pageId = url.replace(/\//g, "-");
                if ($("#" + pageId + "Page").length) {
                    $("#" + pageId + "Page").removeClass("hide");
                    var updateSearch = $("#" + pageId + "Page").data("updateSearch");
                    if (updateSearch) {
                        updateSearch.forEach(function (updateFunction) {
                            updateFunction();
                        });
                    }
                } else {
                    var page = url.substring(url.lastIndexOf("/"));
                    var folder = url;
                    var newPage = $("<div/>", {
                        "id": pageId + "Page",
                        "class": "pagePanel"
                    }).load(appContextPath + "/src/index/children/" + folder + page + ".html").appendTo(pageContent);
                }
            }
        });
    })();
    /*
     * 用户右上角菜单的操作
     * */
    (function userOperate() {
        var userInfo = {};
        $.get(PATH.index.userOperate.getUser).done(function (res) {
            $.extend(userInfo, res.data);
        });
        $.get(PATH.index.userOperate.getUserRole).done(function (res) {
            $.extend(userInfo, res.data);
        });
        $("#userInfoBtn").on("click", function () {
            userInfo.creattime = new Date(userInfo.creattime).format("yyyy-MM-dd");
            $("#userInfoForm").fillForm(userInfo);
        });
        (function updateUserPwd() {
            $("#updateUserPwdModalBtn").on("click", function () {
                $("#updateUserPwdForm").fillForm({ id: userInfo.id });
            });
            $('#updateUserPwdModal').on('hide.bs.modal', function (e) {
                $("#updateUserPwdForm").data('formValidation').resetForm(true);
            });
            $('#updateUserPwdForm').formValidation({
                framework: 'bootstrap',
                locale: 'zh_CN',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    originalPwd: {
                        validators: {
                            notEmpty: {
                                message: '原密码不能为空'
                            }
                        }
                    },
                    newPwd: {
                        validators: {
                            notEmpty: {
                                message: '新密码不能为空'
                            }
                        }
                    },
                    confirmPwd: {
                        validators: {
                            notEmpty: {
                                message: '确认密码不能为空'
                            },
                            identical: {
                                field: 'newPwd',
                                message: '确认密码跟新密码不一致'
                            }
                        }
                    }
                }
            }).on('status.field.fv', function (e, data) {
                var btn = $(e.currentTarget).closest(".modal").find(".submitBtn");
                btn.prop({ "disabled": !formValidationIsValid(e) });
            });
            $("#updateUserPwdBtn").on("click", function () {
                var _this = this;
                var form = $(_this).closest(".modal").find("form");
                var formValidation = $(form).data('formValidation');
                formValidation.validate();
                if (!formValidation.isValid()) {
                    return false;
                }
                var data = form.serializeForm();
                $.post(PATH.index.userOperate.updateUserPwd, data).done(function (res) {
                    $(_this).closest(".modal").modal('hide');
                    BootstrapDialog.show({
                        title: '成功',
                        message: '操作成功',
                        type: BootstrapDialog.TYPE_SUCCESS,
                        onshown: function (dialogRef) {
                            setTimeout(function () {
                                dialogRef.close();
                            }, 500);
                        }
                    });
                });
            });
            $("#logout").on("click", function () {
                $.get(PATH.index.userOperate.logout).done(function (res) {
                    if (res.success) {
                        location.href = appContextPath + "/";
                    }
                });
            });
        })();
    })();
});