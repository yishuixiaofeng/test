/**
 * Created by LiYonglei on 2016/5/6.
 * 重置筛选条件
 */
$("html").on("click",".resetSearch",function(){
    $(this).closest("form").get(0).reset();
    $(this).siblings(".searchBtn").trigger("click");
}).on("click",".updateSearch",function(){
    $(".pagePanel:visible").data("updateSearch").forEach(function(updateFunction){
        updateFunction();
    })
});
