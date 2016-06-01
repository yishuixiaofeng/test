/**
 * Created by LiYonglei on 2016/4/14.
 */
$(function () {
    var tableComponent;
    var loadParams = {
        currentPage: 0,
        pageCount: 15,
        keyword: "",
        rolenumber: TYFO.general.rolenumber

    };
    /*
    * 重要性
    * */
    var priority = {
        0: "无",
        1: "普通",
        2: "重要"
    };
    /*
    * 定义一个数组用来盛放可以更新的检索条件函数
    * */
    var updateSearch = $(".pagePanel:visible").data({ "updateSearch": [] }).data("updateSearch");
    function loadData() {
        return $.get(PATH.index.custom.list.loadData, loadParams).done(function (res) {
            var data = res.data;
            var total = res.count;
            tableComponent.setState({ data: data });
            generatePagination({
                total: total,
                container: $("#customListTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function tableFilterBar() {
        function selectServer() {
            return $.get(PATH.index.custom.list.filter.selectServer).done(function (res) {
                $("#customListTableFilterBarSelectServer").empty();
                var data = res.data;
                data.forEach(function (item) {
                    $("<option/>", {
                        "value": item.id,
                        "data-subtext": item.ip,
                        "text": item.name
                    }).appendTo("#customListTableFilterBarSelectServer");
                });
                $("#customListTableFilterBarSelectServer").selectpicker("refresh");
            });
        }
        selectServer().done(function () {
            $("#customListTableFilterBarSelectServer").on("changed.bs.select", function () {
                selectUser.call(this);
            }).on("refreshed.bs.select", function () {
                selectUser.call(this);
                $("#customListSearchBtn").trigger("click");
            });
        });
        updateSearch.push(selectServer);
        function selectUser() {
            var f_server_id = $(this).selectpicker("val");
            $("#customListTableFilterBarSelectUser").get(0).length = 1;
            $.get(PATH.index.custom.list.filter.selectUser, { f_server_id: f_server_id }).done(function (res) {
                var data = res.data;
                data.forEach(function (item) {
                    $("<option/>", {
                        "value": item.id,
                        "text": item.username
                    }).appendTo("#customListTableFilterBarSelectUser");
                });
            });
        }
        $("#customListSearchBtn").on("click", function () {
            var f_server_id = $("#customListTableFilterBarSelectServer").selectpicker("val");
            if (f_server_id == null) {
                $("#serverEmpty").removeClass("hide");
                $("#customListTableFilterBar,#customListTable").addClass("hide");
                return;
            }
            var params = {
                keyword: $("[name=keyword]", $(this).closest("form")).val(),
                f_server_id: f_server_id,
                f_ty_user_id: $("#customListTableFilterBarSelectUser").val()
            };
            $.extend(loadParams, {
                currentPage: 0
            }, params);
            loadData();
        }).next(".resetSearch").on("click", function () {
            $("#customListTableFilterBarSelectServer").selectpicker('deselectAll');
        });
    })();
    (function table() {
        /*
        * 设备列表表格
        * */
        var tyfoVirtualUserListTableComponent;
        var option = {
            columns: [{ field: 'f_server_name', title: '服务器名称' }, { field: 'f_server_host', title: '服务器地址' }, { field: 'id', title: '用户id' }, { field: 'username', title: '用户名' }, { field: 'ownname', title: '所属筛选员' }, { field: 'enterprisename', title: '单位全称' }, { field: 'maxdevice', title: '最大设备数', formatter: function (value) {
                    return "" + value;
                } }, { field: 'enterpriseabbrname', title: '单位简称' }, { field: 'contacts', title: '联系人' }, { field: 'mobilephone', title: '联系电话' }, { field: 'priority', title: '重要性', formatter: function (value) {
                    return $("<span/>", {
                        "class": function () {
                            switch (value) {
                                case 0:
                                    return "state-error";
                                case 1:
                                    return "state-normal";
                                case 2:
                                    return "state-warning";
                            }
                        },
                        "text": priority[value]
                    }).get(0).outerHTML;
                } }],
            showCheckbox: false,
            onDblClickRow: function (props, td) {
                tableDetail(props);
                $('#customListTableDetailModal').modal('show');
            }
        };
        tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#customListTable"));
        function tableDetail(props) {
            (function tyfoVirtualUserList() {
                var loadParams = {
                    currentPage: 0,
                    pageCount: 15,
                    f_server_id: props.f_server_id,
                    f_user_id: props.id
                };
                function loadData() {
                    return $.get(PATH.index.custom.list.tableDetail.tyfoVirtualUserList, loadParams).done(function (res) {
                        var data = res.data;
                        var total = res.count;
                        tyfoVirtualUserListTableComponent.setState({ data: data });
                        generatePagination({
                            total: total,
                            container: $("#customListTableDetailModalTyfoVirtualUserListTablePagination"),
                            loadParams: loadParams,
                            loadFn: loadData
                        });
                    });
                }
                if (!tyfoVirtualUserListTableComponent) {
                    var option = {
                        columns: [{ field: 'username', title: '手机号' }, { field: 'realname', title: '姓名' }, { field: 'paytype', title: '付费方式', formatter: function (value) {
                                switch (value) {
                                    case 0:
                                        return "";
                                    case 1:
                                        return "试用";
                                    case 2:
                                        return "月付";
                                }
                            } }, { field: 'duetime', title: '到期时间', formatter: function (value) {
                                return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                            } }, { field: 'status', title: '状态', formatter: function (value) {
                                switch (value) {
                                    case 0:
                                        ;
                                    case 1:
                                        return "<span class='state-normal'>启用</span>";
                                    case -1:
                                        return "<span class='state-warning'>禁用</span>";
                                }
                            } }],
                        showCheckbox: false
                    };
                    tyfoVirtualUserListTableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#customListTableDetailModalTyfoVirtualUserListTable"));
                }
                loadData();
            })();
            (function funcGroupUsed() {
                var loadParams = {
                    currentPage: 0,
                    pageCount: 15,
                    f_server_id: props.f_server_id,
                    f_ty_user_id: props.id
                };
                function loadData() {
                    return $.get(PATH.index.custom.list.tableDetail.funcGroupUsed, loadParams).done(function (res) {
                        var data = JSON.parse(res.data.data).Body.Response;
                        $("#customListTableDetailModalFuncGroupUsedTabPanel").empty();
                        for (var key in data) {
                            var arr = data[key];
                            if (arr instanceof Array) {
                                arr.forEach(function (item) {
                                    var fieldset = $("<fieldset/>", {
                                        "class": "taocanModule"
                                    }).appendTo("#customListTableDetailModalFuncGroupUsedTabPanel");
                                    var dl = $("<dl/>", {
                                        "class": "dl-horizontal"
                                    }).appendTo(fieldset);
                                    dl.append($("<dt/>", {
                                        text: "模块名称"
                                    }), $("<dd/>", {
                                        html: item.name
                                    }));
                                    dl.append($("<dt/>", {
                                        text: "可建专题数量"
                                    }), $("<dd/>", {
                                        html: item.maxkeyword || 0
                                    }));
                                    dl.append($("<dt/>", {
                                        text: "已建专题数量"
                                    }), $("<dd/>", {
                                        html: item.usedKeywordCount || 0
                                    }));
                                    if (item.tagname == "weibo") {
                                        dl.append($("<dt/>", {
                                            text: "可建重点人数量"
                                        }), $("<dd/>", {
                                            html: item.maxkeyperson || 0
                                        }));
                                        dl.append($("<dt/>", {
                                            text: "已建重点人数量"
                                        }), $("<dd/>", {
                                            html: item.usedKeypersonCount || 0
                                        }));
                                    }
                                    dl.append($("<dt/>", {
                                        html: "系统推荐(<span class='state-normal'>订阅</span><span class='state-error'>未订阅</span>)"
                                    }), $("<dd/>").append(function () {
                                        var folder = JSON.parse(item.sysfolder || item.customfolder || "[]");
                                        return folder.map(function (item) {
                                            return $("<span/>", {
                                                "class": function () {
                                                    return item.subscribe ? "state-normal" : "state-error";
                                                },
                                                text: item.name + " "
                                            });
                                        });
                                    }()));
                                });
                            }
                        }
                    });
                }
                loadData();
            })();
            (function userInfo() {
                var loadParams = {
                    f_server_id: props.f_server_id,
                    f_ty_user_id: props.ownid || 0
                };
                function loadData() {
                    return $.get(PATH.index.custom.list.tableDetail.userInfo, loadParams).done(function (res) {
                        var data = res.data || {};
                        $("#customListTableDetailModalUserinfoForm").fillForm(data);
                    });
                }
                loadData();
            })();
        }
    })();
}());