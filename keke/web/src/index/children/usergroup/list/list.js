/**
 * Created by LiYonglei on 2016/5/5.
 */
$(function () {
    var tableComponent;
    var loadParams = {
        keyword: ""
    };
    function loadData() {
        return $.get(PATH.index.usergroup.list.loadData, loadParams).done(function (res) {
            if (res.success) {
                var panelTitleData = res.data;
                var total = res.count;
                tableComponent.setState({ panelTitleData: panelTitleData });
            }
        });
    }
    (function tableFilterBar() {
        $("#usergroupSearchBtn").on("click", function () {
            var params = $(this).closest("form").serializeForm();
            $.extend(loadParams, params);
            loadData();
        });
    })();
    (function tableToolbar() {
        /*
         * 标记模态框中的表格是否已经初始化过了
         * */
        var isInit = false;
        var form = $("#usergroupListTableForm");
        var selectedUserIdTableComponent;
        var usergroupSelectUserIdLoadDataFn;
        $("#usergroupListTableAdd").on("click", function () {
            if (isInit) {
                selectedUserIdTableComponent.setState({ data: [] });
                usergroupSelectUserIdLoadDataFn();
                return true;
            } else {
                isInit = true;
            }
            initSelectCreator();
            initSelectUserId();
        });
        function initSelectCreator() {
            var loadParams = {
                currentPage: 0,
                pageCount: 5,
                f_role_id: 2
            };
            var tableComponent;
            function loadData() {
                return $.get(PATH.index.usergroup.list.toolbar.selectCreator, loadParams).done(function (res) {
                    if (typeof res == "string") {
                        res = JSON.parse(res);
                    }
                    var data = res.data;
                    var total = res.count;
                    data.forEach(function (item, idx) {
                        item.active = false;
                    });
                    tableComponent.setState({ data: data });
                    generatePagination({
                        component: tableComponent,
                        total: total,
                        container: $("#usergroupSelectCreatorTablePagination"),
                        loadParams: loadParams,
                        loadFn: loadData
                    });
                });
            }
            (function table() {
                var option = {
                    columns: [{ field: 'username', title: '客服管理员' }, { field: 'name', title: '真实姓名' }, { field: 'phone', title: '手机号' }, { field: 'department', title: '部门名称' }, { field: "description", title: "描述信息" }],
                    showCheckbox: false,
                    onTrClick: function (id, formProps, formState, props, td) {
                        $("[name=creator_id]", form).val(props.id);
                        $("[name=creator_name]", form).val(props.username);
                    }
                };
                tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#usergroupSelectCreatorTable"));
                loadData();
            })();
            $("#usergroupSelectCreatorSearchBtn").on("click", function () {
                loadData();
            });
        }
        function initSelectUserId() {
            var loadParams = {
                currentPage: 0,
                pageCount: 5,
                f_role_id: 3
            };
            var tableComponent;
            (function selectedUserIdTable() {
                var option = {
                    columns: [{ field: 'username', title: '客服专员' }, { field: 'name', title: '真实姓名' }, { field: 'phone', title: '手机号' }, { field: 'department', title: '部门名称' }, { field: "description", title: "描述信息" }],
                    showCheckbox: false,
                    onDblClickRow: function (props, td) {
                        var selectedUserIdTableData = selectedUserIdTableComponent.state.data.filter(function (item) {
                            return item.id != props.id;
                        });
                        selectedUserIdTableComponent.setState({ data: selectedUserIdTableData });
                    }
                };
                selectedUserIdTableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#usergroupSelectedUserIdTable"));
            })();
            function loadData() {
                usergroupSelectUserIdLoadDataFn = arguments.callee;
                return $.get(PATH.index.usergroup.list.toolbar.selectUserId, loadParams).done(function (res) {
                    var data = res.data;
                    var total = res.count;
                    tableComponent.setState({ data: data });
                    generatePagination({
                        total: total,
                        container: $("#usergroupSelectUserIdTablePagination"),
                        loadParams: loadParams,
                        loadFn: loadData
                    });
                });
            }
            (function table() {
                var option = {
                    columns: [{ field: 'username', title: '客服专员' }, { field: 'name', title: '真实姓名' }, { field: 'phone', title: '手机号' }, { field: 'department', title: '部门名称' }, { field: "description", title: "描述信息" }],
                    showCheckbox: false,
                    onTrClick: function (id, formProps, formState, props, td) {
                        var data = {
                            id: props.id,
                            username: props.username,
                            name: props.name,
                            phone: props.phone,
                            department: props.department,
                            description: props.description
                        };
                        var selectedUserIdTableData = selectedUserIdTableComponent.state.data;
                        var isHas = selectedUserIdTableData.some(function (item) {
                            return item.id == data.id;
                        });
                        if (isHas) {
                            return;
                        }
                        selectedUserIdTableComponent.setState({ data: selectedUserIdTableData.concat(data) });
                    }
                };
                tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#usergroupSelectUserIdTable"));
                loadData();
            })();
            $("#usergroupSelectUserIdSearchBtn").on("click", function () {
                loadData();
            });
        }
        $("#usergroupListTableModal").find(".next").on("click", function (event) {
            var panelContainer = $(this).closest(".pageTurningContainer").find(".pageTurningPanelContainer");
            var idx = panelContainer.find(".pageTurningPanel").index(panelContainer.find(".pageTurningActive"));
            if (idx == 0) {
                if (!form.find("[name=creator_id]").val()) {
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
        });
        $('#usergroupListTableForm').formValidation({
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
        $("#usergroupTableBtn").on('click', function () {
            var _this = this;
            var form = $(_this).closest(".modal").find("form");
            var f_user_ids = selectedUserIdTableComponent.state.data.map(function (item) {
                return item.id;
            }).toString();
            var creator_id = $("[name=creator_id]").val();
            var errorMsg = "";
            if (!creator_id) {
                errorMsg = "请选择客服管理员";
            } else if (!f_user_ids) {
                errorMsg = "请至少选择一个客服专员";
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
            $.get(PATH.index.usergroup.list.toolbar.saveUserGroups, { f_user_ids: f_user_ids, creator_id: creator_id }).done(function (res) {
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
        $("#usergroupListTableDelete").on('click', function () {
            var _this = this;
            var ids = tableComponent.state.panelTitleData.filter(function (panelTitleData) {
                return panelTitleData.open && panelTitleData.data.some(function (data) {
                    return !!data.active;
                });
            }).map(function (panelTitleData) {
                return panelTitleData.data.filter(function (data) {
                    return !!data.active;
                }).map(function (data) {
                    return data.id;
                }).toString();
            }).toString();
            if (!ids) {
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message: '请选择将要删除的客服专员!',
                    buttons: [{
                        label: '确定',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
            } else {
                $(_this).prop("disabled", true).find(".spinning").removeClass("hide");
                $.get(PATH.index.usergroup.list.toolbar.deleteUserGroups, { id: ids }).done(function (res) {
                    $(_this).prop("disabled", false).find(".spinning").addClass("hide");
                    BootstrapDialog.show({
                        title: '成功',
                        message: '删除成功!',
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
            columns: [{ field: 'f_user_name', title: '客服专员名称' }, { field: 'f_user_phone', title: '手机号' }],
            formatter: function (option, panelTitleData) {
                return "<span>" + panelTitleData["f_role_name"] + "/" + panelTitleData["username"] + "</span>&nbsp;&nbsp;<span>真实姓名/" + panelTitleData["name"] + "</span>";
            },
            onPanelTitleClick: function (panelTitleData, panelTitleElement) {
                $.get(PATH.index.usergroup.list.getUserGroupsList, { creator_id: panelTitleData.id }).done(function (res) {
                    if (res.success) {
                        var data = res.data;
                        var panelTitleDatas = tableComponent.state.panelTitleData;
                        panelTitleDatas.forEach(function (item) {
                            if (item.id == panelTitleData.id) {
                                item.data = data;
                            }
                        });
                        tableComponent.setState({ panelTitleData: panelTitleDatas });
                    }
                });
            }
        };
        tableComponent = ReactDOM.render(React.createElement(DatagridPanel, { option: option }), document.querySelector("#usergroupListTable"));
        loadData();
    })();
}());