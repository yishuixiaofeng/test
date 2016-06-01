/**
 * Created by LiYonglei on 2016/4/14.
 */
$(function () {
    var tableComponent;
    var loadParams = {
        currentPage: 0,
        pageCount: 15
    };
    function loadData() {
        return $.get(PATH.index.menu.list.loadData, loadParams).done(function (res) {
            var data = res.data;
            var total = res.count;
            tableComponent.setState({ data: data });
            generatePagination({
                total: total,
                container: $("#menuListTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function tableFilterBar() {
        $("#menuSearchBtn").on("click", function () {
            loadData();
        });
    })();
    (function tableToolbar() {
        $("#menuListTableUpdate").on("click", function (event) {
            var data = tableComponent.state.data.filter(function (item) {
                return item.checked;
            })[0];
            if (!data) {
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message: '请选择将要修改的菜单!',
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
            $(this.dataset.target).find(".modalHeaderText").addClass("updateText");
            $("#menuListTableForm").fillForm(data);
        });
        $("#selectMenuListParent").on("click", function () {
            var _this = this;
            $(_this).prop({ "disabled": true });
            var dialog = BootstrapDialog.show({
                title: '请选择父级节点',
                draggable: true,
                size: BootstrapDialog.SIZE_WIDE,
                closable: true,
                closeByBackdrop: false,
                closeByKeyboard: false,
                message: $('<div class="row"></div>').load(appContextPath + "/src/index/children/menu/list/selectParent/selectParent.html"),
                onhidden: function () {
                    $(_this).prop({ "disabled": false });
                },
                buttons: [{
                    label: "取消",
                    action: function (dialogRef) {
                        dialogRef.close();
                    }
                }, {
                    label: "确定",
                    action: function (dialogRef) {
                        /*
                         * selectMenuListParentPanel是定义于selectParent.js的全局变量
                         * */
                        var selectParentTableComponent = selectMenuListParentPanel.tableComponent;
                        var data = selectParentTableComponent.state.data.filter(function (item) {
                            if (item.checked) {
                                return true;
                            }
                        });
                        if (!data.length) {
                            $("#selectMenuListParent").val("").prev("[name=f_parent_id]").val("");
                        } else {
                            var f_parent_id = data[0].id;
                            var f_parent_name = data[0].name;
                            $("#selectMenuListParent").val(f_parent_name).prev("[name=f_parent_id]").val(f_parent_id);
                        }
                        dialogRef.close();
                    }
                }]
            });
        });
        $('#menuListTableForm').formValidation({
            framework: 'bootstrap',
            locale: 'zh_CN',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '菜单名称不能为空'
                        }
                    }
                },
                web_key: {
                    validators: {
                        callback: {
                            message: '当拥有父菜单的时候，面板路径不能为空',
                            callback: function (value, validator, $field) {
                                if ($("#selectMenuListParent").val() && !value.trim()) {
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }).on('status.field.fv', function (e, data) {
            var btn = $(e.currentTarget).closest(".modal").find(".submitBtn");
            btn.prop({ "disabled": !formValidationIsValid(e) });
        });
        $("#menuListTableBtn").on('click', function () {
            var _this = this;
            var form = $(_this).closest(".modal").find("form");
            var formValidation = $(form).data('formValidation');
            formValidation.validate();
            if (!formValidation.isValid()) {
                return false;
            }
            var data = form.serializeForm();
            data.f_parent_id = data.f_parent_id || 0;
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
                send = $.get(PATH.index.menu.list.toolbar.updateSysMenu, data);
            } else {
                send = $.get(PATH.index.menu.list.toolbar.saveSysMenu, data);
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
                loadData().done(function (res) {
                    $.get(PATH.index.leftTabs).done(function (res) {
                        generateLeftTabs(res, $("#leftTabs"));
                    });
                });
            });
        });
        $("#menuListTableDelete").on('click', function () {
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
                    message: '请选择将要删除的菜单!',
                    buttons: [{
                        label: '确定',
                        action: function (dialog) {
                            dialog.close();
                        }
                    }]
                });
            } else {
                $(_this).prop("disabled", true).find(".spinning").removeClass("hide");
                $.get(PATH.index.menu.list.toolbar.deleteSysMenu, { id: ids }).done(function (res) {
                    $(_this).prop("disabled", false).find(".spinning").addClass("hide");
                    BootstrapDialog.show({
                        title: '成功',
                        message: '菜单删除成功!',
                        type: BootstrapDialog.TYPE_SUCCESS,
                        onshown: function (dialogRef) {
                            setTimeout(function () {
                                dialogRef.close();
                            }, 1000);
                        }
                    });
                    loadData().done(function (res) {
                        $.get(PATH.index.leftTabs).done(function (res) {
                            generateLeftTabs(res, $("#leftTabs"));
                        });
                    });
                });
            }
        });
    })();
    (function table() {
        var option = {
            columns: [{ field: 'name', title: '菜单名称' }, { field: 'web_key', title: '面板路径' }, { field: 'urlicon', title: '图标路径' }, { field: 'f_parent_name', title: '父级元素' }],
            showCheckbox: false
        };
        tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#menuListTable"));
        loadData();
    })();
}());