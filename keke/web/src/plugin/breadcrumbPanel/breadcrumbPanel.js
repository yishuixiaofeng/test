/**
 * Created by LiYonglei on 2016/5/11.
 * 面包屑导航
 */
(function ($) {
    var setMethods = {
        selectPath: selectPath,
        hide:hide
    };
    var getMethods = {

    }
    $.fn.breadcrumbPanel = function () {
        var params;
        var method;
        if (!arguments.length || typeof arguments[0] == 'object') {
            this.data('breadcrumbPanel', $.extend({}, $.fn.breadcrumbPanel.default, arguments[0]));
            params = this.data('breadcrumbPanel');
            return this.each(function (idx, item) {
                var _this = init.call(item, params);
                render.call(_this);
            });
        } else {
            if (!$(this).data('breadcrumbPanel')) {
                throw new Error('没初始化');
            }
            params = Array.prototype.slice.call(arguments, 1);
            if (setMethods.hasOwnProperty(arguments[0])) {
                method = setMethods[arguments[0]];
                return this.each(function (idx, item) {
                    var _this = method.apply(item, params);
                });
            } else if (getMethods.hasOwnProperty(arguments[0])) {
                method = getMethods[arguments[0]];
                return method.apply(this, params);
            } else {
                throw new Error('没有这个方法');
            }
        }
    };
    $.fn.breadcrumbPanel.default = {
        rootPath:"",
        data: {},
        onPanelLoaded:function(){}
    };
    function init(params) {
        return this;
    }
    function selectPath(id) {
        modifiedData.call(this,{id:id});
        $(this).removeClass("breadcrumbPanel-hide");
        return this;
    }
    function hide(){
        $(this).addClass("breadcrumbPanel-hide");
    }
    function render() {
        var _this=this;
        var params = $(this).data('breadcrumbPanel');
        var data=params.data;
        var pathData=getPath(data);
        if(!pathData.length){
            return;
        }
        var url=pathData[pathData.length-1].url;
        var breadcrumbPanelContainer=$("<div/>",{
            "class":"breadcrumbPanelContainer"
        }).load(params.rootPath+url,params.onPanelLoaded.bind(this,pathData));
        $(this).addClass("breadcrumbPanel breadcrumbPanel-hide").html(
            generateBreadcrumb.bind(this,pathData)
        ).append(
            breadcrumbPanelContainer
        ).prepend(
            $("<button/>",{
                "class":"btn btn-default btn-xs breadcrumbPanel-return",
                text:"返回",
                click:function(){
                    $(_this).addClass("breadcrumbPanel-hide");
                }
            })
        );
        $(this).height(function(idx,value){
            return value>$(this).parent().height()?value:$(this).parent().height();
        });
    }
    function generateBreadcrumb(pathData){
        var _this=this;
        var breadcrumb=$("<ol/>",{
            "class":"breadcrumb"
        });
        pathData.forEach(function(item){
            (function(item){
                $("<li/>",{
                    "class":(function(){
                        return item.active?"active":"";
                    })
                }).append(
                    (function(){
                        if(item.active){
                            return item.text;
                        }else{
                            return $("<a/>",{
                                text:item.text,
                                click:function(){
                                    modifiedData.call(_this,item);
                                    $(_this).removeClass("breadcrumbPanel-hide");
                                }
                            })
                        }
                    }())
                ).appendTo(breadcrumb);
            }(item))
        })
        return breadcrumb;
    }
    /*
     * 获取拥有active的节点的所有的祖先节点以及该节点组成的数组
     * */
    function getPath(data){
        var path=[];
        getSplitPath(data);
        return path.reverse();
        function getSplitPath(data){
            /*
             * 用来判断该节点或者其后代节点中是否拥有有active的元素
             * */
            var isHasActive=false;
            /*
             * 如果某个节点拥有active，就将该节点放入数组中；并且将isHasActive设置为true
             * */
            if(data.active){
                path.push(data);
                isHasActive=true;
                /*
                 * 如果某个节点没有active，但是它有子节点，那么就循环子节点操作
                 * */
            }else if(data.children&&data.children.length){
                data.children.every(function(item){
                    /*
                     * 判断所有的子节点中的是否含有拥有active的子节点，若是拥有的话，那么就将isHasActive设置为true，
                     * 并且将该节点放入数组中，并且停止循环
                     * */
                    if(getSplitPath(item)){
                        isHasActive=true;
                        path.push(data);
                        return false;
                    }
                    /*
                     * 继续循环
                     * */
                    return true;
                })
            }
            /*
             * 将是否拥有拥有active子节点的标识isHasActive作为返回值给getSplitPath
             * */
            return isHasActive;
        }
    }
    /*
     * 将点击的节点active设置为true,其余的active设置为false,并调用页面渲染
     * */
    function modifiedData(item){
        var params = $(this).data('breadcrumbPanel');
        modified(params.data);
        render.call(this);
        function modified(data){
            data.active=false;
            if(data.id==item.id){
                data.active=true;
            }
            if(data.children){
                data.children.forEach(function(data){
                    modified(data);
                })
            }
        }
    }
}(jQuery))
