/**
 * Created by LiYonglei on 2016/5/7.
 */
var StateTable = function () {
    var Td = React.createClass({
        displayName: "Td",

        onDbClick: function () {
            var columnData = this.props.columnData;
            var rowData = this.props.rowData;
            var option = this.props.option;
            var data = this.props.data;
            var currentData = data.filter(function (item) {
                if (item[option.row.id] == rowData[option.row.id] && item[option.column.id] == columnData[option.column.id]) {
                    return true;
                }
                return false;
            })[0];
            this.props.option.onCellDbClick(currentData, rowData, columnData);
        },
        render: function () {
            var columnData = this.props.columnData;
            var rowData = this.props.rowData;
            var option = this.props.option;
            var data = this.props.data;
            var currentData = data.filter(function (item) {
                if (item[option.row.id] == rowData[option.row.id] && item[option.column.id] == columnData[option.column.id]) {
                    return true;
                }
                return false;
            })[0];
            return React.createElement("td", { onDoubleClick: this.onDbClick, dangerouslySetInnerHTML: { __html: option.formatter(currentData) } });
        }
    });
    var Tr = React.createClass({
        displayName: "Tr",

        render: function () {
            var columnData = this.props.columnData;
            var option = this.props.option;
            var data = this.props.data;
            var rowData = this.props.rowData;
            var Tds = rowData.map(function (item) {
                return React.createElement(Td, { key: item[option.row.id], columnData: columnData, rowData: item, option: option, data: data });
            });
            return React.createElement(
                "tr",
                null,
                React.createElement(
                    "th",
                    null,
                    columnData[option.column.name]
                ),
                Tds
            );
        }
    });
    var StateTable = React.createClass({
        displayName: "StateTable",

        getInitialState: function () {
            return { data: [] };
        },
        render: function () {
            var option = this.props.option;
            var data = this.state.data;
            var rowData = option.rowData || getTitleArray(data, [option.row.id, option.row.name]);
            var rows = rowData.map(function (item) {
                return React.createElement(
                    "th",
                    { key: item[option.row.id] },
                    item[option.row.name]
                );
            });
            var columnData = option.columnData || getTitleArray(data, [option.column.id, option.column.name]);
            var Trs = columnData.map(function (item, idx) {
                return React.createElement(Tr, { key: item[option.column.id], columnData: item, rowData: rowData, option: option, data: data });
            });
            return React.createElement(
                "table",
                { className: "stateTable table table-striped table-bordered table-hover" },
                React.createElement(
                    "thead",
                    null,
                    React.createElement(
                        "tr",
                        null,
                        React.createElement(
                            "th",
                            { className: "stateTable-cephalic" },
                            React.createElement(
                                "div",
                                null,
                                React.createElement(
                                    "span",
                                    null,
                                    option.rowCephalicTitle
                                ),
                                React.createElement(
                                    "span",
                                    null,
                                    option.columnCephalicTitle
                                )
                            )
                        ),
                        rows
                    )
                ),
                React.createElement(
                    "tbody",
                    null,
                    Trs
                )
            );
        }
    });

    /**
     * Created by LiYonglei on 2016/5/7.
     * 获取一组array中的指定的列生成的新的数组，去掉重复的.
     * params:
     * data传入的原始array数据
     * array指定保留的列的数组
     */
    function getTitleArray(data, array) {
        var hashKeys = {};
        var titleArray = [];
        data.forEach(function (item) {
            if (!hashKeys[item[array[0]]]) {
                hashKeys[item[array[0]]] = true;
                var obj = {};
                array.forEach(function (title) {
                    obj[title] = item[title];
                });
                titleArray.push(obj);
            }
        });
        return titleArray;
    }
    return StateTable;
}();