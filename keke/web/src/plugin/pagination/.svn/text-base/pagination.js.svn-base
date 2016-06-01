/**
 * Created by LiYonglei on 2016/4/15.
 * 包装了生成bootstrap的插件
 * 参数params的值有:
 * total:数据的总数
 * loadParams:加载数据的时候向服务器传递的参数
 * container:包含该分页插件的容器，
 * loadFn:点击页码的时候的回调函数
 */
function generatePagination(params) {
    var total = params.total;
    var loadParams = params.loadParams;
    var totalPages = Math.ceil(total / loadParams.pageCount);
    var startPage = loadParams.currentPage;
    /*
    * 当总页数小于当前页数+1的时候就让当前页数-1
    * 主要是为了删除操作或者别的用户同时删除了以后总页数小于当前页数的情况
    * */
    if(totalPages<startPage+1){
         startPage-=1;
    }
    if(startPage<0){
        startPage=0;
        totalPages=1;
    }
    var container = params.container;
    var loadFn = params.loadFn;
    container.empty();
    var pagination = $("<ul/>", {
        "class": "pagination pagination-sm"
    }).appendTo(container);
    $("<div/>",{
        "class":"paginationTotal",
        text:function(){
            return "共"+total+"条数据"
        }
    }).appendTo(container);
    pagination.twbsPagination({
        totalPages: totalPages,
        startPage: startPage + 1,
        visiblePages: 5,
        hrefVariable: '{{number}}',
        first: '首页',
        prev: '上一页',
        next: '下一页',
        last: '末页',
        loop: false,
        paginationClass: 'pagination',
        nextClass: 'next',
        prevClass: 'prev',
        lastClass: 'last',
        firstClass: 'first',
        pageClass: 'page',
        activeClass: 'active',
        disabledClass: 'disabled',
        onPageClick: function (event, page) {
            $.extend(loadParams, {
                currentPage:page - 1
            });
            loadFn();
        }
    });
}