/**
 * Created by LiYonglei on 2016/5/14.
 * 条件过滤的时候表单取消回车默认提交；而是触发查询事件
 */
$("html").on("submit.enterForm",".tableFilterBar form",function(event){
    event.preventDefault();
    event.stopPropagation();
    return false;
}).on("keyup.enterForm",".tableFilterBar :text",function(event){
    if(event.keyCode=="13"){
        $(this).closest("form").find(".searchBtn").trigger("click");
    }
})
