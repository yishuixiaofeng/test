/**
 * Created by LiYonglei on 2016/4/14.
 */
$(function () {
    var tableComponent;
    var loadParams = {
        currentPage: 0,
        pageCount: 15,
        keyword: ""
    };
    /*
     * 定义一个数组用来盛放可以更新的检索条件函数
     * */
    var updateSearch = $(".pagePanel:visible").data({ "updateSearch": [] }).data("updateSearch");
    function getRoleList() {
        return $.get(PATH.index.user.list.getRoleList).done(function (res) {
            $("#userListgetPageUsersAndUserRoleSelectContainer").empty();
            var data = res.data;
            data.forEach(function (item, idx) {
                var btn = $("<input/>", {
                    "class": "btn btn-default",
                    type: "button",
                    val: item.name,
                    click: function () {
                        $(this).addClass("active").siblings(".btn").removeClass("active");
                        $("#userListTableFilterBar").get(0).reset();
                        $.extend(loadParams, {
                            f_role_id: item.id,
                            currentPage: 0,
                            keyword: ""
                        });
                        loadData();
                    }
                }).appendTo("#userListgetPageUsersAndUserRoleSelectContainer");
            });
        }).done(function (res) {
            $("#userListFormGetPageUsersAndUserRoleSelect").empty();
            var data = res.data;
            data.forEach(function (item, idx) {
                var option = $("<option/>", {
                    val: item.id,
                    text: item.name
                }).appendTo("#userListFormGetPageUsersAndUserRoleSelect");
            });
        });
    };
    getRoleList();
    updateSearch.push(getRoleList);
    function loadData() {
        return $.get(PATH.index.user.list.loadData, loadParams).done(function (res) {
            var data = res.data;
            var total = res.count;
            tableComponent.setState({ data: data });
            generatePagination({
                total: total,
                container: $("#userListTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function tableFilterBar() {
        $("#userSearchBtn").on("click", function () {
            var params = $(this).closest("form").serializeForm();
            $.extend(loadParams, {
                f_role_id: 0,
                currentPage: 0
            }, params);
            $(this).closest(".tableFilterBar").find(".extendFilterContainer .btn").removeClass("active");
            loadData();
        });
    })();
    (function tableToolbar() {
        $("#userListTableUpdate").on("click", function (event) {
            var data = tableComponent.state.data.filter(function (item) {
                return item.checked;
            })[0];
            if (!data) {
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message: '请选择将要修改的用户!',
                    buttons: [{
                        label: '确定',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
                event.preventDefault();
                event.stopPropagation();
                return;
            }
            $("#userListTableForm").fillForm(data);
        });
        $('#userListTableForm').formValidation({
            framework: 'bootstrap',
            locale: 'zh_CN',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                username: {
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        }
                    }
                },
                phone: {
                    validators: {
                        phone: {
                            country: 'CN',
                            message: '手机号码格式不正确'
                        }
                    }
                }
            }
        }).on('status.field.fv', function (e, data) {
            var btn = $(e.currentTarget).closest(".modal").find(".submitBtn");
            btn.prop({ "disabled": !formValidationIsValid(e) });
        });
        $("#userListTableBtn").on('click', function () {
            var _this = this;
            var form = $(_this).closest(".modal").find("form");
            var formValidation = $(form).data('formValidation');
            formValidation.validate();
            if (!formValidation.isValid()) {
                return false;
            }
            var data = form.serializeForm();
            var send;
            if (data.id !== "") {
                var datas = tableComponent.state.data;
                datas.every(function (item, idx) {
                    if (item.id == data.id) {
                        $.extend(true, datas[idx], data);
                        tableComponent.setState({ data: datas });
                        return false;
                    }
                    return true;
                });
                send = $.get(PATH.index.user.list.toolbar.updateUser, data);
            } else {
                delete data.id;
                send = $.get(PATH.index.user.list.toolbar.saveUser, data);
            }
            send.done(function (res) {
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
                loadData();
            });
        });
        $("#userListTableDelete").on('click', function () {
            var _this = this;
            var ids = tableComponent.state.data.filter(function (item) {
                return item.checked;
            }).map(function (item) {
                return item.id;
            }).toString();
            if (!ids) {
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message: '请选择将要删除的用户!',
                    buttons: [{
                        label: '确定',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
            } else {
                $(_this).prop("disabled", true).find(".spinning").removeClass("hide");
                $.get(PATH.index.user.list.toolbar.deleteUser, { id: ids }).done(function (res) {
                    $(_this).prop("disabled", false).find(".spinning").addClass("hide");
                    if (res.success) {
                        BootstrapDialog.show({
                            title: '成功',
                            message: '用户删除成功!',
                            type: BootstrapDialog.TYPE_SUCCESS,
                            onshown: function (dialogRef) {
                                setTimeout(function () {
                                    dialogRef.close();
                                }, 1000);
                            }
                        });
                        loadData();
                    } else {
                        BootstrapDialog.show({
                            type: BootstrapDialog.TYPE_WARNING,
                            title: '失败',
                            draggable: true,
                            message: res.errorMsg,
                            buttons: [{
                                label: '确定',
                                action: function (dialog) {
                                    dialog.close();
                                }
                            }]
                        });
                    }
                });
            }
        });
    })();
    (function table() {
        var option = {
            columns: [{ field: 'username', title: '用户名' }, { field: 'f_role_id', title: '角色', formatter: function (value, props) {
                    return props.f_role_name;
                } }, { field: 'name', title: '真实姓名' }, { field: 'phone', title: '手机号' }, { field: 'department', title: '部门名称' }, { field: "description", title: "描述信息" }, { field: "creattime", title: "创建时间",
                formatter: function (value) {
                    if (value) {
                        return new Date(value).format("yyyy-MM-dd");
                    }
                }
            }],
            showCheckbox: false,
            detail: {
                show: false,
                formatter: function (data, option) {}
            }
        };
        tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#userListTable"));
        loadData();
    })();
}());