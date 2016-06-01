/**
 * Created by chenhong on 2016/4/27.
 */
$(function () {
    var tableComponent;
    var loadParams = {
        currentPage: 0,
        pageCount: 15,
        keyword: ""
    };
    //查询系统参数列表
    function loadData() {
        return $.get(PATH.index.sysParam.param.loadData, loadParams).done(function (res) {
            var data = res.data;
            var total = res.count;
            tableComponent.setState({ data: data });
            //分页
            generatePagination({
                total: total,
                container: $("#sysParamParamTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function tableFilterBar() {
        //刷新列表按钮
        $("#sysParamParamSearchBtn").on("click", function () {
            var params = $(this).closest("form").serializeForm();
            $.extend(loadParams, {
                currentPage: 0
            }, params);
            loadData();
        });
    })();
    (function tableToolbar() {
        //修改按钮
        $("#sysParamParamTableUpdate").on("click", function (event) {
            var data = tableComponent.state.data.filter(function (item) {
                return item.checked;
            })[0];
            if (!data) {
                BootstrapDialog.show({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '警告',
                    draggable: true,
                    message: '请选择将要修改的参数!',
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
            $("#sysParamParamTableForm").fillForm(data);
            //修改时参数名不能修改
            $("#sysParamParamTableForm  input[name='name']").prop({ "readonly": true });
        });
        $('#sysParamParamTableForm').formValidation({
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
                            message: '名称不能为空'
                        }
                    }
                },
                type: {
                    validators: {
                        regexp: {
                            regexp: /^\d*$/,
                            message: '类型必须是数字'
                        }
                    }
                }
            }
        }).on('status.field.fv', function (e, data) {
            var btn = $(e.currentTarget).closest(".modal").find(".submitBtn");
            btn.prop({ "disabled": !formValidationIsValid(e) });
        });
        $("#sysParamParamTableBtn").on('click', function () {
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
                send = $.get(PATH.index.sysParam.param.toolbar.updateSysParam, data);
            } else {
                delete data.id;
                send = $.get(PATH.index.sysParam.param.toolbar.saveSysParam, data);
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
        $("#sysParamParamTableDelete").on('click', function () {
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
                $.get(PATH.index.sysParam.param.toolbar.deleteSysParam, { id: ids }).done(function (res) {
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
            columns: [{ field: 'name', title: '名称' }, { field: 'catalog', title: '目录' }, { field: 'value', title: '值' }, { field: 'type', title: '类型',
                formatter: function (value) {
                    if (value == 0) {
                        return "默认";
                    } else {
                        return "其他";
                    }
                } }, { field: "description", title: "描述信息" }, { field: "creattime", title: "创建时间",
                formatter: function (value) {
                    if (value) {
                        return new Date(value).format("yyyy-MM-dd");
                    }
                }
            }],
            showCheckbox: false
        };
        tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#sysParamParamTable"));
        loadData();
    })();
}());