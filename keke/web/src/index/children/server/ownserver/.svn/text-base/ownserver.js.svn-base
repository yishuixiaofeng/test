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
        return $.get(PATH.index.server.ownserver.loadData, loadParams).done(function (res) {
            var data = res.data;
            var total = res.count;
            tableComponent.setState({ data: data });
            generatePagination({
                total: total,
                container: $("#serverOwnserverTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function tableFilterBar() {
        $("#serverOwnserverSearchBtn").on("click", function () {
            var params = $(this).closest("form").serializeForm();
            $.extend(loadParams, {
                currentPage: 0,
                method: 0
            }, params);
            loadData();
            $(this).closest(".tableFilterBar").find(".extendFilterContainer .btn").removeClass("active");
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
        tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#serverOwnserverTable"));
        loadData();
    })();
}());