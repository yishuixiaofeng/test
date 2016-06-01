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
     * 高亮
     * */
    function lineHeight(value) {
        if (loadParams.keyword) {
            return (value + "").replace(new RegExp(loadParams.keyword, "g"), function (word) {
                return "<span class='state-warning'>" + word + "</span>";
            });
        } else {
            return value;
        }
    }
    function loadData() {
        return $.get(PATH.index.custom.search.loadData, loadParams).done(function (res) {
            var data = JSON.parse(res.data);
            var virtualData = data.virtualResult;
            /*
             * 获取用户列表并且让每一个节点都处于打开状态；并且将虚拟设备放到用户底下
             * */
            var panelTitleData = data.userResult.map(function (item) {
                item.open = true;
                /*
                 * 将虚拟设备根据其f_user_id放入对应的用户下面去
                 * */
                virtualData.forEach(function (data) {
                    if (data.f_user_id == item.id && data.f_server_id == item.f_server_id) {
                        item.data = item.data || [];
                        item.data.push(data);
                    }
                });
                for (var key in item) {
                    if (typeof item[key] == "string") {
                        item[key] = lineHeight(item[key]);
                    }
                }
                return item;
            });
            var total = res.count;
            tableComponent.setState({ panelTitleData: panelTitleData });
            generatePagination({
                total: total,
                container: $("#customSearchTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function tableFilterBar() {
        $("#searchSearchBtn").on("click", function () {
            var params = $(this).closest("form").serializeForm();
            $.extend(loadParams, {
                currentPage: 0
            }, params);
            loadData();
        });
    })();
    (function table() {
        var option = {
            columns: [{ field: 'username', title: '手机号', formatter: function (value) {
                    return lineHeight(value);
                } }, { field: 'realname', title: '姓名', formatter: function (value) {
                    return lineHeight(value);
                } }, { field: 'paytype', title: '付费方式', formatter: function (value) {
                    switch (value) {
                        case 0:
                            return "";
                        case 1:
                            return "试用";
                        case 2:
                            return "月付";
                    }
                } }, { field: 'duetime', title: '到期时间', formatter: function (value) {
                    if (value) {
                        return new Date(value).format("yyyy-MM-dd hh:mm:ss");
                    }
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
            formatter: function (option, panelTitleData) {
                return $("<div/>").append($("<span/>").append($("<span/>", {
                    text: "服务器名称\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["f_server_name"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "服务器地址\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["f_server_host"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "用户id\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["id"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "用户名\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["username"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "所属筛选员\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["ownname"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "单位全称\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["enterprisename"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "最大设备数\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["maxdevice"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "单位简称\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["enterpriseabbrname"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "联系人\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["contacts"] || ""
                })), $("<span/>").append($("<span/>", {
                    text: "联系电话\\"
                }), $("<span/>", {
                    "class": "state-normal search-padding-right",
                    html: panelTitleData["mobilephone"] || ""
                }))).get(0).innerHTML;
            },
            panelKey: ["id", "f_server_id"],
            key: ["f_server_id", "f_user_id", "id"]
        };
        tableComponent = ReactDOM.render(React.createElement(DatagridPanel, { option: option }), document.querySelector("#customSearchTable"));
        loadData();
    })();
}());