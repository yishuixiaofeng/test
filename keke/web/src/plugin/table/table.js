var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

/**
 * Created by LiYonglei on 2016/4/15.
 * 一个用react写的通用的生成table的插件
 */
var Table = function () {
    var Thead = React.createClass({
        displayName: 'Thead',

        onAllCheckboxChange: function (event) {
            var checked = event.target.checked;
            this.props.onAllCheckboxChange(checked);
        },
        render: function () {
            var option = this.props.option;
            var showCheckbox = option.showCheckbox;
            var ths = option.columns.map(function (item, idx) {
                return React.createElement(
                    'th',
                    null,
                    item.title
                );
            });
            showCheckbox = classNames({ 'hide': !showCheckbox });
            var isAllChecked = this.props.data.every(function (item) {
                return item.checked;
            });
            ths.unshift(React.createElement(
                'th',
                { className: showCheckbox },
                React.createElement('input', { type: 'checkbox', onChange: this.onAllCheckboxChange, checked: isAllChecked })
            ));
            return React.createElement(
                'thead',
                null,
                React.createElement(
                    'tr',
                    null,
                    ths
                )
            );
        }
    });
    var Td = React.createClass({
        displayName: 'Td',

        onDoubleClick: function (event) {
            var props = this.props;
            var onTdDbClick = props.option.onTdDbClick;
            if (onTdDbClick) {
                onTdDbClick(props, event.target);
            }
        },
        render: function () {
            var _this = this;
            var className = this.props.column.class;
            var formatter = this.props.column.formatter;
            if (formatter) {
                return React.createElement('td', { className: className, onDoubleClick: this.onDoubleClick, dangerouslySetInnerHTML: { __html: this.props.value } });
            } else {
                return React.createElement(
                    'td',
                    { className: className, onDoubleClick: this.onDoubleClick },
                    this.props.value
                );
            }
        }
    });
    var Tr = React.createClass({
        displayName: 'Tr',

        onDoubleClick: function (event) {
            var props = this.props;
            var onDblClickRow = props.option.onDblClickRow;
            if (onDblClickRow) {
                onDblClickRow(props, event.target);
            }
        },
        onClick: function (event) {
            this.props.onTrClick(this.props.id, this.props, event.target);
        },
        render: function () {
            var props = this.props;
            var option = props.option;
            var columns = option.columns;
            var showCheckbox = option.showCheckbox;
            var active = this.props.active;
            var className = classNames({ 'activeTr': active });
            var checkboxHide = classNames({ 'hide': !showCheckbox });
            var tds = columns.map(function (item, idx) {
                var value = "";
                if (item.formatter) {
                    value = item.formatter(props[item.field], props);
                } else {
                    value += props[item.field] || "";
                }
                var params = {
                    column: item,
                    value: value,
                    idx: idx
                };
                return React.createElement(Td, _extends({ option: this.props.option }, params));
            }.bind(this));
            tds.unshift(React.createElement(
                'td',
                { className: checkboxHide },
                React.createElement('input', { type: 'checkbox', checked: this.props.checked })
            ));
            return React.createElement(
                'tr',
                { onDoubleClick: this.onDoubleClick, onClick: this.onClick, className: className },
                tds
            );
        }
    });
    var Detail = React.createClass({
        displayName: 'Detail',

        render: function () {
            var data = this.props.data;
            var props = this.props;
            var option = props.option;
            var columns = option.columns;
            var detail = option.detail || {};
            var formatter = detail.formatter;
            var className = classNames({ 'hide': !detail.show || !formatter || !data.active });
            return React.createElement(
                'tr',
                { className: className },
                React.createElement('td', { dangerouslySetInnerHTML: { __html: formatter ? formatter(data, option) : "" }, colSpan: columns.length })
            );
        }
    });
    var EmptyText = React.createClass({
        displayName: 'EmptyText',

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
            var option = this.props.option;
            var length = this.props.data.length;
            /*
            * 数据的长度不为0或者初始化的时候隐藏
            * */
            var emptyHide = classNames({ "text-center": true, 'hide': length || this.isInit });
            return React.createElement(
                'tr',
                { className: emptyHide },
                React.createElement(
                    'td',
                    { colSpan: option.columns.length },
                    '未查询到任何数据'
                )
            );
        }
    });
    var Tbody = React.createClass({
        displayName: 'Tbody',

        render: function () {
            var onTrClick = this.props.onTrClick;
            var option = this.props.option;
            var data = this.props.data;
            var trs = data.map(function (item, idx) {
                return React.createElement(Tr, _extends({ option: option, onTrClick: onTrClick, key: item.id }, item));
            });
            var details = data.map(function (item, idx) {
                return React.createElement(Detail, { option: option, data: item, key: "details-" + item.id });
            });
            var detailsTrs = [];
            for (var i = 0; i < trs.length; i++) {
                detailsTrs.push(trs[i]);
                detailsTrs.push(details[i]);
            }
            return React.createElement(
                'tbody',
                { option: this.props.option, ref: 'tbody' },
                detailsTrs,
                React.createElement(EmptyText, { option: this.props.option, data: data })
            );
        }
    });
    var Table = React.createClass({
        displayName: 'Table',

        onAllCheckboxChange: function (checked) {
            var data = this.state.data.map(function (item, idx) {
                item.checked = checked;
                return item;
            });
            this.setState({ data: data });
        },
        onTrClick: function (id, props, td) {
            var option = this.props.option;
            var showCheckbox = option.showCheckbox;
            var data = this.state.data.map(function (item, idx) {
                /*
                 * 让所有的tr节点都处于未激活状态
                 * */
                item.active = false;
                /*
                 * 让当前节点处于激活状态
                 * */
                if (item.id == id) {
                    item.active = true;
                }
                if (showCheckbox) {
                    if (item.id == id) {
                        /*
                         * 如果当前节点时选中的则改为未选中；否则为选中
                         * */
                        if (item.checked) {
                            item.checked = false;
                        } else {
                            item.checked = true;
                        }
                    }
                } else {
                    /*
                     * 让所有的元素都处于未选中状态
                     * */
                    item.checked = false;
                    /*
                     * 让当前元素处于选中状态
                     * */
                    if (item.id == id) {
                        item.checked = true;
                    }
                }
                return item;
            });
            this.setState({ data: data });
            if (this.props.option.onTrClick) {
                this.props.option.onTrClick(id, this.props, this.state, props, td);
            }
        },
        getInitialState: function () {
            return { data: [] };
        },
        componentWillMount: function () {},
        render: function () {
            return React.createElement(
                'table',
                { className: 'table table-striped table-bordered table-hover' },
                React.createElement(Thead, { option: this.props.option, data: this.state.data, onAllCheckboxChange: this.onAllCheckboxChange }),
                React.createElement(Tbody, { option: this.props.option, data: this.state.data, onTrClick: this.onTrClick })
            );
        }
    });
    return Table;
}();