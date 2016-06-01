/**
 * Created by LiYonglei on 2016/5/5.
 */
var DatagridPanel = function () {
    /*
    * 根据传入的item和key数组获取react数组得唯一的key值
    * */
    function getKey(item, keys) {
        var result = [];
        for (var key in item) {
            if ($.inArray(key, keys) != -1) {
                result.push(item[key]);
            }
        }
        return result.join("-");
    }
    var Title = React.createClass({
        displayName: "Title",

        render: function () {
            var option = this.props.option;
            var columns = option.columns;
            var ths = columns.map(function (item) {
                return React.createElement(
                    "th",
                    { key: item.field },
                    item.title
                );
            });
            return React.createElement(
                "table",
                { className: "datagridPanel-title table table-striped table-bordered table-hover" },
                React.createElement(
                    "thead",
                    null,
                    React.createElement(
                        "tr",
                        null,
                        ths
                    )
                )
            );
        }
    });
    var Td = React.createClass({
        displayName: "Td",

        render: function () {
            var field = this.props.field;
            var value = this.props.data[field];
            var formatter = this.props.formatter;
            if (formatter) {
                return React.createElement("td", { dangerouslySetInnerHTML: { __html: formatter(value) } });
            } else {
                return React.createElement(
                    "td",
                    null,
                    value
                );
            }
        }
    });
    var Tr = React.createClass({
        displayName: "Tr",

        onRowClick: function (event) {
            var data = this.props.data;
            this.props.option.onRowClickCallback(data, event.target);
        },
        render: function () {
            var data = this.props.data;
            var option = this.props.option;
            var columns = option.columns;
            var trClass = classNames({ 'datagridPanel-active': data.active });
            var Tds = columns.map(function (item, idx) {
                return React.createElement(Td, { key: item.field, data: data, option: this.props.option, field: item.field, formatter: item.formatter });
            }.bind(this));
            return React.createElement(
                "tr",
                { className: trClass, onClick: this.onRowClick },
                Tds
            );
        }
    });
    var PanelTable = React.createClass({
        displayName: "PanelTable",

        render: function () {
            var option = this.props.option;
            var data = this.props.data;
            var Trs = data.map(function (item) {
                return React.createElement(Tr, { key: getKey(item, option.key), data: item, option: this.props.option });
            }.bind(this));
            return React.createElement(
                "table",
                { className: "datagridPanel-panelTable table table-striped table-bordered table-hover" },
                React.createElement(
                    "tbody",
                    null,
                    Trs
                )
            );
        }
    });
    var Panel = React.createClass({
        displayName: "Panel",

        onPanelTitleClick: function (event) {
            var panelTitleData = this.props.panelTitleData;
            this.props.option.onPanelTitleClickCallback(panelTitleData, event.target);
        },
        render: function () {
            var panelTitleData = this.props.panelTitleData;
            var data = panelTitleData.data;
            var option = this.props.option;
            var formatter = option.formatter;
            var panelTitleClass = classNames({ 'datagridPanel-close': !panelTitleData.open, "datagridPanel-panelTitle": true });
            return React.createElement(
                "div",
                { className: "datagridPanel-panel" },
                React.createElement(
                    "div",
                    { className: panelTitleClass, onClick: this.onPanelTitleClick },
                    React.createElement("div", { dangerouslySetInnerHTML: { __html: formatter(option, panelTitleData) }, className: "datagridPanel-panelTitleInner" })
                ),
                React.createElement(PanelTable, { data: data, option: option })
            );
        }
    });
    var EmptyText = React.createClass({
        displayName: "EmptyText",

        componentWillMount: function () {
            /*
             * 初始化的时候做一个初始化的标记
             * */
            this.isInit = true;
        },
        componentWillUpdate: function () {
            /*
             * 更新的时候让初始化标记变为false
             * */
            this.isInit = false;
        },
        render: function () {
            var length = this.props.panelTitleData.length;
            /*
             * 数据的长度不为0或者初始化的时候隐藏
             * */
            var emptyHide = classNames({ "emptyText": true, "info": true, "text-center": true, 'hide': length || this.isInit });
            return React.createElement(
                "div",
                { className: emptyHide },
                "未查询到任何数据"
            );
        }
    });
    var PanelContainer = React.createClass({
        displayName: "PanelContainer",

        render: function () {
            var option = this.props.option;
            var panelKey = option.panelKey;
            var panelTitleData = this.props.panelTitleData;
            var Panels = panelTitleData.map(function (item) {
                return React.createElement(Panel, { key: getKey(item, panelKey), panelTitleData: item, option: option });
            }.bind(this));
            return React.createElement(
                "div",
                { className: "datagridPanel-panelContainer" },
                Panels,
                React.createElement(EmptyText, { panelTitleData: panelTitleData })
            );
        }
    });
    var DatagridPanel = React.createClass({
        displayName: "DatagridPanel",

        getInitialState: function () {
            return { panelTitleData: [] };
        },
        onPanelTitleClickCallback: function (panelTitleData, element) {
            var option = this.props.option;
            var panelTitleDatas = this.state.panelTitleData;
            var currentPanelTitleData = {};
            panelTitleDatas.forEach(function (item) {
                if (getKey(item, option.panelKey) == getKey(panelTitleData, option.panelKey)) {
                    currentPanelTitleData = item;
                }
            });
            currentPanelTitleData.open = !panelTitleData.open;
            this.setState({ panelTitleData: panelTitleDatas });
            if (currentPanelTitleData.open) {
                var onPanelTitleClick = this.props.option.onPanelTitleClick;
                if (onPanelTitleClick) {
                    onPanelTitleClick(currentPanelTitleData, element);
                }
            }
        },
        onRowClickCallback: function (currentData, td) {
            var option = this.props.option;
            var panelTitleDatas = this.state.panelTitleData;
            panelTitleDatas.forEach(function (panelTitleData) {
                var datas = panelTitleData.data;
                datas.forEach(function (data) {
                    data.active = false;
                    if (getKey(data, option.key) == getKey(currentData, option.key)) {
                        data.active = true;
                    }
                });
            });
            this.setState({ panelTitleData: panelTitleDatas });
        },
        render: function () {
            var option = this.props.option;
            /*
             * 若是配置参数中未提供key;那么就将key设置为id；
             * 之后若是key是字符串的格式；就将key转化为数组得格式
             * */
            (function () {
                option.panelKey = option.panelKey || "id";
                if (typeof option.panelKey == "string") {
                    option.panelKey = [option.panelKey];
                }
                option.key = option.key || "id";
                if (typeof option.key == "string") {
                    option.key = [option.key];
                }
            })();
            $.extend(option, {
                onPanelTitleClickCallback: this.onPanelTitleClickCallback,
                onRowClickCallback: this.onRowClickCallback
            });
            this.state.panelTitleData.forEach(function (item) {
                if (!item.data) {
                    item.data = [];
                }
            });
            return React.createElement(
                "div",
                { className: "datagridPanel" },
                React.createElement(Title, { option: option }),
                React.createElement(PanelContainer, { option: option, panelTitleData: this.state.panelTitleData })
            );
        }
    });
    return DatagridPanel;
}();