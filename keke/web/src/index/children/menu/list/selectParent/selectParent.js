/**
 * Created by LiYonglei on 2016/4/17.
 */
var selectMenuListParentPanel = {};
$(function () {
    var tableComponent;
    var loadParams = {
        currentPage: 0,
        pageCount: 5,
        f_parent_id: 0
    };
    var cacheData = [];
    function loadData() {
        /*
        * 先使用缓存的数据填充表格，优化用户体验，然后向后台请求返回最新的数据
        * */
        (function fillCacheData() {
            var fillData = cacheData.slice(loadParams.currentPage * loadParams.pageCount, (loadParams.currentPage + 1) * loadParams.pageCount);
            tableComponent.setState({ data: fillData });
        })();
        $.get(PATH.index.menu.list.selectParent.loadData, loadParams).done(function (res) {
            var data = res.data;
            var total = res.count;
            /*
             * 将要填充到table中的数据
             * */
            tableComponent.setState({ data: data });
            generatePagination({
                component: tableComponent,
                total: total,
                container: $("#selectParentMenuListTablePagination"),
                loadParams: loadParams,
                loadFn: loadData
            });
        });
    }
    (function table() {
        var option = {
            columns: [{ field: 'name', title: '菜单名称' }],
            showCheckbox: false,
            onDblClickRow: function (props, row) {
                var f_parent_id = props.id;
                var f_parent_name = props.name;
                $("#selectMenuListParent").val(f_parent_name).prev("[name=f_parent_id]").val(f_parent_id);
                $(row).closest(".modal").data("bs.modal").hide();
            }
        };
        tableComponent = ReactDOM.render(React.createElement(Table, { option: option }), document.querySelector("#selectParentMenuListTable"));
        loadData();
        selectMenuListParentPanel.tableComponent = tableComponent;
    })();
}());