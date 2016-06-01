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
        return $.get(PATH.index.server.serverinfo.loadData, loadParams).done(function (res) {
            var data = res.data;
            var total = res.count;
            tableComponent.setState({ data: data });
            generatePagination({
                total: total,
                container: $("#serverServerinfoTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function tableFilterBar() {
        $("#serverSearchBtn").on("click", function () {
            var params = $(this).closest("form").serializeForm();
            $.extend(loadParams, {
                currentPage: 0,
                method: 0
            }, params);
            loadData();
            $(this).closest(".tableFilterBar").find(".extendFilterContainer .btn").removeClass("active");
        });
        $("#serverSearchUndistributed").on("click", function () {
            $(this).addClass("active");
            $("#serverServerinfoTableFilterBar").get(0).reset();
            $.extend(loadParams, {
                currentPage: 0,
                keyword: "",
                method: 1
            });
            loadData();
        });
    })();
    (function tableToolbar() {
        $("#serverServerinfoTableUpdate").on("click", function (event) {
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
            $(this.dataset.target).find(".modalHeaderText").addClass("updateText");
            $("#serverServerinfoTableForm").fillForm(data);
        });
        $('#serverServerinfoTableForm').formValidation({
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
                            message: '服务器名称不能为空'
                        }
                    }
                },
                ip: {
                    validators: {
                        notEmpty: {
                            message: '服务器地址不能为空'
                        },
                        remote: {
                            url: PATH.index.server.serverinfo.toolbar.validateServer,
                            type: 'GET',
                            delay: 500,
                            validKey: "success",
                            data: function (validator, $field, value) {
                                /*
                                * 每次验证的时候都让提交按钮变为不可用状态
                                * */
                                setTimeout(function () {
                                    var btn = validator.$form.closest(".modal").find(".submitBtn");
                                    btn.prop({ "disabled": true });
                                }, 1);
                                return {
                                    host: validator.getFieldElements('ip').val()
                                };
                            },
                            message: "服务器地址不存在"
                        },
                        regexp: {
                            regexp: /^(https?:\/\/).+$/,
                            message: 'url格式不正确,可能没有写http://头部'
                        }
                    }
                }
            }
        }).on('status.field.fv', function (e, data) {
            var btn = $(e.currentTarget).closest(".modal").find(".submitBtn");
            btn.prop({ "disabled": !formValidationIsValid(e) });
        });
        $("#serverServerinfoTableBtn").on('click', function () {
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
                send = $.get(PATH.index.server.serverinfo.toolbar.updateServer, data);
            } else {
                send = $.get(PATH.index.server.serverinfo.toolbar.saveServer, data);
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
        $("#serverServerinfoTableDelete").on('click', function () {
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
                $.get(PATH.index.server.serverinfo.toolbar.deleteServer, { id: ids }).done(function (res) {
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
            columns: [{ field: 'name', title: '服务器名称' }, { field: 'ip', title: '服务器地址' }, { field: 'administrator', title: '联系人' }, { field: 'administrator_phone', title: '手机号' }, { field: 'description', title: '描述' }, { field: 'runStatus', title: '服务器状态', formatter: function (value) {
                    if (value == -1) {
                        return "<span class='state-warning'>异常</span>";
                    } else if (value == 0) {
                        return "<span class='state-normal'>正常</span>";
                    } else {
                        return "<span class='state-medium'>未知</span>";
                    }
                } }, { field: 'maxMemory', title: '最大内存', formatter: function (value) {
                    if (value) {
                        return value + "M";
                    }
                } }, { field: 'totalMemory', title: '总共内存', formatter: function (value) {
                    if (value) {
                        return value + "M";
                    }
                } }, { field: 'freeMemory', title: '可用内存', formatter: function (value) {
                    if (value) {
                        return value + "M";
                    }
                } }, { field: 'usedMemory', title: '已用内存', formatter: function (value) {
                    if (value) {
                        return value + "M";
                    }
                } }, { field: 'loadStatus', title: '负载状态', formatter: function (value) {
                    if (value !== undefined && value !== null && value !== "") {
                        if (value <= .4) {
                            return "<span class='state-normal'>低(" + parseInt(value * 100) + "%)</span>";
                        } else if (value <= .8) {
                            return "<span class='state-medium'>中(" + parseInt(value * 100) + "%)</span>";
                        } else {
                            return "<span class='state-warning'>中(" + parseInt(value * 100) + "%)</span>";
                        }
                    }
                } }, { field: 'creattime', title: '创建时间', formatter: function (value) {
                    if (value) {
                        return new Date(value).format("yyyy-MM-dd");
                    }
                } }],
            showCheckbox: false,
            onTdDblClick: function (props, td) {}
        };
        tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#serverServerinfoTable"));
        loadData();
    })();
}());