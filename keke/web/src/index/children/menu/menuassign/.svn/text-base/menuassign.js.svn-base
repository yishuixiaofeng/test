/**
 * Created by chenhong on 2016/4/28.
 */
$(function () {
    var tableComponent;
    var loadParams = {};
    (function tableFilterBar() {
        $("#menuMenuassignSearchBtn").on("click", function () {
            table();
        });
    })();
    //查询系统参数列表
    function loadData() {
        return $.get(PATH.index.menu.menuassign.loadData, loadParams).done(function (res) {
            var data = res.data;
            tableComponent.setState({ data: data });
        });
    }
    function table() {
        var roleData = undefined,
            menuData = undefined;
        var roleSend = $.get(PATH.index.menu.menuassign.getRoleList, {}).done(function (res) {
            roleData = res.data.map(function (item) {
                return { "f_role_id": item.id, "f_role_name": item.name };
            });
        });
        var menuSend = $.get(PATH.index.menu.menuassign.getSysMenuList, {}).done(function (res) {
            menuData = res.data.map(function (item) {
                return { "f_menu_id": item.id, "f_menu_name": item.name };
            });
        });
        $.when(roleSend, menuSend).done(function () {
            if (roleData && menuData) {
                var option = {
                    rowCephalicTitle: "角色",
                    columnCephalicTitle: "模块",
                    rowData: roleData,
                    row: { id: "f_role_id", name: "f_role_name" },
                    columnData: menuData,
                    column: { id: "f_menu_id", name: "f_menu_name" },
                    formatter: function (currentData) {
                        if (currentData) {
                            return "<span class='state-normal menuassign-td-fontSize'>√</span>";
                        } else {
                            return "<span class='state-error menuassign-td-fontSize'>×</span>";
                        }
                    },
                    onCellDbClick: function (currentData, rowData, columnData) {
                        if (currentData) {
                            $.get(PATH.index.menu.menuassign.deleteRoleMenus, { id: currentData.id }).done(function (res) {
                                loadData();
                            });
                        } else {
                            $.get(PATH.index.menu.menuassign.saveRoleMenus, { f_role_id: rowData.f_role_id, f_menu_id: columnData.f_menu_id }).done(function (res) {
                                loadData();
                            });
                        }
                    }
                };
                tableComponent = ReactDOM.render(React.createElement(StateTable, { option: option }), document.querySelector("#menuMenuassignTable"));
                loadData();
            }
        });
    };
    table();
}());