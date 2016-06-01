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
    function loadData() {
        return $.get(PATH.index.server.assignserver.loadData, loadParams).done(function (res) {
            var data = res.data;
            var total = res.count;
            tableComponent.setState({ data: data });
            generatePagination({
                total: total,
                container: $("#serverAssignserverTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function tableFilterBar() {
        $("#assignserverSearchBtn").on("click", function () {
            var params = $(this).closest("form").serializeForm();
            $.extend(loadParams, {
                currentPage: 0
            }, params);
            loadData();
        });
    })();
    (function tableToolbar() {
        /*
        * 标记模态框中的表格是否已经初始化过了
        * */
        var isInit = false;
        var form = $("#serverAssignserverTableForm");
        $("#serverAssignserverTableAdd,#serverAssignserverTableUpdate").on("click", function () {
            if (this.dataset.operate == "add") {
                $("#serverAssignserverSelectUserTableContainer").removeClass("hide");
            } else {
                $("#serverAssignserverSelectUserTableContainer").addClass("hide");
            }
            if (isInit) {
                $("#serverAssignserverSelectUserSearchBtn,#serverAssignserverSelectServerSearchBtn").trigger("click");
                return true;
            } else {
                isInit = true;
            }
            initSelectUser();
            initSelectServer();
        });
        function initSelectUser() {
            var loadParams = {
                currentPage: 0,
                pageCount: 5,
                "f_role_id": 3
            };
            var tableComponent;
            function loadData() {
                return $.get(PATH.index.server.assignserver.toolbar.selectUser, loadParams).done(function (res) {
                    var data = res.data;
                    var total = res.count;
                    tableComponent.setState({ data: data });
                    generatePagination({
                        total: total,
                        container: $("#serverAssignserverSelectUserTablePagination"),
                        loadParams: loadParams,
                        loadFn: loadData
                    });
                });
            }
            (function table() {
                var option = {
                    columns: [{ field: 'username', title: '用户名' }, { field: 'name', title: '真实姓名' }, { field: 'phone', title: '手机号' }, { field: 'department', title: '部门名称' }, { field: "description", title: "描述信息" }],
                    showCheckbox: false,
                    onTrClick: function (id, formProps, formState, props, td) {
                        $("[name=f_user_id]", form).val(props.id);
                        $("[name=f_user_name]", form).val(props.username);
                    }
                };
                tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#serverAssignserverSelectUserTable"));
                loadData();
            })();
            $("#serverAssignserverSelectUserSearchBtn").on("click", function () {
                $.extend(loadParams, {
                    currentPage: 0
                });
                loadData();
            });
        }
        function initSelectServer() {
            var loadParams = {
                currentPage: 0,
                pageCount: 5
            };
            var tableComponent;
            function loadData() {
                return $.get(PATH.index.server.assignserver.toolbar.selectServer, loadParams).done(function (res) {
                    var data = res.data;
                    var total = res.count;
                    tableComponent.setState({ data: data });
                    generatePagination({
                        total: total,
                        container: $("#serverAssignserverSelectServerTablePagination"),
                        loadParams: loadParams,
                        loadFn: loadData
                    });
                });
            }
            (function table() {
                var option = {
                    columns: [{ field: 'name', title: '服务器名称' }, { field: 'ip', title: 'ip' }, { field: 'description', title: '描述' }, { field: 'status', title: '状态', formatter: function (value) {
                            if (value == -1) {
                                return "不可用";
                            } else {
                                return "可用";
                            }
                        } }],
                    showCheckbox: false,
                    onTrClick: function (id, formProps, formState, props, td) {
                        $("[name=f_server_id]", form).val(props.id);
                        $("[name=f_server_name]", form).val(props.name);
                        $("[name=f_server_ip]", form).val(props.ip);
                        $("[name=f_id]", form).val("");
                        $("[name=f_name]", form).val("");
                        $("[name=enterprisename]", form).val("");
                    }
                };
                tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#serverAssignserverSelectServerTable"));
                loadData();
            })();
            $("#serverAssignserverSelectServerSearchBtn").on("click", function () {
                $.extend(loadParams, {
                    currentPage: 0
                });
                loadData();
            });
        }
        $("#serverAssignserverTableModal").find(".next").on("click", function (event) {
            var panelContainer = $(this).closest(".pageTurningContainer").find(".pageTurningPanelContainer");
            var idx = panelContainer.find(".pageTurningPanel").index(panelContainer.find(".pageTurningActive"));
            if (idx == 0) {
                if (!form.find("[name=f_user_name]").val()) {
                    event.stopPropagation();
                    BootstrapDialog.show({
                        type: BootstrapDialog.TYPE_WARNING,
                        title: '警告',
                        draggable: true,
                        message: '请先选择用户!',
                        buttons: [{
                            label: '确定',
                            action: function (dialog) {
                                dialog.close();
                            }
                        }]
                    });
                }
            }
            if (idx == 1) {
                if (form.find("[name=f_server_id]").val()) {
                    initSelectUserIdentify();
                } else {
                    event.stopPropagation();
                    BootstrapDialog.show({
                        type: BootstrapDialog.TYPE_WARNING,
                        title: '警告',
                        draggable: true,
                        message: '请先选择服务器!',
                        buttons: [{
                            label: '确定',
                            action: function (dialog) {
                                dialog.close();
                            }
                        }]
                    });
                }
            }
        });
        function initSelectUserIdentify() {
            var loadParams = {
                currentPage: 0,
                pageCount: 5,
                f_server_id: form.find("[name=f_server_id]").val(),
                rolenumber: TYFO.filter.rolenumber
            };
            var tableComponent;
            function loadData() {
                return $.get(PATH.index.server.assignserver.toolbar.selectUserIdentify, loadParams).done(function (res) {
                    var data = res.data;
                    var total = res.count;
                    tableComponent.setState({ data: data });
                    generatePagination({
                        total: total,
                        container: $("#serverAssignserverSelectUserIdentifyTablePagination"),
                        loadParams: loadParams,
                        loadFn: loadData
                    });
                });
            }
            (function table() {
                var option = {
                    columns: [{ field: 'id', title: '筛选员id' }, { field: 'username', title: '名称' }, { field: 'enterprisename', title: '单位名称' }],
                    showCheckbox: false,
                    onTrClick: function (id, formProps, formState, props, td) {
                        $("[name=f_id]", form).val(props.id);
                        $("[name=f_name]", form).val(props.username);
                        $("[name=enterprisename]", form).val(props.enterprisename);
                    }
                };
                tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#serverAssignserverSelectUserIdentifyTable"));
                loadData();
            })();
            $("#serverAssignserverSelectUserIdentifySearchBtn").off("click").on("click", function () {
                $.extend(loadParams, {
                    currentPage: 0
                });
                loadData();
            });
        }
        $("#serverAssignserverTableUpdate").on("click", function (event) {
            var data = tableComponent.state.data.filter(function (item) {
                return item.checked;
            })[0];
            if (!data) {
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message: '请选择将要修改的数据!',
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
            $("#serverAssignserverTableForm").fillForm(data);
        });
        $('#serverAssignserverTableForm').formValidation({
            framework: 'bootstrap',
            locale: 'zh_CN',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {}
        }).on('status.field.fv', function (e, data) {
            var btn = $(e.currentTarget).closest(".modal").find(".submitBtn");
            btn.prop({ "disabled": !formValidationIsValid(e) });
        });
        $("#serverAssignserverTableBtn").on('click', function () {
            var _this = this;
            var form = $(_this).closest(".modal").find("form");
            var data = form.serializeForm();
            var errorMsg = "";
            if (!data.f_user_id) {
                errorMsg = "用户不能为空!";
            } else if (!data.f_server_id) {
                errorMsg = "服务器不能为空!";
            } else if (!data.f_id) {
                errorMsg = "筛选员不能为空!";
            }
            if (errorMsg) {
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message: errorMsg,
                    buttons: [{
                        label: '确定',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
                return false;
            }
            userIdentifySendFn();
            function userIdentifySendFn() {
                var userIdentifySend;
                if (data.id) {
                    userIdentifySend = $.get(PATH.index.server.assignserver.toolbar.userIdentifySend.updateUserIdentify, data);
                } else {
                    userIdentifySend = $.get(PATH.index.server.assignserver.toolbar.userIdentifySend.saveUserIdentify, data);
                }
                userIdentifySend.done(function (res) {
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
            }
        });
        $("#serverAssignserverTableDelete").on('click', function () {
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
                    message: '请选择将要删除的数据!',
                    buttons: [{
                        label: '确定',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
            } else {
                $(_this).prop("disabled", true).find(".spinning").removeClass("hide");
                $.get(PATH.index.server.assignserver.toolbar.deleteUserIdentify, { id: ids }).done(function (res) {
                    $(_this).prop("disabled", false).find(".spinning").addClass("hide");
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
                });
            }
        });
    })();
    (function table() {
        var option = {
            columns: [{ field: 'f_user_name', title: '用户名称' }, { field: 'f_server_name', title: '服务器名称' }, { field: 'f_server_ip', title: '服务器ip' }, { field: "f_name", title: "筛选员名称" }, { field: "operate_time", title: "最后修改时间",
                formatter: function (value) {
                    if (value) {
                        return new Date(value).format("yyyy-MM-dd");
                    }
                } }],
            showCheckbox: false
        };
        tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#serverAssignserverTable"));
        loadData();
    })();
}());